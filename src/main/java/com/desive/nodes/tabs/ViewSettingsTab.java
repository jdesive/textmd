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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

import static javafx.geometry.Pos.CENTER;

/*
 Created by Jack DeSive on 10/23/2017 at 10:00 PM
*/
public class ViewSettingsTab extends SettingsTab {

    private final Dictionary dict = Dictionary.getInstance();

    private CheckBox alwaysPrettifyCode = new CheckBox(dict.SETTINGS_VIEW_PRETTIFY_CODE_LABEL);
    private Label viewRefreshRateLabel = new Label(dict.SETTINGS_VIEW_REFRESH_RATE_LABEL + " ");
    private TextField viewRefreshRateField = new TextField(String.valueOf(Settings.VIEW_REFRESH_RATE));

    public ViewSettingsTab(Dictionary dictionary) {
        super(dictionary.SETTINGS_VIEW_TAB_HEADER_LABEL, dictionary);

        this.addPrettifyCodeCheckBox(0, 0);
        this.addRefreshRateBox(1, 0);

    }

    private void addPrettifyCodeCheckBox(int col, int row) {
        this.alwaysPrettifyCode.setTooltip(new Tooltip(dict.SETTINGS_VIEW_PRETTIFY_CODE_LABEL_TOOLTIP));
        this.alwaysPrettifyCode.setTextFill(this.textColor);

        this.alwaysPrettifyCode.setOnAction(e ->
                Settings.setAlwaysPrettifyCodeView(this.alwaysPrettifyCode.isSelected())
        );
        this.grid.add(this.alwaysPrettifyCode, col,row);
    }

    private void addRefreshRateBox(int col, int row) {
        HBox viewRefreshRateBox = new HBox();
        viewRefreshRateBox.setAlignment(CENTER);
        this.viewRefreshRateLabel.setTextFill(this.textColor);
        this.viewRefreshRateLabel.setAlignment(CENTER);
        this.viewRefreshRateField.setPrefSize(25, 15);
        viewRefreshRateBox.getChildren().addAll(
                this.viewRefreshRateLabel,
                this.viewRefreshRateField
        );
        this.viewRefreshRateField.setOnAction(e ->
                Settings.setViewRefreshRate(Integer.parseInt(this.viewRefreshRateField.getText()))
        );
        this.viewRefreshRateField.setTooltip(new Tooltip(dict.SETTINGS_VIEW_REFRESH_RATE_FIELD_TOOLTIP));
        this.viewRefreshRateLabel.setTooltip(new Tooltip(dict.SETTINGS_VIEW_REFRESH_RATE_LABEL_TOOLTIP));
        this.grid.add(viewRefreshRateBox, col,row);
    }

}
