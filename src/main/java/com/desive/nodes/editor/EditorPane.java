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
import com.desive.stages.dialogs.DialogFactory;
import com.desive.utilities.Dictionary;
import com.desive.utilities.Settings;
import com.desive.utilities.Utils;
import com.desive.utilities.constants.FileExtensionFilters;
import com.desive.views.EditorView;
import com.vladsch.flexmark.pdf.converter.PdfConverterExtension;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntFunction;

/*
 Created by Jack DeSive on 10/8/2017 at 2:12 PM
*/
public class EditorPane extends SplitPane {

    private Dictionary dict;
    private CodeArea editor = new CodeArea("");
    private WebView webView = new WebView();
    private WebEngine webEngine = webView.getEngine();
    private Timeline covertTask = null;
    private File file = new File(Utils.getDefaultFileName());
    private AtomicBoolean saved = new AtomicBoolean(false), prettifyCode = new AtomicBoolean(false);
    private String currentHtml = "", currentHtmlWithStyle = "";
    private Subscription editorHightlightSubscription;
    private DialogFactory dialogFactory;
    private MarkdownParser markdownParser;

    public EditorPane(Dictionary dictionary, DialogFactory dialogFactory, MarkdownParser markdownParser, String content) {

        this.dict = dictionary;
        this.dialogFactory = dialogFactory;
        this.markdownParser = markdownParser;

        this.styleEditor("css/editor.css");
        this.styleWebView();
        this.setSyncViews();
        this.setContent(content);
        this.createEditorHighlightSubscription(Settings.EDITOR_HIGHLIGHT_REFRESH_RATE);
        this.getItems().addAll(getEditorWithScrollbar(), webView);
    }

    private StackPane getEditorWithScrollbar() {
        return new StackPane(new VirtualizedScrollPane<>(getEditor()));
    }

    public CodeArea getEditor() {
        return editor;
    }

    public WebView getWebView() {
        return webView;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isSaved() {
        return saved.get();
    }

    public void setView(EditorView view) {
        getItems().clear();
        switch (view) {
            case CODE_ONLY:
                getItems().add(getEditorWithScrollbar());
                break;
            case VIEW_ONLY:
                getItems().add(getWebView());
                break;
            case SPLIT_VIEW:
                getItems().addAll(getEditorWithScrollbar(), getWebView());
                break;
            default:
        }
    }

    public boolean exit(Stage primaryStage) {
        if(!saved.get()){
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
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.print(editor.getText());
            writer.close();
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
            boolean newFile = this.file.createNewFile();
            return newFile && this.save(primaryStage);
        }
        return false;
    }

    public boolean saveHtml(Stage primaryStage, boolean style) throws IOException {
        return save(
                primaryStage,
                FileExtensionFilters.HTML,
                Utils.wrapWithHtmlDocType(style ? currentHtmlWithStyle : currentHtml)
        );
    }

    public boolean saveDocx(Stage primaryStage) throws IOException, Docx4JException, JAXBException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file.getParentFile());
        fileChooser.setInitialFileName(file.getName().split("\\.")[0] + ".docx");
        fileChooser.getExtensionFilters().add(FileExtensionFilters.DOCX);
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file != null){
            markdownParser.convertToDocx(editor.getText(), file);
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
            PdfConverterExtension.exportToPdf(
                    file.getAbsolutePath(),
                    markdownParser.convertToHTML(Utils.wrapWithHtmlDocType(style ? currentHtmlWithStyle : currentHtml)),
                    "",
                    markdownParser.getOptions()
            );
            return true;
        }
        return false;
    }

    public boolean saveJira(Stage primaryStage) throws IOException{
        return save(
                primaryStage,
                FileExtensionFilters.TEXT,
                markdownParser.convertToJira(editor.getText())
        );
    }

    public boolean saveYoutrack(Stage primaryStage) throws IOException{
        return save(
                primaryStage,
                FileExtensionFilters.TEXT,
                markdownParser.convertToYoutrack(editor.getText())
        );
    }

    public boolean saveText(Stage primaryStage) throws IOException{
        return save(
                primaryStage,
                FileExtensionFilters.TEXT,
                markdownParser.convertToText(editor.getText())
        );
    }

    public boolean saveConfluenceMarkup(Stage primaryStage) throws IOException{
        return save(
                primaryStage,
                FileExtensionFilters.TEXT,
                markdownParser.markdownToConfluenceMarkup(editor.getText())
        );
    }

    private boolean save(Stage primaryStage, FileChooser.ExtensionFilter ext, String content) throws IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file.getParentFile());
        fileChooser.setInitialFileName(file.getName().split("\\.")[0] + ext.getExtensions().stream().findFirst().get().replace("*", ""));
        fileChooser.getExtensionFilters().add(ext);
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file != null){
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.print(content);
            writer.close();
            return true;
        }
        return false;
    }


    public void setContent(String content){
        editor.replaceText(0, editor.getText().length(), content);
        webEngine.loadContent(markdownParser.convertToHTML(content));
        MarkdownHighligher.computeHighlighting(editor.getText(), editor);
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
    }

    public void refreshWebView(){
        covertTask.playFrom(javafx.util.Duration.seconds(Settings.VIEW_REFRESH_RATE));
    }

    private void styleEditor(String stylesheet){
        editor.getStylesheets().add(stylesheet);
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

    private void setSyncViews(){
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

    public void createSyncTimer(int duration){
        covertTask = new Timeline(new KeyFrame(javafx.util.Duration.seconds(duration), (event2) -> {
            String html = markdownParser.convertToHTML(editor.getText());
            webEngine.loadContent(html);
            currentHtml = html;
            covertTask.stop();
        }));
    }

    public void createEditorHighlightSubscription(int value) {
        if(editorHightlightSubscription != null)
            editorHightlightSubscription.unsubscribe();

        editorHightlightSubscription = editor.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
                .successionEnds(Duration.ofMillis(value))
                .subscribe(change -> {
                    saved.set(false);
                    MarkdownHighligher.computeHighlighting(editor.getText(), editor);
                });
    }

}
