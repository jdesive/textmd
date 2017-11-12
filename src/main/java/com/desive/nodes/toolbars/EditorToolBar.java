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

package com.desive.nodes.toolbars;

import com.desive.nodes.TabFactory;
import com.desive.nodes.editor.toolbar.ActionTextPane;
import com.desive.nodes.editor.toolbar.ViewSelectorPane;
import com.desive.utilities.Dictionary;
import javafx.scene.layout.BorderPane;

/*
 Created by Jack DeSive on 11/7/2017 at 9:30 PM
*/
public class EditorToolBar extends BorderPane{

    private ActionTextPane actionTextPane;

    public EditorToolBar(Dictionary dictionary, TabFactory tabFactory) {

        setPrefHeight(20.0);
        getStyleClass().add("toolbar");

        actionTextPane = new ActionTextPane();

        setRight(new ViewSelectorPane(dictionary, tabFactory));
        setLeft(actionTextPane);
    }

    public void setActionText(String text) {
        actionTextPane.setActionText(text);
    }

}
