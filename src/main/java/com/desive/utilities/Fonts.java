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

import javafx.scene.text.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Arrays;

/*
 Created by Jack DeSive on 10/27/2017 at 11:11 PM
*/
public class Fonts {

    public final String COURIER_PRIMAL_URL = "https://github.com/localredhead/courier-primal/blob/master/Courier%20Primal.ttf?raw=true",
            COURIER_PRIMAL_ITALICS_URL = "https://github.com/localredhead/courier-primal/blob/master/Courier%20Primal%20Italic.ttf?raw=true",
            COURIER_PRIMAL_BOLD_URL = "https://github.com/localredhead/courier-primal/blob/master/Courier%20Primal%20Bold.ttf?raw=true",
            COURIER_PRIMAL_BOLD_ITALICS_URL = "https://github.com/localredhead/courier-primal/blob/master/Courier%20Primal%20Bold%20Italic.ttf?raw=true",
            COURIER_PRIMAL_NAME = "Courier Primal",
            COURIER_REGULAR_NAME = "Courier Regular",
            COURIER_PRIMAL_ITALICS_NAME = "Courier Primal Italic",
            COURIER_PRIMAL_BOLD_NAME = "Courier Primal Bold",
            COURIER_PRIMAL_BOLD_ITALICS_NAME = "Courier Primal Bold Italic";

    private final Logger logger = LoggerFactory.getLogger(Fonts.class);

    public boolean fontExits(String fontName) {
        return Arrays.stream(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames())
                .anyMatch(font -> font.equals(fontName));
    }

    public void registerFont(String fontUrl, String name) {
        logger.debug("Loading font \'{}\'", name);
        Font.loadFont(fontUrl, 14);
    }
}
