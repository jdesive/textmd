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

import com.desive.nodes.menus.ToolBarMenus;
import com.desive.nodes.menus.items.editor.edit.EditorRedoItem;
import com.desive.nodes.menus.items.editor.edit.EditorUndoItem;
import com.desive.nodes.menus.items.editor.file.EditorExitItem;
import com.desive.nodes.menus.items.editor.file.EditorNewItem;
import com.desive.nodes.menus.items.editor.file.EditorSaveAsItem;
import com.desive.nodes.menus.items.editor.file.EditorSaveItem;
import com.desive.nodes.menus.items.editor.file.export.*;
import com.desive.nodes.menus.items.editor.file.open.EditorOpenFileItem;
import com.desive.nodes.menus.items.editor.file.open.EditorOpenUrlItem;
import com.desive.nodes.menus.items.editor.help.EditorHelpPageItem;
import com.desive.nodes.menus.items.editor.help.EditorSettingsItem;
import com.desive.nodes.menus.items.editor.help.EditorTestPageItem;
import com.desive.nodes.menus.items.editor.view.EditorPrettifyItem;
import com.desive.nodes.menus.items.editor.view.EditorRefreshViewItem;
import com.desive.utilities.Dictionary;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.desive.utilities.Shortcut.*;

/*
 Created by Jack DeSive on 10/8/2017 at 1:56 PM
*/
public class EditorToolBar extends MenuBar {

    private final Logger logger = LoggerFactory.getLogger(EditorToolBar.class);

    public EditorToolBar(TabFactory tabFactory,
                         Dictionary dictionary,
                         ToolBarMenus menus,
                         Stage primaryStage,
                         Stage settingsStage) {

        this.log(dictionary.TOOLBAR_EDITOR_FILE_MENU);
        menus.getEditorFileMenu().getItems().addAll(
                new EditorNewItem(dictionary, NEW_FILE_EDITOR, primaryStage, tabFactory),
                menus.getEditorOpenSubmenu(),
                menus.getEditorImportSubmenu(),
                menus.getEditorExportSubmenu(),
                new EditorSaveItem(dictionary, SAVE_EDITOR, primaryStage, tabFactory),
                new EditorSaveAsItem(dictionary, SAVE_AS_EDITOR, primaryStage, tabFactory),
                new EditorExitItem(dictionary, primaryStage, tabFactory)
        );

        this.log(dictionary.TOOLBAR_EDITOR_EDIT_MENU);
        menus.getEditorEditMenu().getItems().addAll(
                new EditorUndoItem(dictionary, UNDO_EDITOR, tabFactory),
                new EditorRedoItem(dictionary, REDO_EDITOR, tabFactory)
        );

        this.log(dictionary.TOOLBAR_EDITOR_VIEW_MENU);
        menus.getEditorViewMenu().getItems().addAll(
                new EditorRefreshViewItem(dictionary, REFRESH_EDITOR_VIEW, tabFactory),
                new EditorPrettifyItem(dictionary, tabFactory)
        );

        this.log(dictionary.TOOLBAR_EDITOR_HELP_MENU);
        menus.getEditorHelpMenu().getItems().addAll(
                new EditorHelpPageItem(dictionary, primaryStage, tabFactory),
                new EditorTestPageItem(dictionary, primaryStage, tabFactory),
                new EditorSettingsItem(dictionary, OPEN_SETTINGS_STAGE, settingsStage)
        );

        this.log(dictionary.TOOLBAR_EDITOR_IMPORT_MENU);
        menus.getEditorImportSubmenu().getItems().addAll(
                new EditorOpenFileItem(dictionary, IMPORT_FILE_EDITOR, primaryStage, tabFactory),
                new EditorOpenUrlItem(dictionary, IMPORT_URL_EDITOR, primaryStage, tabFactory)
        );

        this.log(dictionary.TOOLBAR_EDITOR_EXPORT_MENU);
        menus.getEditorExportSubmenu().getItems().addAll(
                new EditorExportDocxItem(dictionary, primaryStage, tabFactory), // Docx
                new EditorExportPdfItem(dictionary, primaryStage, tabFactory, false), // PDF
                new EditorExportPdfItem(dictionary, primaryStage, tabFactory, true), // PDF/CSS
                new EditorExportJiraItem(dictionary, primaryStage, tabFactory), // JIRA
                new EditorExportConfluenceItem(dictionary, primaryStage, tabFactory), // Confluence
                new EditorExportYoutrackItem(dictionary, primaryStage, tabFactory), // Youtrack
                new EditorExportPlainTextItem(dictionary, primaryStage, tabFactory), // Plain Text
                new EditorExportHtmlItem(dictionary, primaryStage, tabFactory, false), // HTML
                new EditorExportHtmlItem(dictionary, primaryStage, tabFactory, true) // HTML/CSS
        );

        this.log(dictionary.TOOLBAR_EDITOR_OPEN_MENU);
        menus.getEditorOpenSubmenu().getItems().addAll(
                new EditorOpenFileItem(dictionary, OPEN_FILE_EDITOR, primaryStage, tabFactory),
                new EditorOpenUrlItem(dictionary, OPEN_URL_EDITOR, primaryStage, tabFactory)
        );

        logger.debug("Building toolbar");
        this.getMenus().addAll(
                menus.getEditorFileMenu(),
                menus.getEditorEditMenu(),
                menus.getEditorViewMenu(),
                menus.getEditorHelpMenu()
        );
    }

    private void log(String param) {
        logger.debug("Building \'{}\' menu", param);
    }

}