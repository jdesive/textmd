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

package com.desive.nodes.editor.toolbars.menus.items.view.extensions;

import com.desive.markdown.MarkdownParser;
import com.desive.nodes.TabFactory;
import com.desive.nodes.editor.toolbars.menus.items.MdExtensionMenuItem;
import com.desive.utilities.constants.Dictionary;
import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import javafx.scene.control.Tooltip;

/*
 Created by Jack DeSive on 11/5/2017 at 9:52 PM
*/
public class EditorExtTaskListItem extends MdExtensionMenuItem {

    private Extension taskLinkExtension;

    public EditorExtTaskListItem(Dictionary dictionary, MarkdownParser markdownParser, TabFactory tabFactory, boolean defaultValue) {
        super(dictionary.TOOLBAR_EDITOR_EXTENSIONS_TASK_LIST_ITEM);
        taskLinkExtension = TaskListExtension.create();
        setSelected(defaultValue);
        if(defaultValue)
            markdownParser.addExtension(taskLinkExtension);
        setToolTip(new Tooltip(dictionary.TOOLBAR_EDITOR_EXTENSIONS_TASK_LIST_TOOLTIP));
        setOnAction(event -> getClickAction(markdownParser, tabFactory));
    }

    @Override
    protected void getClickAction(MarkdownParser markdownParser, final TabFactory tabFactory) {
        addExtension(markdownParser, taskLinkExtension);
        tabFactory.refreshSelectedTabView();
    }

}
