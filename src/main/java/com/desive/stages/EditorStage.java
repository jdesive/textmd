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

package com.desive.stages;

import com.desive.nodes.EditorPane;
import com.desive.nodes.EditorToolBar;
import com.desive.nodes.TabFactory;
import com.desive.nodes.tabs.EditorTab;
import com.desive.utilities.Dictionary;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/*
 Created by Jack DeSive on 10/20/2017 at 1:34 AM
*/
public class EditorStage extends Stage {

    BorderPane root = new BorderPane();
    StackPane container = new StackPane();
    EditorPane editorPane = new EditorPane();
    EditorToolBar toolbar;
    EditorTab tempTab;

    public EditorStage(Stage settingsStage) {

        toolbar = new EditorToolBar(TabFactory.getInstance(), this, settingsStage);
        tempTab = new EditorTab(editorPane);

        TabFactory.addNewEditorTab(tempTab);

        root.setTop(toolbar);
        root.setCenter(TabFactory.getTabPane());
        container.getChildren().add(root);

        Scene scene = new Scene(container, 800, 800);
        scene.getStylesheets().add("css/window.css");
        this.setScene(scene);

        this.setTitle(Dictionary.STAGE_EDITOR_TITLE);
        this.getIcons().add(new Image("assets/favicon.png"));
        this.show();
        this.centerOnScreen();

        this.widthProperty().addListener((event) -> {
            toolbar.setPrefSize(this.getWidth(), 25);
            editorPane.getEditor().setPrefSize(this.getWidth(), this.getHeight()-25);
            editorPane.getWebView().setPrefSize(this.getWidth(), this.getHeight()-25);
        });


    }
}
