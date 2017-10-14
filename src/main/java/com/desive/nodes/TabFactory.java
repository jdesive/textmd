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

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/*
 Created by Jack DeSive on 10/13/2017 at 7:04 PM
*/
public class TabFactory {

    private TabPane tabPane;

    public TabFactory(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void addNewEditorTab(EditorTab editorTab) {
        editorTab.computeTabName();
        this.tabPane.getTabs().add(editorTab);
        this.tabPane.getSelectionModel().select(editorTab);
    }

    public Tab getSelectedTab(){
        return this.tabPane.getSelectionModel().getSelectedItem();
    }

}
