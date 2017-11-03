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

package com.desive.nodes.tabs;

import com.desive.utilities.Dictionary;
import com.desive.utilities.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

/*
 Created by Jack DeSive on 10/23/2017 at 9:34 PM
*/
class SettingsTab extends Tab {

    Paint textColor = Utils.getDefaultTextColor();

    BorderPane container = new BorderPane(), buttonContainer = new BorderPane();
    GridPane grid = new GridPane();

    Button reset, apply;
    HBox buttonBox = new HBox();

    SettingsTab(String name, Dictionary dictionary) {
        Label headerLabel = new Label(name);
        headerLabel.setTextFill(this.textColor);
        headerLabel.setRotate(90.0);
        this.setGraphic(headerLabel);
        this.setClosable(false);

        this.container.setTop(this.grid);
        this.container.setPadding(new Insets(15));
        this.grid.setAlignment(Pos.CENTER);
        this.grid.setVgap(15);
        this.grid.setHgap(25);

        this.apply = new Button(dictionary.SETTINGS_APPLY_BUTTON_LABEL);
        this.reset = new Button(dictionary.SETTINGS_RESET_BUTTON_LABEL);
        this.buttonBox.getChildren().addAll(this.reset, this.apply);
        this.buttonBox.setSpacing(15.0);
        this.buttonContainer.setRight(this.buttonBox);
        this.container.setBottom(this.buttonContainer);

        this.setContent(this.container);

    }

}
