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

package com.desive.nodes.menus.items.editor.file.open;

import com.desive.nodes.EditorPane;
import com.desive.nodes.TabFactory;
import com.desive.nodes.menus.MdPageMenuItem;
import com.desive.nodes.tabs.EditorTab;
import com.desive.utilities.Dictionary;
import com.desive.utilities.FileExtensionFilters;
import com.desive.utilities.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 Created by Jack DeSive on 11/1/2017 at 9:54 PM
*/
public class EditorOpenFileItem extends MdPageMenuItem {

    public EditorOpenFileItem(Dictionary dictionary, KeyCombination accelerator, Stage stage, TabFactory tabFactory) {
        super(dictionary.TOOLBAR_EDITOR_OPEN_FILE_ITEM);
        this.setAccelerator(accelerator);
        this.setOnAction(this.getClickAction(dictionary, stage, tabFactory));
    }

    @Override
    public EventHandler<ActionEvent> getClickAction(final Dictionary dictionary, final Stage stage, final TabFactory tabFactory) {
        return event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(FileExtensionFilters.MARKDOWN);
            File file = fileChooser.showOpenDialog(stage);
            if(file != null){
                try {
                    String content =  new Scanner(file).useDelimiter("\\Z").next();
                    EditorPane editorPane = new EditorPane(dictionary, content);
                    EditorTab newTab = new EditorTab(editorPane, stage);
                    newTab.getEditorPane().setFile(file);
                    tabFactory.addNewEditorTab(newTab);
                } catch (FileNotFoundException e1) {
                    Utils.getExceptionDialogBox(
                            dictionary.DIALOG_EXCEPTION_TITLE,
                            dictionary.DIALOG_EXCEPTION_OPENING_MARKDOWN_CONTENT,
                            e1.getMessage(),
                            e1,
                            stage
                    ).showAndWait();
                }
            }
        };
    }

}
