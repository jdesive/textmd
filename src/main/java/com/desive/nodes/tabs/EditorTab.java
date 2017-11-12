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

package com.desive.nodes.tabs;

import com.desive.nodes.TabFactory;
import com.desive.nodes.editor.EditorPane;
import com.desive.utilities.Utils;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 Created by Jack DeSive on 10/13/2017 at 6:59 PM
*/
public class EditorTab extends Tab {

    private Logger logger = LoggerFactory.getLogger(EditorTab.class);

    private EditorPane editorPane;

    public EditorTab(EditorPane editorPane, TabFactory tabFactory, Stage primaryStage) {

        this.editorPane = editorPane;

        this.setOnCloseRequest(e -> {
            if(getEditorPane().exit(primaryStage)) {
                logger.debug("Closing tab {}", getEditorPane().getFile().getPath());
            }else {
                e.consume();
            }
        });

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
        text.setFill(Utils.getDefaultTextColor());
        this.setGraphic(new StackPane(text));
        this.getGraphic().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
    }

    public EditorPane getEditorPane() {
        return editorPane;
    }
}
