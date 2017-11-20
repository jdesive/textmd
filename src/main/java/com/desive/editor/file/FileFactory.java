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

package com.desive.editor.file;

import com.desive.editor.file.export.*;
import com.desive.nodes.editor.toolbars.EditorToolBar;
import javafx.stage.Stage;

/*
 Created by Jack DeSive on 11/18/2017 at 6:38 PM
*/
public class FileFactory {

    private SaveMarkdown saveMarkdown;
    private ExportToDocx exportToDocx;
    private ExportToPdf exportToPdf;
    private ExportToJira exportToJira;
    private ExportToConfluence exportToConfluence;
    private ExportToHtml exportToHtml;
    private ExportToYoutrack exportToYoutrack;
    private ExportToText exportToText;

    public FileFactory(Stage primaryStage, EditorToolBar toolBar) {
        saveMarkdown = new SaveMarkdown(primaryStage, toolBar);
        exportToDocx = new ExportToDocx(primaryStage, toolBar);
        exportToPdf = new ExportToPdf(primaryStage, toolBar);
        exportToJira = new ExportToJira(primaryStage, toolBar);
        exportToConfluence = new ExportToConfluence(primaryStage, toolBar);
        exportToHtml = new ExportToHtml(primaryStage, toolBar);
        exportToYoutrack = new ExportToYoutrack(primaryStage, toolBar);
        exportToText = new ExportToText(primaryStage, toolBar);
    }

    public SaveMarkdown markdown() {
        return saveMarkdown;
    }

    public ExportToDocx office() {
        return exportToDocx;
    }

    public ExportToPdf pdf() {
        return exportToPdf;
    }

    public ExportToJira jira() {
        return exportToJira;
    }

    public ExportToConfluence confluence() {
        return exportToConfluence;
    }

    public ExportToHtml html() {
        return exportToHtml;
    }

    public ExportToYoutrack youtrack() {
        return exportToYoutrack;
    }

    public ExportToText plainText() {
        return exportToText;
    }

}
