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
import java.util.Scanner;

/*
 Created by Jack DeSive on 10/8/2017 at 1:27 PM
*/
public class Utils {

    private static ClassLoader loader;

    public Utils() {
        loader = getClass().getClassLoader();
    }

    public static Paint getDefaultTextColor() {
        return Color.valueOf("f8f8f2");
    }

    public static String getDefaultFileName(){
        return "untitled.md";
    }

    public static String getHelpPageFileName(){
        return "help.md";
    }

    public static String getTestPageFileName(){
        return "test.md";
    }

    public static String getSampleText(){
        return new Scanner(loader.getResourceAsStream("assets/defaults/sample.md")).useDelimiter("\\Z").next();
    }

    public static String getHelpText(){
        return new Scanner(loader.getResourceAsStream("assets/defaults/help.md")).useDelimiter("\\Z").next();
    }

    public static String getTestText(){
        return new Scanner(loader.getResourceAsStream("assets/defaults/test.md")).useDelimiter("\\Z").next();
    }

    public static String getNewPageText(){
        return new Scanner(loader.getResourceAsStream("assets/defaults/new_page.md")).useDelimiter("\\Z").next();
    }

    public static String getWebViewCss(String background){
        return new Scanner(loader.getResourceAsStream("css/editor_view.css"))
                .useDelimiter("\\Z").next()
                .replace("${code.background}", background);
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
