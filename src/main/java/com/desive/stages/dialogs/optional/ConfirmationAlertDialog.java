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
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/*
 Created by Jack DeSive on 11/4/2017 at 9:29 PM
*/
public class ConfirmationAlertDialog extends AlertDialog {

    private String title;
    private String content;
    private Stage ownerStage;

    public ConfirmationAlertDialog(String title, String content, Stage ownerStage) {
        super(Alert.AlertType.INFORMATION);
        this.title = title;
        this.content = content;
        this.ownerStage = ownerStage;
        getDialogPane().getStyleClass().add("confirmation");
    }

    @Override
    public AlertDialog build() {
        setTitle(title);
        setHeaderText(null);
        setContentText(content);
        initOwner(ownerStage);
        return this;
    }
}
