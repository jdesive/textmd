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

import com.desive.markdown.MarkdownParser;
import com.desive.nodes.TabFactory;
import com.desive.nodes.editor.tabs.EditorTab;
import com.desive.nodes.editor.toolbars.EditorMenuToolBar;
import com.desive.nodes.editor.toolbars.EditorToolBar;
import com.desive.nodes.editor.toolbars.menus.ToolBarMenus;
import com.desive.stages.dialogs.DialogFactory;
import com.desive.utilities.Settings;
import com.desive.utilities.constants.Dictionary;
import javafx.collections.FXCollections;
import javafx.event.Event;
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

    private EditorMenuToolBar menuToolbar;
    private EditorToolBar toolBar;
    private MarkdownParser markdownParser;
    private TabFactory tabFactory;

    private Settings settings;

    private final Logger logger = LoggerFactory.getLogger(EditorStage.class);

    public EditorStage(Dictionary dictionary, DialogFactory dialogFactory, Stage settingsStage) {

        markdownParser = new MarkdownParser();
        tabFactory = new TabFactory(dictionary, dialogFactory, this);

        settings = new Settings(tabFactory);

        tabFactory.setMarkdownParser(markdownParser);

        ToolBarMenus toolBarMenus = new ToolBarMenus(dictionary);
        menuToolbar = new EditorMenuToolBar(tabFactory, dialogFactory, dictionary, toolBarMenus, markdownParser,this, settingsStage);
        toolBar = new EditorToolBar(dictionary, tabFactory);
        tabFactory.setEditorToolBar(toolBar);

        BorderPane root = new BorderPane(tabFactory.getTabPane());
        root.setTop(menuToolbar);
        root.setBottom(toolBar);
        StackPane container = new StackPane(root);

        Scene scene = new Scene(container, 800, 800);
        scene.getStylesheets().add("css/window.css");
        this.setScene(scene);

        this.setTitle(dictionary.STAGE_EDITOR_TITLE);
        this.getIcons().add(new Image("assets/favicon.png"));
        this.show();
        this.centerOnScreen();

        this.setOnCloseRequest(event -> getOnCloseAction(tabFactory, event));

    }

    private void getOnCloseAction(TabFactory tabFactory, Event event){
            List<Tab> tabs = FXCollections.observableArrayList(tabFactory.getTabPane().getTabs());
            Collections.reverse(tabs);
            tabs.forEach(t -> {
                EditorTab eTab = ((EditorTab) t);
                if(!eTab.getEditorPane().exit()){
                    event.consume();
                }else{
                    logger.debug("Closing tab {}", eTab.getEditorPane().getFile().getPath());
                    tabFactory.getTabPane().getTabs().remove(eTab);
                }
            });
    }

}
