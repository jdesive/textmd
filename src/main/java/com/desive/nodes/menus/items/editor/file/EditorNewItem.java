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

package com.desive.nodes.menus.items.editor.file;

import com.desive.nodes.TabFactory;
import com.desive.nodes.menus.MdNewPageMenuItem;
import com.desive.utilities.Dictionary;
import com.desive.utilities.Utils;
import javafx.scene.input.KeyCombination;

import java.io.File;

/*
 Created by Jack DeSive on 11/1/2017 at 9:54 PM
*/
public class EditorNewItem extends MdNewPageMenuItem {

    public EditorNewItem(Dictionary dictionary, KeyCombination accelerator, TabFactory tabFactory) {
        super(dictionary.TOOLBAR_EDITOR_NEW_ITEM);
        this.setAccelerator(accelerator);
        this.setOnAction(event -> getClickAction(tabFactory));
    }

    @Override
    public void getClickAction(final TabFactory tabFactory) {
        tabFactory.addNewEditorTab(new File(Utils.getDefaultFileName()), Utils.getNewPageText());
    }

}
