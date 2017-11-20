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

package com.desive.nodes.editor.toolbars;

import com.desive.nodes.TabFactory;
import com.desive.nodes.editor.toolbars.nodes.ActionTextPane;
import com.desive.nodes.editor.toolbars.nodes.CaretPositionPane;
import com.desive.nodes.editor.toolbars.nodes.SpellcheckPane;
import com.desive.nodes.editor.toolbars.nodes.ViewSelectorPane;
import com.desive.utilities.constants.Dictionary;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/*
 Created by Jack DeSive on 11/7/2017 at 9:30 PM
*/
public class EditorToolBar extends BorderPane{

    private ActionTextPane actionTextPane;

    private HBox rightBox = new HBox(15);

    public EditorToolBar(Dictionary dictionary, TabFactory tabFactory) {

        setPrefHeight(20.0);
        getStyleClass().add("toolbar");

        actionTextPane = new ActionTextPane();

        rightBox.getChildren().addAll(
                new CaretPositionPane(),
                new SpellcheckPane(dictionary, tabFactory),
                new ViewSelectorPane(dictionary, tabFactory)
        );

        setRight(rightBox);
        setLeft(actionTextPane);
    }

    public void setActionText(String text) {
        actionTextPane.setActionText(text);
    }

}
