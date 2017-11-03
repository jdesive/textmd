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

package com.desive.utilities;

import javafx.scene.input.KeyCombination;

/*
 Created by Jack DeSive on 10/24/2017 at 8:05 PM
*/
public class Shortcut {

    public final static KeyCombination REFRESH_EDITOR_VIEW = KeyCombination.valueOf("F5");
    public final static KeyCombination OPEN_SETTINGS_STAGE = KeyCombination.valueOf("SHORTCUT+ALT+S");
    public final static KeyCombination UNDO_EDITOR = KeyCombination.valueOf("SHORTCUT+Z");
    public final static KeyCombination REDO_EDITOR = KeyCombination.valueOf("SHORTCUT+SHIFT+Z");
    public final static KeyCombination NEW_FILE_EDITOR = KeyCombination.valueOf("SHORTCUT+N");
    public final static KeyCombination SAVE_EDITOR = KeyCombination.valueOf("SHORTCUT+S");
    public final static KeyCombination SAVE_AS_EDITOR = KeyCombination.valueOf("SHORTCUT+SHIFT+S");
    public final static KeyCombination IMPORT_FILE_EDITOR = KeyCombination.valueOf("SHORTCUT+I");
    public final static KeyCombination IMPORT_URL_EDITOR = KeyCombination.valueOf("SHORTCUT+SHIFT+I");
    public final static KeyCombination OPEN_FILE_EDITOR = KeyCombination.valueOf("SHORTCUT+O");
    public final static KeyCombination OPEN_URL_EDITOR = KeyCombination.valueOf("SHORTCUT+SHIFT+O");

}
