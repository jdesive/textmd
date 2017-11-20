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

import com.desive.nodes.editor.toolbars.EditorToolBar;
import com.desive.utilities.constants.FileExtensionFilters;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;

/*
 Created by Jack DeSive on 11/18/2017 at 6:27 PM
*/
public class SaveMarkdown extends SaveMachine{

    public SaveMarkdown(Stage primaryStage, EditorToolBar toolbar) {
        super(primaryStage, toolbar);
    }

    public boolean save(File file, String content, AtomicBoolean saved) throws IOException {
        if(file.exists()){
            timer.start();
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.print(content);
            writer.close();
            toolbar.setActionText("Successfully saved file \'" + file.getName() + "\' in " + timer.end() + "ms");
            saved.set(true);
            return true;
        }else{
            return saveAs(file, content, saved);
        }
    }

    public boolean saveAs(File contentFile, String content, AtomicBoolean saved) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(contentFile.getParentFile());
        fileChooser.setInitialFileName(contentFile.getName());
        fileChooser.getExtensionFilters().add(FileExtensionFilters.MARKDOWN);
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file != null){
            contentFile = file;
            contentFile.createNewFile();
            save(contentFile, content, saved);
            return true;
        }
        return false;
    }

    @Override
    protected boolean saveFile(File contentFile, FileChooser.ExtensionFilter ext, String content, String actionText) throws IOException {
        return false;
    }

}
