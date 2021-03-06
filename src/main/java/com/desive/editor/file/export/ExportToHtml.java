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

package com.desive.editor.file.export;

import com.desive.editor.file.SaveMachine;
import com.desive.nodes.editor.toolbars.EditorToolBar;
import com.desive.utilities.Utils;
import com.desive.utilities.constants.Dictionary;
import com.desive.utilities.constants.FileExtensionFilters;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/*
 Created by Jack DeSive on 11/18/2017 at 6:27 PM
*/
public class ExportToHtml extends SaveMachine{

    public ExportToHtml(Stage primaryStage, EditorToolBar toolbar) {
        super(primaryStage, toolbar);
    }

    public boolean save(File file, String content, boolean style, Dictionary dictionary) throws IOException {
        return super.saveFile(
                file,
                FileExtensionFilters.HTML,
                Utils.wrapWithHtmlDocType(content),
                style ? dictionary.DIALOG_EXPORT_SUCCESS_HTML_CSS_CONTENT : dictionary.DIALOG_EXPORT_SUCCESS_HTML_CONTENT
        );
    }
}
