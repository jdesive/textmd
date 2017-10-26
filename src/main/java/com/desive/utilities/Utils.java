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

package com.desive.utilities;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;

/*
 Created by Jack DeSive on 10/8/2017 at 1:27 PM
*/
public class Utils {

    public static Paint getDefaultTextColor() {
        return Color.valueOf("f8f8f2");
    }

    public static String getDefaultFileName(){
        return "untitled.md";
    }

    public static String getSampleText(){
        return "# TextMd Sample\n" +
                "\n" +
                "## Some samples\n" +
                "**BOLD** Text\n" +
                "*ITALIC* Text\n" +
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
                "Much more!\n" +
                "***";
    }

    public static String getWebViewCss(String background){ // eh, ill just hard code it.
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
                "pre {\n" +
                "  background-color: " + background + ";\n" +
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
        TextInputDialog dialog = new TextInputDialog("https://raw.githubusercontent.com/fullpipe/markdown-test-page/master/test-page.md");
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText("URL:");
        dialog.initOwner(primaryStage);
        return dialog;
    }

    public static Alert getConfirmationDialog(String title, String content, Stage primaryStage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.initOwner(primaryStage);
        return alert;
    }

    public static String wrapWithHtmlDocType(String content){
        return "<!DOCTYPE html>\n<html>\n" + content + "\n</html>";
    }

}
