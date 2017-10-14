package com.desive.utilities;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;

/*
 Created by Jack DeSive on 10/8/2017 at 1:27 PM
*/
public class Utils {

    public static String getDefaultFileName(){
        return "untitled.md";
    }

    public static String getSampleText(){
        return "# TextMd Sample\n" +
                "\n" +
                "## Some samples\n" +
                "__BOLD__ Text\n" +
                "_ITALIC_ Text\n" +
                "__BOLD__ Text\n" +
                "_ITALIC_ Text\n" +
                "`inline-code` Text\n" +
                "[Link](https://github.com) Hyperlink\n" +
                "![Alt Text](http://via.placeholder.com/50x50) Image\n" +
                "\n" +
                "## Code\n" +
                "```java\n" +
                "public static String sample = \"Here is some sample Java code!\";\n" +
                "```\n" +
                "\n" +
                "Much more!";
    }

    public static String getWebViewCss(){ // eh, ill just hard code it.
        return "\nbody {\n" +
                "  background-color: #3B3F42;\n" +
                "  font-family: 'Courier Primal';\n" +
                "  color: #f8f8f2;\n" +
                "}\n" +
                "\n" +
                "a {\n" +
                "  background-color: #3B3F42;\n" +
                "  font-family: 'Courier Primal';\n" +
                "  color: #90b8e0;\n" +
                "  text-decoration: none;\n" +
                "}\n" +
                "\n" +
                "a:hover {\n" +
                "  text-decoration: underline;\n" +
                "}\n" +
                "\n" +
                "p code, li code {\n" +
                "  background-color: rgba(44, 44, 44, 0.4);\n" +
                "  padding: 0px 3px 0px 3px;\n" +
                "  border-radius: 2px;\n" +
                "}\n" +
                "\n" +
                "pre code[class]:after {\n" +
                "  content: 'Code: ' attr(class);\n" +
                "  display: block; text-align: right;\n" +
                "  font-size: smaller;\n" +
                "  padding-top: 5px;\n" +
                "}\n" +
                "\n" +
                "pre {\n" +
                "  background-color: rgba(44, 44, 44, 0.4);\n" +
                "  font-family: 'Courier Primal';\n" +
                "  color: #f8f8f2;\n" +
                "  padding: 10px;\n" +
                "  border-radius: 2px;\n" +
                "  width: 97%;\n" +
                "  overflow: scroll;\n" +
                "}\n";
    }

    public static Alert getYesNoDialogBox(String title, String header, String content, Stage primaryStage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(primaryStage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES),
        no = new ButtonType("No", ButtonBar.ButtonData.NO),
        cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, no, cancel);
        return alert;
    }

    public static Alert getExceptionDialogBox(String title, String header, String message, Exception ex, Stage primaryStage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        TextArea textArea = new TextArea();
        GridPane expContent = new GridPane();
        Label label = new Label("Stacktrace:");
        alert.initOwner(primaryStage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        ex.printStackTrace(pw);
        String exceptionText = sw.toString();
        textArea.setText(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
        return alert;

    }

    public static TextInputDialog getTextInputDialog(String title, String header, Stage primaryStage) {
        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText("URL:");
        dialog.initOwner(primaryStage);
        return dialog;
    }

    public static String wrapWithHtmlDocType(String content){
        return "<!DOCTYPE html>\n<html>\n" + content + "\n</html>";
    }

}
