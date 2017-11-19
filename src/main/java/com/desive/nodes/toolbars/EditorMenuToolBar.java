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

package com.desive.nodes.toolbars;

import com.desive.markdown.MarkdownParser;
import com.desive.nodes.TabFactory;
import com.desive.nodes.toolbars.menus.ToolBarMenus;
import com.desive.nodes.toolbars.menus.items.editor.edit.EditorRedoItem;
import com.desive.nodes.toolbars.menus.items.editor.edit.EditorUndoItem;
import com.desive.nodes.toolbars.menus.items.editor.file.EditorExitItem;
import com.desive.nodes.toolbars.menus.items.editor.file.EditorNewItem;
import com.desive.nodes.toolbars.menus.items.editor.file.EditorSaveAsItem;
import com.desive.nodes.toolbars.menus.items.editor.file.EditorSaveItem;
import com.desive.nodes.toolbars.menus.items.editor.file.export.*;
import com.desive.nodes.toolbars.menus.items.editor.file.imports.EditorImportFileItem;
import com.desive.nodes.toolbars.menus.items.editor.file.imports.EditorImportUrlItem;
import com.desive.nodes.toolbars.menus.items.editor.file.open.EditorOpenFileItem;
import com.desive.nodes.toolbars.menus.items.editor.file.open.EditorOpenUrlItem;
import com.desive.nodes.toolbars.menus.items.editor.help.EditorHelpPageItem;
import com.desive.nodes.toolbars.menus.items.editor.help.EditorSettingsItem;
import com.desive.nodes.toolbars.menus.items.editor.help.EditorTestPageItem;
import com.desive.nodes.toolbars.menus.items.editor.view.EditorPrettifyItem;
import com.desive.nodes.toolbars.menus.items.editor.view.EditorRefreshViewItem;
import com.desive.nodes.toolbars.menus.items.editor.view.extensions.*;
import com.desive.stages.dialogs.DialogFactory;
import com.desive.utilities.Dictionary;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.desive.utilities.constants.Shortcut.*;

/*
 Created by Jack DeSive on 10/8/2017 at 1:56 PM
*/
public class EditorMenuToolBar extends MenuBar {

    private final Logger logger = LoggerFactory.getLogger(EditorMenuToolBar.class);

    public EditorMenuToolBar(TabFactory tabFactory,
                             DialogFactory dialogFactory,
                             Dictionary dictionary,
                             ToolBarMenus menus,
                             MarkdownParser markdownParser,
                             Stage primaryStage,
                             Stage settingsStage) {

        this.log(dictionary.TOOLBAR_EDITOR_FILE_MENU);
        menus.getEditorFileMenu().getItems().addAll(
                new EditorNewItem(dictionary, NEW_FILE_EDITOR, tabFactory),
                menus.getEditorOpenSubmenu(),
                new SeparatorMenuItem(),
                menus.getEditorImportSubmenu(),
                menus.getEditorExportSubmenu(),
                new SeparatorMenuItem(),
                new EditorSaveItem(dictionary, SAVE_EDITOR, tabFactory, dialogFactory),
                new EditorSaveAsItem(dictionary, SAVE_AS_EDITOR, tabFactory, dialogFactory),
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
                new EditorPrettifyItem(dictionary, tabFactory),
                new SeparatorMenuItem(),
                menus.getEditorExtensionsMenu()
        );

        this.log(dictionary.TOOLBAR_EDITOR_HELP_MENU);
        menus.getEditorHelpMenu().getItems().addAll(
                new EditorHelpPageItem(dictionary, tabFactory),
                new EditorTestPageItem(dictionary, tabFactory),
                new SeparatorMenuItem(),
                new EditorSettingsItem(dictionary, OPEN_SETTINGS_STAGE, settingsStage)
        );

        this.log(dictionary.TOOLBAR_EDITOR_EXTENSIONS_MENU);
        menus.getEditorExtensionsMenu().getItems().addAll(
                //Enabled by default
                new EditorExtAbbreviationItem(dictionary, markdownParser, tabFactory, true),
                new EditorExtAnchorLinkItem(dictionary, markdownParser, tabFactory, true),
                new EditorExtAutoLinkItem(dictionary, markdownParser,tabFactory, true),
                new EditorExtTablesItem(dictionary, markdownParser, tabFactory, true),
                new EditorExtTaskListItem(dictionary, markdownParser, tabFactory, true),
                new EditorExtStrikethroughSubscriptItem(dictionary, markdownParser, tabFactory, true),

                // Not enabled by default
                new EditorExtGFMUsersItem(dictionary, markdownParser, tabFactory, false),
                new EditorExtGFMIssuesItem(dictionary, markdownParser, tabFactory, false),
                new EditorExtSuperscriptItem(dictionary, markdownParser, tabFactory, false),
                new EditorExtTableOfContentsItem(dictionary, markdownParser, tabFactory, false),
                new EditorExtWikiLinkItem(dictionary, markdownParser, tabFactory, false)

        );

        this.log(dictionary.TOOLBAR_EDITOR_IMPORT_MENU);
        menus.getEditorImportSubmenu().getItems().addAll(
                new EditorImportFileItem(dictionary, IMPORT_FILE_EDITOR, primaryStage, tabFactory, dialogFactory),
                new EditorImportUrlItem(dictionary, IMPORT_URL_EDITOR, tabFactory, dialogFactory)
        );

        this.log(dictionary.TOOLBAR_EDITOR_EXPORT_MENU);
        menus.getEditorExportSubmenu().getItems().addAll(
                new EditorExportDocxItem(dictionary, tabFactory, dialogFactory), // Docx
                new EditorExportPdfItem(dictionary, tabFactory, dialogFactory, true), // PDF/CSS
                new EditorExportPdfItem(dictionary, tabFactory, dialogFactory, false), // PDF
                new EditorExportJiraItem(dictionary, tabFactory, dialogFactory), // JIRA
                new EditorExportConfluenceItem(dictionary, tabFactory, dialogFactory), // Confluence
                new EditorExportYoutrackItem(dictionary, tabFactory, dialogFactory), // Youtrack
                new EditorExportPlainTextItem(dictionary, tabFactory, dialogFactory), // Plain Text
                new EditorExportHtmlItem(dictionary, tabFactory, dialogFactory, false), // HTML
                new EditorExportHtmlItem(dictionary, tabFactory, dialogFactory, true) // HTML/CSS
        );

        this.log(dictionary.TOOLBAR_EDITOR_OPEN_MENU);
        menus.getEditorOpenSubmenu().getItems().addAll(
                new EditorOpenFileItem(dictionary, OPEN_FILE_EDITOR, primaryStage, tabFactory, dialogFactory),
                new EditorOpenUrlItem(dictionary, OPEN_URL_EDITOR, primaryStage, tabFactory, dialogFactory)
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