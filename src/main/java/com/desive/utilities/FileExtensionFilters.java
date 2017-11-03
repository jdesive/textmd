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

import javafx.stage.FileChooser;

/*
 Created by Jack DeSive on 10/24/2017 at 9:21 PM
*/
public class FileExtensionFilters {

    public final static FileChooser.ExtensionFilter PDF = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"),
            MARKDOWN = new FileChooser.ExtensionFilter("Md files (*.md)", "*.md"),
            DOCX = new FileChooser.ExtensionFilter("Docx files (*.docx)", "*.docx"),
            HTML = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html"),
            TEXT = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");


}
