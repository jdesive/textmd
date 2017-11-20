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

package com.desive.nodes.settings.tabs;

import com.desive.utilities.Settings;
import com.desive.utilities.constants.Dictionary;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;

/*
 Created by Jack DeSive on 10/23/2017 at 9:37 PM
*/
public class GeneralSettingsTab extends SettingsTab {

    private Dictionary dict = Dictionary.getInstance();

    private CheckBox useCourierPrimalFont;

    public GeneralSettingsTab(Dictionary dictionary) {
        super(dictionary.SETTINGS_GENERAL_TAB_HEADER_LABEL, dictionary);

        this.addCourierPrimalCheckBox(0, 0);

        this.apply.setOnAction(e ->
                Settings.setLoadFontsAtRuntime(this.useCourierPrimalFont.isSelected())
        );

        this.reset.setOnAction(e ->
                useCourierPrimalFont.setSelected(Settings.LOAD_FONTS_AT_RUNTIME)
        );

    }

    private void addCourierPrimalCheckBox(int col, int row) {
        this.useCourierPrimalFont = new CheckBox(dict.SETTINGS_GENERAL_USE_COURIER_PRIMAL_FONT);
        this.useCourierPrimalFont.setTooltip(new Tooltip(dict.SETTINGS_GENERAL_USE_COURIER_PRIMAL_FONT_TOOLTIP));
        this.useCourierPrimalFont.setTextFill(this.textColor);
        this.grid.add(this.useCourierPrimalFont, col, row);
    }

}
