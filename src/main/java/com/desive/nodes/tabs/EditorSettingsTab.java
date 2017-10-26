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
import com.desive.utilities.Settings;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

/*
 Created by Jack DeSive on 10/23/2017 at 9:37 PM
*/
public class EditorSettingsTab extends SettingsTab {

    Dictionary dict = Dictionary.getInstance();

    Label editorHighlightRefreshRateLabel = new Label(dict.SETTINGS_EDITOR_REFRESH_RATE_LABEL + " ");
    TextField editorHighlightRefreshRateField = new TextField(String.valueOf(Settings.EDITOR_HIGHLIGHT_REFRESH_RATE));

    public EditorSettingsTab() {
        super(Dictionary.SETTINGS_EDITOR_TAB_HEADER_LABEL);

        this.addHighlightRefreshRateBox(0, 0);

    }

    private void addHighlightRefreshRateBox(int col, int row) {
        HBox viewRefreshRateBox = new HBox();
        viewRefreshRateBox.setAlignment(Pos.CENTER);
        this.editorHighlightRefreshRateLabel.setTextFill(this.textColor);
        this.editorHighlightRefreshRateLabel.setAlignment(Pos.CENTER);
        this.editorHighlightRefreshRateField.setPrefSize(45, 15);
        viewRefreshRateBox.getChildren().addAll(
                this.editorHighlightRefreshRateLabel,
                this.editorHighlightRefreshRateField
        );
        this.editorHighlightRefreshRateField.setOnAction(e ->
                Settings.setViewRefreshRate(Integer.parseInt(this.editorHighlightRefreshRateField.getText()))
        );
        this.editorHighlightRefreshRateField.setTooltip(new Tooltip(dict.SETTINGS_EDITOR_REFRESH_RATE_FIELD_TOOLTIP));
        this.editorHighlightRefreshRateLabel.setTooltip(new Tooltip(dict.SETTINGS_EDITOR_REFRESH_RATE_LABEL_TOOLTIP));
        this.grid.add(viewRefreshRateBox, col, row);
    }

}
