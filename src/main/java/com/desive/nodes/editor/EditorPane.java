/*
 * Copyright (C) 2017  TextMd
 *
 * This file is part of TextMd.
 *
 * TextMd is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.desive.nodes.editor;

import com.desive.markdown.MarkdownHighligher;
import com.desive.markdown.MarkdownParser;
import com.desive.markdown.syntax.SyntaxHighlighter;
import com.desive.nodes.editor.toolbar.LineNumberPane;
import com.desive.nodes.toolbars.EditorToolBar;
import com.desive.stages.dialogs.DialogFactory;
import com.desive.utilities.Dictionary;
import com.desive.utilities.Settings;
import com.desive.utilities.Spellcheck;
import com.desive.utilities.Utils;
import com.desive.utilities.constants.FileExtensionFilters;
import com.desive.utilities.constants.Timer;
import com.desive.views.EditorView;
import com.google.common.collect.Maps;
import com.vladsch.flexmark.pdf.converter.PdfConverterExtension;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.jetbrains.annotations.NotNull;
import org.languagetool.rules.RuleMatch;
import org.reactfx.Subscription;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntFunction;

/*
 Created by Jack DeSive on 10/8/2017 at 2:12 PM
*/
public class EditorPane extends SplitPane {

    private Dictionary dict;
    private DialogFactory dialogFactory;
    private MarkdownParser markdownParser;
    private EditorToolBar editorToolBar;
    private Spellcheck spellcheck = new Spellcheck();

    private CodeArea editor = new CodeArea("");
    private VirtualWebView webView = new VirtualWebView(new WebView());
    private WebEngine webEngine = webView.getEngine();
    private VirtualizedScrollPane<CodeArea> editorScrollPane = new VirtualizedScrollPane<>(getEditor());
    private VirtualizedScrollPane<VirtualWebView> viewScrollPane = new VirtualizedScrollPane<>(webView);

    private Timer timer = new Timer();
    private Timeline covertTask = null;
    private File file = new File(Utils.getDefaultFileName());
    private AtomicBoolean saved = new AtomicBoolean(false), prettifyCode = new AtomicBoolean(false);
    private String currentHtml = "", currentHtmlWithStyle = "";
    private Subscription editorHightlightSubscription;
    private HashMap<String, List<String>> misspellingSuggestions = Maps.newHashMap();

    private SyntaxHighlighter highlighter = new SyntaxHighlighter();


    public EditorPane(Dictionary dictionary,
                      DialogFactory dialogFactory,
                      MarkdownParser markdownParser,
                      EditorToolBar editorToolBar,
                      String content) {

        this.dict = dictionary;
        this.dialogFactory = dialogFactory;
        this.markdownParser = markdownParser;
        this.editorToolBar = editorToolBar;

        this.styleEditor();
        this.styleWebView();
        this.setSyncViews();
        this.setContent(content);
        this.createEditorHighlightSubscription(Settings.EDITOR_HIGHLIGHT_REFRESH_RATE);
        this.getItems().addAll(getEditorWithScrollbar(), getWebViewWithScrollbar());

        getEditor().caretColumnProperty().addListener(event -> LineNumberPane.resetPosition(this));
        getEditor().caretPositionProperty().addListener(event -> LineNumberPane.resetPosition(this));

        syncScrollbars();
    }

    @NotNull
    private StackPane getEditorWithScrollbar() {
        return new StackPane(editorScrollPane);
    }

    @NotNull
    private StackPane getWebViewWithScrollbar() {
        return new StackPane(viewScrollPane);
    }

