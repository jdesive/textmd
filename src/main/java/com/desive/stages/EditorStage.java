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
import com.desive.nodes.menus.ToolBarMenus;
import com.desive.nodes.tabs.EditorTab;
import com.desive.utilities.Dictionary;
import com.desive.utilities.Utils;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/*
 Created by Jack DeSive on 10/20/2017 at 1:34 AM
*/
public class EditorStage extends Stage {

    private EditorPane editorPane;
    private EditorToolBar toolbar;

    private final Logger logger = LoggerFactory.getLogger(EditorStage.class);

    public EditorStage(Dictionary dictionary, Stage settingsStage) {

        ToolBarMenus toolBarMenus = new ToolBarMenus(dictionary);
        editorPane = new EditorPane(dictionary, Utils.getSampleText());
        toolbar = new EditorToolBar(TabFactory.getInstance(), dictionary, toolBarMenus,this, settingsStage);
        EditorTab tempTab = new EditorTab(editorPane, this);

        TabFactory.addNewEditorTab(tempTab);

        BorderPane root = new BorderPane();
        root.setTop(toolbar);
        root.setCenter(TabFactory.getTabPane());
        StackPane container = new StackPane();
        container.getChildren().add(root);

        Scene scene = new Scene(container, 800, 800);
        scene.getStylesheets().add("css/window.css");
        this.setScene(scene);

        this.setTitle(dictionary.STAGE_EDITOR_TITLE);
        this.getIcons().add(new Image("assets/favicon.png"));
        this.show();
        this.centerOnScreen();

        this.widthProperty().addListener(event -> {
            toolbar.setPrefSize(this.getWidth(), 25);
            editorPane.getEditor().setPrefSize(this.getWidth(), this.getHeight()-25);
            editorPane.getWebView().setPrefSize(this.getWidth(), this.getHeight()-25);
        });

        this.setOnCloseAction();

    }

    private void setOnCloseAction(){
        this.setOnCloseRequest(e -> {
            List<Tab> tabs = FXCollections.observableArrayList(TabFactory.getTabPane().getTabs());
            Collections.reverse(tabs);
            tabs.forEach(t -> {
                EditorTab eTab = ((EditorTab) t);
                if(!eTab.getEditorPane().exit(this)){
                    e.consume();
                }else{
                    logger.debug("Closing tab {}", eTab.getEditorPane().getFile().getPath());
                    TabFactory.getTabPane().getTabs().remove(eTab);
                }
            });
        });
    }

}