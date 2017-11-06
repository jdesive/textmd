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

import com.desive.nodes.TabFactory;
import com.desive.nodes.tabs.EditorTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 Created by Jack DeSive on 10/24/2017 at 10:33 AM
*/
public class Settings {

    private final static Logger LOGGER = LoggerFactory.getLogger(Settings.class);

    private static TabFactory tabFactory;

    // Settings View
    public static boolean ALWAYS_PRETTIFY_CODE_VIEW = false;
    public static int VIEW_REFRESH_RATE = 1;

    // Settings Editor
    public static int EDITOR_HIGHLIGHT_REFRESH_RATE = 500;

    // General Settings
    public static boolean LOAD_FONTS_AT_RUNTIME = true;

    public Settings(TabFactory tabFactoryParam) {
        tabFactory = tabFactoryParam;
    }

    public static void setAlwaysPrettifyCodeView(boolean value) {
        LOGGER.debug("Setting \'Always Prettify Code View\' to \'{}\'", value);
        ALWAYS_PRETTIFY_CODE_VIEW = value;
    }

    public static void setViewRefreshRate(int value) {
        LOGGER.debug("Setting \'View Refresh Rate\' to \'{}\'", value);
        VIEW_REFRESH_RATE = value;
        ((EditorTab) tabFactory.getSelectedTab()).getEditorPane().createSyncTimer(value);
    }

    public static void setEditorHighlightRefreshRate(int value) {
        LOGGER.debug("Setting \'Editor Highlight Refresh Rate\' to \'{}\'", value);
        EDITOR_HIGHLIGHT_REFRESH_RATE = value;
        ((EditorTab) tabFactory.getSelectedTab()).getEditorPane().createEditorHighlightSubscription(value);
    }

    public static void setLoadFontsAtRuntime(boolean value) {
        LOGGER.debug("Setting \'Load Fonts at runtime\' to \'{}\'", value);
        LOAD_FONTS_AT_RUNTIME = value;
    }


}
