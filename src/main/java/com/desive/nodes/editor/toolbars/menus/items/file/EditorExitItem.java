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

package com.desive.nodes.editor.toolbars.menus.items.file;

import com.desive.nodes.TabFactory;
import com.desive.nodes.editor.tabs.EditorTab;
import com.desive.nodes.editor.toolbars.menus.items.MdStageMenuItem;
import com.desive.utilities.constants.Dictionary;
import javafx.collections.FXCollections;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/*
 Created by Jack DeSive on 11/1/2017 at 9:54 PM
*/
public class EditorExitItem extends MdStageMenuItem {

    private final Logger logger = LoggerFactory.getLogger(EditorExitItem.class);

    public EditorExitItem(Dictionary dictionary, Stage stage, TabFactory tabFactory) {
        super(dictionary.TOOLBAR_EDITOR_EXIT_ITEM);
        this.setOnAction(event -> getClickAction(stage, tabFactory));
    }

    @Override
    public void getClickAction(final Stage stage, final TabFactory tabFactory) {
        List<Tab> tabs = FXCollections.observableArrayList(tabFactory.getTabPane().getTabs());
        Collections.reverse(tabs);
        AtomicBoolean close = new AtomicBoolean(true);
        tabs.forEach(t -> {
            if(close.get()){
                EditorTab eTab = ((EditorTab) t);
                if(!eTab.getEditorPane().exit()){
                    close.set(false);
                    return;
                }else{
                    logger.debug("Closing tab {}", eTab.getEditorPane().getFile().getPath());
                    tabFactory.getTabPane().getTabs().remove(eTab);
                }
            }
        });
        if(close.get())
            stage.close();
    }

}
