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

package com.desive.nodes.menus.items.editor.file.export;

import com.desive.nodes.TabFactory;
import com.desive.nodes.menus.MdPageMenuItem;
import com.desive.nodes.tabs.EditorTab;
import com.desive.utilities.Dictionary;
import com.desive.utilities.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.io.IOException;

/*
 Created by Jack DeSive on 11/1/2017 at 9:54 PM
*/
public class EditorExportConfluenceItem extends MdPageMenuItem {

    public EditorExportConfluenceItem(Dictionary dictionary, Stage stage, TabFactory tabFactory) {
        super(dictionary.TOOLBAR_EDITOR_EXPORT_CONFLUENCE_ITEM);

        this.setOnAction(this.getClickAction(dictionary, stage, tabFactory));
    }

    @Override
    public EventHandler<ActionEvent> getClickAction(final Dictionary dictionary, final Stage stage, final TabFactory tabFactory) {
        return event -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            try {
                if(currTab.getEditorPane().saveConfluenceMarkup(stage)) {
                    Utils.getConfirmationDialog(
                            dictionary.DIALOG_EXPORT_SUCCESS_TITLE,
                            dictionary.DIALOG_EXPORT_SUCCESS_CONFLUENCE_CONTENT,
                            stage
                    ).showAndWait();
                }
            } catch (IOException e1) {
                Utils.getExceptionDialogBox(
                        dictionary.DIALOG_EXCEPTION_TITLE,
                        dictionary.DIALOG_EXCEPTION_EXPORT_CONFLUENCE_CONTENT,
                        e1.getMessage(),
                        e1,
                        stage
                ).showAndWait();
            }
        };
    }

}
