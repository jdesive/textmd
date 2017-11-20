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

package com.desive.nodes.editor.toolbars.menus.items.file.export;

import com.desive.nodes.TabFactory;
import com.desive.nodes.editor.tabs.EditorTab;
import com.desive.nodes.editor.toolbars.menus.MdPageMenuItem;
import com.desive.stages.dialogs.DialogFactory;
import com.desive.utilities.constants.Dictionary;

import java.io.IOException;

/*
 Created by Jack DeSive on 11/1/2017 at 9:54 PM
*/
public class EditorExportJiraItem extends MdPageMenuItem {

    public EditorExportJiraItem(Dictionary dictionary, TabFactory tabFactory, DialogFactory dialogFactory) {
        super(dictionary.TOOLBAR_EDITOR_EXPORT_JIRA_ITEM);

        this.setOnAction(event -> getClickAction(dictionary, tabFactory, dialogFactory));
    }

    @Override
    public void getClickAction(final Dictionary dictionary, final TabFactory tabFactory, final DialogFactory dialogFactory) {
        EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
        try {
            if(currTab.getEditorPane().saveJira()) {
                dialogFactory.buildConfirmationDialogBox(
                        dictionary.DIALOG_EXPORT_SUCCESS_TITLE,
                        dictionary.DIALOG_EXPORT_SUCCESS_JIRA_CONTENT
                ).showAndWait();
            }
        } catch (IOException e1) {
            dialogFactory.buildExceptionDialogBox(
                    dictionary.DIALOG_EXCEPTION_TITLE,
                    dictionary.DIALOG_EXCEPTION_EXPORT_JIRA_CONTENT,
                    e1.getMessage(),
                    e1
            ).showAndWait();
        }
    }

}
