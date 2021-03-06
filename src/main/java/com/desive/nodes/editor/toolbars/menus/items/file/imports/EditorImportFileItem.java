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

package com.desive.nodes.editor.toolbars.menus.items.file.imports;

import com.desive.nodes.TabFactory;
import com.desive.nodes.editor.tabs.EditorTab;
import com.desive.nodes.editor.toolbars.menus.items.MdOpenPageMenuItem;
import com.desive.stages.dialogs.DialogFactory;
import com.desive.utilities.constants.Dictionary;
import com.desive.utilities.constants.FileExtensionFilters;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/*
 Created by Jack DeSive on 11/1/2017 at 9:54 PM
*/
public class EditorImportFileItem extends MdOpenPageMenuItem {

    public EditorImportFileItem(Dictionary dictionary, KeyCombination accelerator, Stage stage, TabFactory tabFactory, DialogFactory dialogFactory) {
        super(dictionary.TOOLBAR_EDITOR_IMPORT_FILE_ITEM);
        this.setAccelerator(accelerator);
        this.setOnAction(event -> getClickAction(dictionary, stage, tabFactory, dialogFactory));
    }

    @Override
    public void getClickAction(final Dictionary dictionary, final Stage stage, final TabFactory tabFactory, final DialogFactory dialogFactory) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(FileExtensionFilters.MARKDOWN);
        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            try {
                EditorTab tab = ((EditorTab)tabFactory.getSelectedTab());
                tab.getEditorPane().setContent(tab.getEditorPane().getContent() + "\n" + new Scanner(file).useDelimiter("\\Z").next());
            } catch (IOException e1) {
                dialogFactory.buildExceptionDialogBox(
                        dictionary.DIALOG_EXCEPTION_TITLE,
                        dictionary.DIALOG_EXCEPTION_IMPORT_CONTENT,
                        e1.getMessage(),
                        e1
                ).showAndWait();
            }
        }
    }

}
