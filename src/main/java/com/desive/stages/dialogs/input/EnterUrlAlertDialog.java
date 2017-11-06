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

package com.desive.stages.dialogs.input;

import com.desive.stages.dialogs.TextInputAlertDialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/*
 Created by Jack DeSive on 11/4/2017 at 9:23 PM
*/
public class EnterUrlAlertDialog extends TextInputAlertDialog{

    private String title;
    private String header;
    private Stage ownerStage;

    public EnterUrlAlertDialog(String title, String header, Stage ownerStage) {
        super("");
        this.title = title;
        this.header = header;
        this.ownerStage = ownerStage;
    }

    @Override
    public TextInputDialog build() {
        setTitle(title);
        setHeaderText(header);
        setContentText("URL:");
        initOwner(ownerStage);
        return this;
    }

}
