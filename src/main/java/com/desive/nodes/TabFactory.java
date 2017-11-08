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

package com.desive.nodes;

import com.desive.markdown.MarkdownParser;
import com.desive.nodes.editor.EditorPane;
import com.desive.nodes.editor.EditorTabPane;
import com.desive.nodes.tabs.EditorTab;
import com.desive.stages.dialogs.DialogFactory;
import com.desive.utilities.Dictionary;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 Created by Jack DeSive on 10/13/2017 at 7:04 PM
*/
public class TabFactory {

    private EditorTabPane tabPane = new EditorTabPane();
    private MarkdownParser markdownParser;
    private Dictionary dictionary;
    private DialogFactory dialogFactory;
    private Stage ownerStage;

    public TabFactory(Dictionary dictionary, DialogFactory dialogFactory, Stage ownerStage) {
       this.dictionary = dictionary;
       this.dialogFactory = dialogFactory;
       this.ownerStage = ownerStage;
    }

    public void addNewEditorTab(File file) throws FileNotFoundException {
        addNewEditorTab(file, new Scanner(file).useDelimiter("\\Z").next());
    }

    public void addNewEditorTab(File file, String fileContent) {
        EditorPane editorPane = new EditorPane(dictionary, dialogFactory, markdownParser, fileContent);
        EditorTab newTab = new EditorTab(editorPane, ownerStage);
        newTab.getEditorPane().setFile(file);
        addNewTab(newTab);
    }

    public void addNewTab(EditorTab editorTab) {
        editorTab.computeTabName();
        tabPane.getTabs().add(editorTab);
        tabPane.getSelectionModel().select(editorTab);
    }

    public Tab getSelectedTab(){
        return tabPane.getSelectionModel().getSelectedItem();
    }

    public EditorTabPane getTabPane() {
        return tabPane;
    }

    public void setMarkdownParser(MarkdownParser markdownParser) {
        this.markdownParser = markdownParser;
    }

}
