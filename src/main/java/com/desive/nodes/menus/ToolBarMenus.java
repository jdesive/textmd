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

package com.desive.nodes.menus;

import com.desive.utilities.Dictionary;
import javafx.scene.control.Menu;

/*
 Created by Jack DeSive on 11/1/2017 at 9:41 PM
*/
public class ToolBarMenus {

    private Menu EDITOR_FILE_MENU;
    private Menu EDITOR_EDIT_MENU;
    private Menu EDITOR_VIEW_MENU;
    private Menu EDITOR_HELP_MENU;
    private Menu EDITOR_IMPORT_SUBMENU;
    private Menu EDITOR_EXPORT_SUBMENU;
    private Menu EDITOR_OPEN_SUBMENU;

    public ToolBarMenus(Dictionary dictionary) {
        this.EDITOR_FILE_MENU = new Menu(dictionary.TOOLBAR_EDITOR_FILE_MENU);
        this.EDITOR_EDIT_MENU = new Menu(dictionary.TOOLBAR_EDITOR_EDIT_MENU);
        this.EDITOR_VIEW_MENU = new Menu(dictionary.TOOLBAR_EDITOR_VIEW_MENU);
        this.EDITOR_HELP_MENU = new Menu(dictionary.TOOLBAR_EDITOR_HELP_MENU);
        this.EDITOR_OPEN_SUBMENU = new Menu(dictionary.TOOLBAR_EDITOR_OPEN_MENU);
        this.EDITOR_IMPORT_SUBMENU = new Menu(dictionary.TOOLBAR_EDITOR_IMPORT_MENU);
        this.EDITOR_EXPORT_SUBMENU = new Menu(dictionary.TOOLBAR_EDITOR_EXPORT_MENU);
    }

    public Menu getEditorFileMenu() {
        return EDITOR_FILE_MENU;
    }

    public Menu getEditorEditMenu() {
        return EDITOR_EDIT_MENU;
    }

    public Menu getEditorViewMenu() {
        return EDITOR_VIEW_MENU;
    }

    public Menu getEditorHelpMenu() {
        return EDITOR_HELP_MENU;
    }

    public Menu getEditorImportSubmenu() {
        return EDITOR_IMPORT_SUBMENU;
    }

    public Menu getEditorExportSubmenu() {
        return EDITOR_EXPORT_SUBMENU;
    }

    public Menu getEditorOpenSubmenu() {
        return EDITOR_OPEN_SUBMENU;
    }
}
