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

package com.desive.stages.dialogs.exceptions;

import com.desive.stages.dialogs.AlertDialog;
import com.desive.utilities.Utils;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;

/*
 Created by Jack DeSive on 11/4/2017 at 2:19 PM
*/
public class ExceptionAlertDialog extends AlertDialog {

    private String title;
    private String header;
    private String message;
    private Exception exception;
    private Stage ownerStage;

    public ExceptionAlertDialog(String title, String header, String message, Exception exception, Stage ownerStage) {
        super(Alert.AlertType.ERROR);
        this.title = title;
        this.header = header;
        this.message = message;
        this.exception = exception;
        this.ownerStage = ownerStage;
        getDialogPane().getStyleClass().add("exception");
    }

    @Override
    public AlertDialog build() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        TextArea textArea = new TextArea();
        GridPane expContent = new GridPane();
        Label label = new Label("Stacktrace:");
        label.setTextFill(Utils.getDefaultTextColor());
        initOwner(ownerStage);
        setTitle(title);
        setHeaderText(header);
        setContentText(message);

        exception.printStackTrace(pw);
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

        getDialogPane().setExpandableContent(expContent);
        return this;
    }
}
