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

package com.desive.nodes;

import com.desive.markdown.MarkdownHighligher;
import com.desive.markdown.MarkdownParser;
import com.desive.utilities.Utils;
import com.vladsch.flexmark.pdf.converter.PdfConverterExtension;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Worker;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntFunction;

//import java.util.Scanner;

/*
 Created by Jack DeSive on 10/8/2017 at 2:12 PM
*/
public class EditorPane extends SplitPane {
    CodeArea editor = new CodeArea("");
    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();
    Timeline covertTask = null;
    File file = new File(Utils.getDefaultFileName());
    AtomicBoolean saved = new AtomicBoolean(false), highlightLimiter = new AtomicBoolean(false);
    String currentHtml = "", currentHtmlWithStyle = "";

    public EditorPane() {
        this(Utils.getSampleText());
    }

    public EditorPane(String content) {
        this.styleEditor("css/editor.css");
        this.styleWebView();
        this.setSyncViews();
        this.setContent(content);
        editor.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
                .successionEnds(Duration.ofMillis(100))
                .subscribe(change -> {
                    saved.set(false);
                    MarkdownHighligher.computeHighlighting(editor.getText(), editor);
                });
        VirtualizedScrollPane editorScroller = new VirtualizedScrollPane<>(editor);
        this.getItems().addAll(new StackPane(editorScroller), webView);
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

    public void save(Stage primaryStage) throws IOException {
        if(file.exists()){
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.print(editor.getText());
            writer.close();
        }else{
            saveAs(primaryStage);
        }
    }

    public void saveAs(Stage primaryStage) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file.getParentFile());
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Md files (*.md)", "*.md");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file != null){
            this.file = file;
            this.file.createNewFile();
            this.save(primaryStage);
        }
    }

    public boolean saveHtml(Stage primaryStage, boolean style) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file.getParentFile());
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file != null){
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.print(Utils.wrapWithHtmlDocType(style ? currentHtmlWithStyle : currentHtml));
            writer.close();
            return true;
        }
        return false;
    }

    public boolean saveDocx(Stage primaryStage) throws IOException, Docx4JException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file.getParentFile());
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Docx files (*.docx)", "*.docx");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file != null){
            MarkdownParser.convertMarkdownToDocx(editor.getText()).save(file, Docx4J.FLAG_SAVE_ZIP_FILE);
            return true;
        }
        return false;
    }

    public boolean savePdf(Stage primaryStage, boolean style) throws IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file.getParentFile());
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file != null){
            PdfConverterExtension.exportToPdf(
                    file.getAbsolutePath(),
                    MarkdownParser.convertMarkdownToHTML(Utils.wrapWithHtmlDocType(style ? currentHtmlWithStyle : currentHtml)),
                    "", MarkdownParser.options
            );
            return true;
        }
        return false;
    }

    public boolean saveJira(Stage primaryStage) throws IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file.getParentFile());
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file != null){
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.print(MarkdownParser.convertMarkdownToJira(editor.getText()));
            writer.close();
            return true;
        }
        return false;
    }

    public boolean saveYoutrack(Stage primaryStage) throws IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file.getParentFile());
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file != null){
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.print(MarkdownParser.convertMarkdownToYoutrack(editor.getText()));
            writer.close();
            return true;
        }
        return false;
    }

    public boolean saveText(Stage primaryStage) throws IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file.getParentFile());
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file != null){
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.print(MarkdownParser.convertMarkdownToText(editor.getText()));
            writer.close();
            return true;
        }
        return false;
    }

    public void setContent(String content){
        editor.replaceText(0, editor.getText().length(), content);
        webEngine.loadContent(MarkdownParser.convertMarkdownToHTML(content));
        MarkdownHighligher.computeHighlighting(editor.getText(), editor);
    }

    public String getContent(){
        return editor.getText();
    }

    private void styleEditor(String stylesheet){
        editor.getStylesheets().add(stylesheet);
        IntFunction<String> format = (digits -> " %" + digits + "d ");
        editor.setParagraphGraphicFactory(LineNumberFactory.get(editor, format));
    }

    // Working to get code highlighting with highlight.js
    /*private void styleWebViewWithHighlight(){
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                Document doc = webEngine.getDocument() ;


                // Execute Highlight.js scripts
                InputStream jsPack = getClass().getClassLoader().getResourceAsStream("js/highlight.pack.js");
                String jsPackContent = new Scanner(jsPack).useDelimiter("\\Z").next();
                Element scriptNode = doc.createElement("script"), startScriptNode = doc.createElement("script");
                Text scriptContent = doc.createTextNode(jsPackContent), startScriptContent = doc.createTextNode("$(document).ready(function() {\n" +
                        "  $('pre code').each(function(i, block) {\n" +
                        "    hljs.highlightBlock(block);\n" +
                        "  });\n" +
                        "});");
                webEngine.executeScript(jsPackContent);

                scriptNode.appendChild(scriptContent);
                startScriptNode.appendChild(startScriptContent);
                doc.getDocumentElement().getElementsByTagName("head").item(0).appendChild(scriptNode);
                doc.getDocumentElement().getElementsByTagName("head").item(0).appendChild(startScriptNode);

                // Inject css styles
                Element styleNode = doc.createElement("style");
                InputStream jsPackStyle = getClass().getClassLoader().getResourceAsStream("css/highlight/github.css");
                String jsPackStyleContent = new Scanner(jsPackStyle).useDelimiter("\\Z").next();
                Text styleContent = doc.createTextNode(jsPackStyleContent);

                styleNode.appendChild(styleContent);
                doc.getDocumentElement().getElementsByTagName("head").item(0).appendChild(styleNode);
                currentHtmlWithStyle = webEngine.executeScript("document.documentElement.innerHTML").toString();
                System.out.println(currentHtmlWithStyle);
            }
        });
    }*/

    private void styleWebView(){
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                Document doc = webEngine.getDocument() ;

                // Working to get code highlighting with highlight.js
                /*Element linkNode = doc.createElement("link"), scriptNode = doc.createElement("script"),
                        startScript = doc.createElement("script");
                linkNode.setAttribute("rel", "stylesheet");
                linkNode.setAttribute("href", "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/styles/default.min.css");

                scriptNode.setAttribute("src", "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/highlight.min.js");

                Text startScriptContent = doc.createTextNode("hljs.initHighlightingOnLoad();");
                startScript.appendChild(startScriptContent);*/
                /*
                <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/styles/default.min.css">
                <script src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/highlight.min.js"></script>
                 */

                // Inject css styles
                Element styleNode = doc.createElement("style");
                Text styleContent = doc.createTextNode(Utils.getWebViewCss());

                styleNode.appendChild(styleContent);
                doc.getDocumentElement().getElementsByTagName("head").item(0).appendChild(styleNode);
                //doc.getDocumentElement().getElementsByTagName("head").item(0).appendChild(linkNode);
                //doc.getDocumentElement().getElementsByTagName("head").item(0).appendChild(scriptNode);
                //doc.getDocumentElement().getElementsByTagName("head").item(0).appendChild(startScript);
                currentHtmlWithStyle = webEngine.executeScript("document.documentElement.innerHTML").toString();
            }
        });
    }

    private void setSyncViews(){
        editor.textProperty().addListener((obs, oldValue, newValue) -> {
            if(covertTask == null || covertTask.getStatus().equals(Animation.Status.STOPPED)){
                covertTask = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), (event2) -> {
                    String html = MarkdownParser.convertMarkdownToHTML(editor.getText());
                    webEngine.loadContent(html);
                    currentHtml = html;
                    covertTask.stop();
                }));
                covertTask.play();
            }else{
                covertTask.stop();
                covertTask.playFromStart();
            }
        });
    }

}
