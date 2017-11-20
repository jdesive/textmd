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

import com.desive.editor.file.FileFactory;
import com.desive.editor.views.EditorView;
import com.desive.markdown.MarkdownHighligher;
import com.desive.markdown.MarkdownParser;
import com.desive.markdown.syntax.SyntaxHighlighter;
import com.desive.nodes.editor.toolbars.EditorToolBar;
import com.desive.nodes.editor.toolbars.nodes.CaretPositionPane;
import com.desive.stages.dialogs.DialogFactory;
import com.desive.utilities.Settings;
import com.desive.utilities.Spellcheck;
import com.desive.utilities.Utils;
import com.desive.utilities.constants.Dictionary;
import com.desive.utilities.constants.Timer;
import com.google.common.collect.Maps;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.concurrent.Worker;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.languagetool.rules.RuleMatch;
import org.reactfx.Subscription;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
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
    private FileFactory fileFactory;

    private CodeArea editor = new CodeArea("");
    private VirtualWebView webView = new VirtualWebView(new WebView());
    private WebEngine webEngine = webView.getEngine();
    private VirtualizedScrollPane<CodeArea> editorScrollPane = new VirtualizedScrollPane<>(getEditor());
    private VirtualizedScrollPane<VirtualWebView> viewScrollPane = new VirtualizedScrollPane<>(webView);
    private SyntaxHighlighter highlighter = new SyntaxHighlighter();

    private Timer timer = new Timer();
    private Timeline covertTask = null;
    private File file = new File(Utils.getDefaultFileName());
    private AtomicBoolean saved = new AtomicBoolean(false), prettifyCode = new AtomicBoolean(false);
    private String currentHtml = "", currentHtmlWithStyle = "";
    private HashMap<String, List<String>> misspellingSuggestions = Maps.newHashMap();

    private Subscription editorHighlightSubscription;
    private InvalidationListener caretPositionListener;

    public EditorPane(Dictionary dictionary,
                      DialogFactory dialogFactory,
                      MarkdownParser markdownParser,
                      EditorToolBar editorToolBar,
                      Stage primaryStage,
                      String content) {

        this.dict = dictionary;
        this.dialogFactory = dialogFactory;
        this.markdownParser = markdownParser;
        this.editorToolBar = editorToolBar;
        this.fileFactory = new FileFactory(primaryStage, editorToolBar);

        styleEditor();
        styleWebView();
        setSyncViews();
        setContent(content);
        createEditorHighlightSubscription(Settings.EDITOR_HIGHLIGHT_REFRESH_RATE);
        getItems().addAll(getEditorWithScrollbar(), getWebViewWithScrollbar());

        caretPositionListener = event -> CaretPositionPane.resetPosition(this);
        getEditor().caretColumnProperty().addListener(caretPositionListener);
        getEditor().caretPositionProperty().addListener(caretPositionListener);

        //syncScrollbars(); - Not implemented yet
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

    @Contract(pure = true)
    private boolean isSaved() {
        return saved.get();
    }

    public boolean save() throws IOException {
        return fileFactory.markdown().save(file, getContent(), saved);
    }

    public boolean saveAs() throws IOException {
        return fileFactory.markdown().saveAs(file, getContent(), saved);
    }

    public boolean saveHtml(boolean style) throws IOException {
        return fileFactory.html().save(file, style ? currentHtmlWithStyle : currentHtml);
    }

    public boolean saveDocx() throws IOException, Docx4JException, JAXBException {
        return fileFactory.office().saveDocx(getContent(), file, markdownParser);
    }

    public boolean savePdf(boolean style) throws IOException{
        return fileFactory.pdf().save(file, markdownParser, style ? currentHtmlWithStyle : currentHtml);
    }

    public boolean saveJira() throws IOException{
        return fileFactory.jira().save(file, markdownParser, getContent());
    }

    public boolean saveYoutrack() throws IOException{
        return fileFactory.youtrack().save(file, markdownParser, getContent());
    }

    public boolean saveText() throws IOException{
        return fileFactory.plainText().save(file, markdownParser, getContent());
    }

    public boolean saveConfluenceMarkup() throws IOException{
        return fileFactory.confluence().save(file, markdownParser, getContent());
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

    public boolean exit() {
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
                        if(!save()){
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
        getEditor().caretPositionProperty().removeListener(caretPositionListener);
        getEditor().caretColumnProperty().removeListener(caretPositionListener);
        return true;
    }

    public void setContent(String content){
        editor.replaceText(0, editor.getText().length(), content);
        webEngine.loadContent(markdownParser.convertToHTML(content));
        highlighter.compute(getContent(), editor);
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

    public void createEditorHighlightSubscription(int duration) {
        if(editorHighlightSubscription != null)
            editorHighlightSubscription.unsubscribe();

        editorHighlightSubscription = editor.plainTextChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
                .successionEnds(Duration.ofMillis(duration))
                .subscribe(change -> {
                    saved.set(false);
                    timer.start();
                    highlighter.compute(getContent(), editor);
                    editorToolBar.setActionText("Computed highlighting in " + timer.end() + "ms");
                });
    }

}
