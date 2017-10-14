package com.desive.nodes;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/*
 Created by Jack DeSive on 10/13/2017 at 7:04 PM
*/
public class TabFactory {

    private TabPane tabPane;

    public TabFactory(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void addNewEditorTab(EditorTab editorTab) {
        editorTab.computeTabName();
        this.tabPane.getTabs().add(editorTab);
        this.tabPane.getSelectionModel().select(editorTab);
    }

    public Tab getSelectedTab(){
        return this.tabPane.getSelectionModel().getSelectedItem();
    }

}
