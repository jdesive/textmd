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

package com.desive.nodes.toolbars.menus.items.editor.edit;

import com.desive.nodes.TabFactory;
import com.desive.nodes.tabs.EditorTab;
import com.desive.utilities.Dictionary;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

/*
 Created by Jack DeSive on 11/1/2017 at 9:54 PM
*/
public class EditorUndoItem extends MenuItem {

    public EditorUndoItem(Dictionary dictionary, KeyCombination accelerator, TabFactory tabFactory) {
        super(dictionary.TOOLBAR_EDITOR_UNDO_ITEM);
        this.setAccelerator(accelerator);
        this.setOnAction(this.getClickAction(tabFactory));
    }

    private EventHandler<ActionEvent> getClickAction(final TabFactory tabFactory) {
        return event -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            if(currTab.getEditorPane().getEditor().isUndoAvailable())
                currTab.getEditorPane().getEditor().undo();
        };
    }

}
