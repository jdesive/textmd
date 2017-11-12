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

package com.desive.nodes.editor.toolbar;

import com.desive.utilities.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/*
 Created by Jack DeSive on 11/11/2017 at 2:40 AM
*/
public class ActionTextPane extends StackPane {

    private Label actionText;

    public ActionTextPane() {

        actionText = new Label("Test");
        actionText.setTextFill(Utils.getDefaultTextColor());
        actionText.setAlignment(Pos.CENTER);
        actionText.setPadding(new Insets(0, 0, 0, 15));
        getChildren().add(actionText);
        setAlignment(Pos.CENTER);

    }

    public void setActionText(String text) {
        actionText.setText(text);
    }

}
