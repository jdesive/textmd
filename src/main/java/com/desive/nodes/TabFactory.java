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

import com.desive.editor.exceptions.TabFactoryNotInitializedException;
import com.desive.markdown.MarkdownParser;
import com.desive.nodes.editor.EditorPane;
import com.desive.nodes.editor.EditorTabPane;
import com.desive.nodes.editor.tabs.EditorTab;
import com.desive.nodes.editor.toolbars.EditorToolBar;
import com.desive.nodes.editor.toolbars.nodes.CaretPositionPane;
import com.desive.stages.dialogs.DialogFactory;
import com.desive.utilities.Utils;
import com.desive.utilities.constants.Dictionary;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private EditorToolBar editorToolBar;

    private final static Logger logger = LoggerFactory.getLogger(TabFactory.class);

    public TabFactory(Dictionary dictionary, DialogFactory dialogFactory, Stage ownerStage) {
        this.dictionary = dictionary;
        this.dialogFactory = dialogFactory;
        this.ownerStage = ownerStage;
        addSampleEditorTab(); // temp

        // Reset the caret position pane on tab select
        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> CaretPositionPane.resetPosition(newTab != null ? ((EditorTab) newTab).getEditorPane() : null));

    }

    public void createAndAddNewEditorTab(File file) throws FileNotFoundException {
        createAndAddNewEditorTab(file, new Scanner(file).useDelimiter("\\Z").next());
    }

    public void createAndAddNewEditorTab(File file, String fileContent) {

        if(editorToolBar == null) {
            throw new TabFactoryNotInitializedException();
        }

        EditorPane editorPane = new EditorPane(dictionary, dialogFactory, markdownParser, editorToolBar, ownerStage, fileContent);
        EditorTab newTab = new EditorTab(editorPane);
        newTab.getEditorPane().setFile(file);
        addEditorTab(newTab);
    }

    private void addEditorTab(EditorTab editorTab) {
        editorTab.computeTabName();
        tabPane.addTab(editorTab);
    }

    public Tab getSelectedTab(){
        return tabPane.getSelectionModel().getSelectedItem();
    }

    private EditorTab getSelectedEditorTab() {
        if(getSelectedTab() instanceof EditorTab) {
            return ((EditorTab) getSelectedTab());
        }
        return null;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public void refreshSelectedTabView() {
        getSelectedEditorTab().getEditorPane().refreshWebView();
    }

    public boolean spellcheckSelectedTab() {
        return getSelectedEditorTab().getEditorPane().spellcheckDocument();
    }

    public void setMarkdownParser(MarkdownParser markdownParser) {
        this.markdownParser = markdownParser;
    }

    public void setEditorToolBar(EditorToolBar editorToolBar) {
        this.editorToolBar = editorToolBar;
    }

    // Lets populate in a better way.
    private void addSampleEditorTab() {
        Timeline sample = new Timeline(new KeyFrame(Duration.ONE, event -> createAndAddNewEditorTab(new File(Utils.getDefaultFileName()), Utils.getSampleText())));
        sample.playFromStart();
    }

}
