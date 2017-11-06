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

package com.desive.stages.dialogs;

import com.desive.stages.dialogs.exceptions.ExceptionAlertDialog;
import com.desive.stages.dialogs.input.EnterUrlAlertDialog;
import com.desive.stages.dialogs.optional.ConfirmationAlertDialog;
import com.desive.stages.dialogs.optional.YesNoAlertDialog;
import com.desive.utilities.Dictionary;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/*
 Created by Jack DeSive on 11/4/2017 at 2:01 PM
*/
public class DialogFactory {

    private Stage ownerStage;
    private Dictionary dictionary;

    public DialogFactory(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void initOwner(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    public AlertDialog buildYesNoDialog(String title, String header, String content) {
        return new YesNoAlertDialog(title, header, content, dictionary, ownerStage).build();
    }

    public AlertDialog buildExceptionDialogBox(String title, String header, String message, Exception exception) {
        return new ExceptionAlertDialog(title, header, message, exception, ownerStage).build();

    }

    public AlertDialog buildConfirmationDialogBox(String title, String content) {
        return new ConfirmationAlertDialog(title, content, ownerStage);
    }

    public TextInputDialog buildEnterUrlDialogBox(String title, String header) {
        return new EnterUrlAlertDialog(title, header, ownerStage).build();
    }

}
