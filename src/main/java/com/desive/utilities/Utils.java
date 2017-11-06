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

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Scanner;

/*
 Created by Jack DeSive on 10/8/2017 at 1:27 PM
*/
public class Utils {

    private static ClassLoader loader;

    public Utils() {
        loader = getClass().getClassLoader();
    }

    public static Paint getDefaultTextColor() {
        return Color.valueOf("f8f8f2");
    }

    public static String getDefaultFileName(){
        return "untitled.md";
    }

    public static String getHelpPageFileName(){
        return "help.md";
    }

    public static String getTestPageFileName(){
        return "test.md";
    }

    public static String getSampleText(){
        return new Scanner(loader.getResourceAsStream("assets/defaults/sample.md")).useDelimiter("\\Z").next();
    }

    public static String getHelpText(){
        return new Scanner(loader.getResourceAsStream("assets/defaults/help.md")).useDelimiter("\\Z").next();
    }

    public static String getTestText(){
        return new Scanner(loader.getResourceAsStream("assets/defaults/test.md")).useDelimiter("\\Z").next();
    }

    public static String getNewPageText(){
        return new Scanner(loader.getResourceAsStream("assets/defaults/new_page.md")).useDelimiter("\\Z").next();
    }

    public static String getWebViewCss(String background){
        return new Scanner(loader.getResourceAsStream("css/editor_view.css"))
                .useDelimiter("\\Z").next()
                .replace("${code.background}", background);
    }

    public static String wrapWithHtmlDocType(String content){
        return "<!DOCTYPE html>\n<html>\n" + content + "\n</html>";
    }

}
