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

package com.desive.utilities.constants;

import javafx.scene.input.KeyCombination;

/*
 Created by Jack DeSive on 10/24/2017 at 8:05 PM
*/
public class Shortcut {

    public final static KeyCombination REFRESH_EDITOR_VIEW = KeyCombination.valueOf("F5"),
            OPEN_SETTINGS_STAGE = KeyCombination.valueOf("SHORTCUT+ALT+S"),
            UNDO_EDITOR = KeyCombination.valueOf("SHORTCUT+Z"),
            REDO_EDITOR = KeyCombination.valueOf("SHORTCUT+SHIFT+Z"),
            NEW_FILE_EDITOR = KeyCombination.valueOf("SHORTCUT+N"),
            SAVE_EDITOR = KeyCombination.valueOf("SHORTCUT+S"),
            SAVE_AS_EDITOR = KeyCombination.valueOf("SHORTCUT+SHIFT+S"),
            IMPORT_FILE_EDITOR = KeyCombination.valueOf("SHORTCUT+I"),
            IMPORT_URL_EDITOR = KeyCombination.valueOf("SHORTCUT+SHIFT+I"),
            OPEN_FILE_EDITOR = KeyCombination.valueOf("SHORTCUT+O"),
            OPEN_URL_EDITOR = KeyCombination.valueOf("SHORTCUT+SHIFT+O");

}