    public CodeArea getEditor() {
        return editor;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    private boolean isSaved() {
        return saved.get();
    }

    public void setView(EditorView view) {
        getItems().clear();
        switch (view) {
            case CODE_ONLY:
                getItems().add(getEditorWithScrollbar());
                break;
            case VIEW_ONLY:
                getItems().add(getWebViewWithScrollbar());
                break;
            case SPLIT_VIEW:
                getItems().addAll(getEditorWithScrollbar(), getWebViewWithScrollbar());
                break;
            default:
        }
        editorToolBar.setActionText("View set to " + view.getName());
    }

    public boolean exit(Stage primaryStage) {
        if(!isSaved()){
            Optional<ButtonType> save = dialogFactory.buildYesNoDialog(file.getPath(),
                    dict.DIALOG_FILE_NOT_SAVED_TITLE,
                    dict.DIALOG_FILE_NOT_SAVED_CONTENT
            ).showAndWait();

            if(!save.isPresent())
                return false;

            switch (save.get().getButtonData()){
                case YES:
                    try {
                        if(!save(primaryStage)){
                            return false;
                        }
                    } catch (IOException e1) {
                        dialogFactory.buildExceptionDialogBox(
                                dict.DIALOG_EXCEPTION_TITLE,
                                dict.DIALOG_EXCEPTION_SAVING_MARKDOWN_CONTENT,
                                e1.getMessage(),
                                e1
                        ).showAndWait();
                        return false;
                    }
                    break;
                case NO:
                    break;
                case CANCEL_CLOSE:
                default:
                    return false;
            }
        }
        return true;
    }

    public boolean save(Stage primaryStage) throws IOException {
        if(file.exists()){
            timer.start();
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.print(editor.getText());
            writer.close();
            editorToolBar.setActionText("Successfully saved file \'" + file.getName() + "\' in " + timer.end() + "ms");
            saved.set(true);
            return true;
        }else{
            return saveAs(primaryStage);
        }
    }

    public boolean saveAs(Stage primaryStage) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file.getParentFile());
        fileChooser.setInitialFileName(file.getName());
        fileChooser.getExtensionFilters().add(FileExtensionFilters.MARKDOWN);
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file != null){
            this.file = file;
            this.file.createNewFile();
            save(primaryStage);
            return true;
        }
        return false;
    }

    public boolean saveHtml(Stage primaryStage, boolean style) throws IOException {
        return save(
                primaryStage,
                FileExtensionFilters.HTML,
                Utils.wrapWithHtmlDocType(style ? currentHtmlWithStyle : currentHtml),
                "Successfully exported html file"
        );
    }

    public boolean saveDocx(Stage primaryStage) throws IOException, Docx4JException, JAXBException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file.getParentFile());
        fileChooser.setInitialFileName(file.getName().split("\\.")[0] + ".docx");
        fileChooser.getExtensionFilters().add(FileExtensionFilters.DOCX);
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file != null){
            timer.start();
            markdownParser.convertToDocx(editor.getText(), file);
            editorToolBar.setActionText("Successfully exported docx file \'" + file.getName() + "\' in " + timer.end() + "ms");
            return true;
        }
        return false;
    }

    public boolean savePdf(Stage primaryStage, boolean style) throws IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file.getParentFile());
        fileChooser.setInitialFileName(file.getName().split("\\.")[0] + ".pdf");
        fileChooser.getExtensionFilters().add(FileExtensionFilters.PDF);
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file != null){
            timer.start();
            PdfConverterExtension.exportToPdf(
                    file.getAbsolutePath(),
                    markdownParser.convertToHTML(Utils.wrapWithHtmlDocType(style ? currentHtmlWithStyle : currentHtml)),
                    "",
                    markdownParser.getOptions()
            );
            editorToolBar.setActionText("Successfully exported pdf file \'" + file.getName() + "\' in " + timer.end() + "ms");
            return true;
        }
        return false;
    }

    public boolean saveJira(Stage primaryStage) throws IOException{
        return save(
                primaryStage,
                FileExtensionFilters.TEXT,
                markdownParser.convertToJira(editor.getText()),
                "Successfully exported jira file"
        );
    }

    public boolean saveYoutrack(Stage primaryStage) throws IOException{
        return save(
                primaryStage,
                FileExtensionFilters.TEXT,
                markdownParser.convertToYoutrack(editor.getText()),
                "Successfully exported youtrack file"
        );
    }

    public boolean saveText(Stage primaryStage) throws IOException{
        return save(
                primaryStage,
                FileExtensionFilters.TEXT,
                markdownParser.convertToText(editor.getText()),
                "Successfully exported plain text file"
        );
    }

    public boolean saveConfluenceMarkup(Stage primaryStage) throws IOException{
        return save(
                primaryStage,
                FileExtensionFilters.TEXT,
                markdownParser.markdownToConfluenceMarkup(editor.getText()),
                "Successfully exported confluence markup file"
        );
    }

    private boolean save(Stage primaryStage, FileChooser.ExtensionFilter ext, String content, String actionText) throws IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file.getParentFile());
        fileChooser.setInitialFileName(file.getName().split("\\.")[0] + ext.getExtensions().stream().findFirst().get().replace("*", ""));
        fileChooser.getExtensionFilters().add(ext);
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file != null){
            timer.start();
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.print(content);
            writer.close();
            editorToolBar.setActionText(actionText + " in " + timer.end() + "ms");
            return true;
        }
        return false;
    }


    public void setContent(String content){
        editor.replaceText(0, editor.getText().length(), content);
        webEngine.loadContent(markdownParser.convertToHTML(content));
        //MarkdownHighligher.computeHighlighting(editor.getText(), editor);
        highlighter.compute2(getContent(), editor);
    }

    public String getContent(){
        return editor.getText();
    }

    public void prettifyWebViewCode(){
        if(prettifyCode.get()){
            prettifyCode.set(false);
        }else {
            prettifyCode.set(true);
        }
        this.refreshWebView();
        editorToolBar.setActionText("Enabled Code Prettify for current document \'" + file.getName() + "\'");
    }

    public void refreshWebView(){
        covertTask.playFrom(javafx.util.Duration.seconds(Settings.VIEW_REFRESH_RATE));
    }

    public boolean spellcheckDocument() {
        editorToolBar.setActionText("Spell checking document \'" + file.getName() + "\'");
        Platform.runLater(() -> {
            try {
                timer.start();
                List<RuleMatch> matches = spellcheck.check(getContent());
                MarkdownHighligher.computeSpellcheckHighlighting(matches, this);
                editorToolBar.setActionText("Found " + matches.size() + " misspellings in the document \'" + file.getName() + "\' (" + timer.end() + "ms)");
            } catch (IOException e) {
                e.printStackTrace(); // TODO: Exception Dialog
            }
        });

        return true;
    }

    public HashMap<String, List<String>> getMisspellingSuggestions() {
        return misspellingSuggestions;
    }

    private void styleEditor(){
        editor.getStylesheets().add("css/editor.css");
        IntFunction<String> format = (digits -> " %" + digits + "d\t");
        editor.setParagraphGraphicFactory(LineNumberFactory.get(editor, format));
    }

    private void styleWebView(){
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            Document doc = webEngine.getDocument();
            if (newState == Worker.State.SUCCEEDED && doc.getDocumentElement().getElementsByTagName("head").item(0) != null) {
                Node head = doc.getDocumentElement().getElementsByTagName("head").item(0);

                // Google prettify
                if(prettifyCode.get() || Settings.ALWAYS_PRETTIFY_CODE_VIEW) {
                    Element scriptNode = doc.createElement("script");
                    scriptNode.setAttribute("src", "https://cdn.rawgit.com/google/code-prettify/master/loader/run_prettify.js");
                    head.appendChild(scriptNode);
                }

                //JavascriptSpellCheck
                Element spellNode = doc.createElement("script");
                spellNode.setAttribute("type", "text/javascript");
                String loc = getClass().getClassLoader().getResource("spellcheck/include.js").getFile();
                spellNode.setAttribute("src", loc.substring(1, loc.length()));
                head.appendChild(spellNode);

                Element spellExecNode = doc.createElement("script");
                spellExecNode.setAttribute("type", "text/javascript");
                spellExecNode.setTextContent("$Spelling.BinSpellCheckFields(body)");
                head.appendChild(spellExecNode);
                //$Spelling.BinSpellCheckFields(Fields)

                // Inject css styles
                Element styleNode = doc.createElement("style");
                Text styleContent = doc.createTextNode(Utils.getWebViewCss(prettifyCode.get() || Settings.ALWAYS_PRETTIFY_CODE_VIEW ? "#f2f2f2" : "#545454"));

                styleNode.appendChild(styleContent);
                head.appendChild(styleNode);
                currentHtmlWithStyle = webEngine.executeScript("document.documentElement.innerHTML").toString();
            }
        });
    }

    private void setSyncViews() {
        editor.textProperty().addListener((obs, oldValue, newValue) -> {
            if(covertTask == null || covertTask.getStatus().equals(Animation.Status.STOPPED)){
                createSyncTimer(Settings.VIEW_REFRESH_RATE);
                covertTask.play();
            }else{
                covertTask.stop();
                covertTask.playFromStart();
            }
        });
    }

    private void syncScrollbars() {

        //TODO: learn how to sync the scroll bars. at any cost...
    }



    public void createSyncTimer(int duration) {
        covertTask = new Timeline(new KeyFrame(javafx.util.Duration.seconds(duration), (event2) -> {
            timer.start();
            String html = markdownParser.convertToHTML(editor.getText());
            webEngine.loadContent(html);
            currentHtml = html;
            covertTask.stop();
            editorToolBar.setActionText("Refreshed view successfully in " + timer.end() + "ms");
        }));
    }

    public void createEditorHighlightSubscription(int value) {
        if(editorHightlightSubscription != null)
            editorHightlightSubscription.unsubscribe();

        editorHightlightSubscription = editor.plainTextChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
                .successionEnds(Duration.ofMillis(value))
                .subscribe(change -> {
                    saved.set(false);
                    timer.start();
                    //MarkdownHighligher.computeHighlighting(editor.getText(), editor);
                    highlighter.compute2(getContent(), editor);
                    editorToolBar.setActionText("Computed highlighting in " + timer.end() + "ms");
                });
    }

}
