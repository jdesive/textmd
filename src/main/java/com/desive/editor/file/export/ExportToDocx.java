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
import com.desive.markdown.MarkdownParser;
import com.desive.nodes.editor.toolbars.EditorToolBar;
import com.desive.utilities.constants.FileExtensionFilters;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.docx4j.openpackaging.exceptions.Docx4JException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

/*
 Created by Jack DeSive on 11/18/2017 at 6:27 PM
*/
public class ExportToDocx extends SaveMachine{

    public ExportToDocx(Stage primaryStage, EditorToolBar toolbar) {
        super(primaryStage, toolbar);
    }

    public boolean saveDocx(String text, File contentFile, MarkdownParser markdownParser) throws IOException, Docx4JException, JAXBException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(contentFile.getParentFile());
        fileChooser.setInitialFileName(contentFile.getName().split("\\.")[0] + ".docx");
        fileChooser.getExtensionFilters().add(FileExtensionFilters.DOCX);
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file != null){
            timer.start();
            markdownParser.convertToDocx(text, file);
            toolbar.setActionText("Successfully exported docx file \'" + file.getName() + "\' in " + timer.end() + "ms");
            return true;
        }
        return false;
    }

    @Override
    protected boolean saveFile(File contentFile, FileChooser.ExtensionFilter ext, String content, String actionText) throws IOException {
        return false;
    }
}
