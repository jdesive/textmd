package com.desive.nodes;

import javafx.geometry.NodeOrientation;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/*
 Created by Jack DeSive on 10/13/2017 at 6:59 PM
*/
public class EditorTab extends Tab {

    private EditorPane editorPane;

    public EditorTab(EditorPane editorPane) {

        this.editorPane = editorPane;

        this.setContent(this.editorPane);
    }

    public void computeTabName(){
        String filePath = editorPane.getFile().getPath();
        Text text;
        if(filePath.length() > 20){
            text = new Text(filePath.substring(filePath.length()-20, filePath.length()));
        }else{
            text = new Text(filePath);
        }
        text.setFill(Color.valueOf("f8f8f2"));
        this.setGraphic(new StackPane(text));
        this.getGraphic().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
    }

    public EditorPane getEditorPane() {
        return editorPane;
    }
}
