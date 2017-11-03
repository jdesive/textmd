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

package com.desive.nodes;

import com.desive.nodes.tabs.EditorSettingsTab;
import com.desive.nodes.tabs.GeneralSettingsTab;
import com.desive.nodes.tabs.ViewSettingsTab;
import com.desive.utilities.Dictionary;
import javafx.geometry.Side;
import javafx.scene.control.TabPane;

/*
 Created by Jack DeSive on 10/23/2017 at 9:30 PM
*/
public class SettingsTabPane extends TabPane {

    public SettingsTabPane(Dictionary dictionary) {

        this.setSide(Side.LEFT);

        this.getTabs().addAll(
                new GeneralSettingsTab(dictionary),
                new EditorSettingsTab(dictionary),
                new ViewSettingsTab(dictionary)
        );

    }
}
