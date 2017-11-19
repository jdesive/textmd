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

import com.desive.nodes.toolbars.EditorToolBar;
import com.desive.utilities.constants.Timer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
 Created by Jack DeSive on 11/19/2017 at 12:02 AM
*/
public abstract class SaveMachine {

    protected Timer timer = new Timer();
    protected Stage primaryStage;
    protected EditorToolBar toolbar;

    protected SaveMachine(Stage primaryStage, EditorToolBar toolbar) {
        this.primaryStage = primaryStage;
        this.toolbar = toolbar;
    }

    protected boolean saveFile(File contentFile, FileChooser.ExtensionFilter ext, String content, String actionText) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(contentFile.getParentFile());
        fileChooser.setInitialFileName(contentFile.getName().split("\\.")[0] + ext.getExtensions().stream().findFirst().get().replace("*", ""));
        fileChooser.getExtensionFilters().add(ext);
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file != null){
            timer.start();
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.print(content);
            writer.close();
            toolbar.setActionText(actionText + " in " + timer.end() + "ms");
            return true;
        }
        return false;
    }

}
