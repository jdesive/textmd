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

package com.desive.stages.dialogs.optional;

import com.desive.stages.dialogs.AlertDialog;
import com.desive.utilities.Dictionary;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/*
 Created by Jack DeSive on 11/4/2017 at 2:14 PM
*/
public class YesNoAlertDialog extends AlertDialog {

    private String title;
    private String header;
    private String content;
    private Dictionary dictionary;
    private Stage ownerStage;

    public YesNoAlertDialog(String title, String header, String content, Dictionary dictionary, Stage ownerStage) {
        super(Alert.AlertType.CONFIRMATION);
        this.title = title;
        this.header = header;
        this.content = content;
        this.dictionary = dictionary;
        this.ownerStage = ownerStage;
        getDialogPane().getStyleClass().add("yesno");
    }

    @Override
    public AlertDialog build() {
        initOwner(ownerStage);
        setTitle(title);
        setHeaderText(header);
        setContentText(content);
        ButtonType yes = new ButtonType(dictionary.DIALOG_BUTTON_YES_LABEL, ButtonBar.ButtonData.YES),
                no = new ButtonType(dictionary.DIALOG_BUTTON_NO_LABEL, ButtonBar.ButtonData.NO),
                cancel = new ButtonType(dictionary.DIALOG_BUTTON_CANCEL_LABEL, ButtonBar.ButtonData.CANCEL_CLOSE);
        getButtonTypes().setAll(yes, no, cancel);
        return this;
    }
}
