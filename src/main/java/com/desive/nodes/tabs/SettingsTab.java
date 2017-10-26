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

import com.desive.utilities.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;

/*
 Created by Jack DeSive on 10/23/2017 at 9:34 PM
*/
public class SettingsTab extends Tab {

    // Lets hard code in java the css style for the settings tabs
    // Im not sure why... Just feels right
    private String cssStyle = "-fx-padding: 3em 0.2em 3em 0.2em;";
    Paint textColor = Utils.getDefaultTextColor();

    BorderPane container = new BorderPane();
    GridPane grid = new GridPane();

    public SettingsTab(String name) {
        Label headerLabel = new Label(name);
        headerLabel.setTextFill(this.textColor);
        headerLabel.setRotate(90.0);
        this.setGraphic(headerLabel);
        this.setClosable(false);
        this.setStyle(this.cssStyle);

        this.container.setTop(this.grid);
        this.container.setPadding(new Insets(15));
        this.grid.setAlignment(Pos.CENTER);
        this.grid.setVgap(15);
        this.grid.setHgap(25);

        this.setContent(this.container);

    }

}
