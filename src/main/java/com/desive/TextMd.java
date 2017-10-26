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

package com.desive;

import com.desive.stages.EditorStage;
import com.desive.stages.SettingsStage;
import com.desive.utilities.Dictionary;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/*
 Created by Jack DeSive on 10/7/2017 at 9:28 PM
*/
public class TextMd extends Application{

    EditorStage editorStage;
    SettingsStage settingsStage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        loadFonts();
        new Dictionary();
        primaryStage.close();
        this.settingsStage = new SettingsStage();
        this.editorStage = new EditorStage(this.settingsStage);
        this.settingsStage.initOwner(this.editorStage);
        this.editorStage.show();
    }

    private void loadFonts(){
        Font.loadFont("https://github.com/localredhead/courier-primal/blob/master/Courier%20Primal.ttf?raw=true", 14.0);
        Font.loadFont("https://github.com/localredhead/courier-primal/blob/master/Courier%20Primal%20Italic.ttf?raw=true", 14.0);
        Font.loadFont("https://github.com/localredhead/courier-primal/blob/master/Courier%20Primal%20Bold.ttf?raw=true", 14.0);
        Font.loadFont("https://github.com/localredhead/courier-primal/blob/master/Courier%20Primal%20Bold%20Italic.ttf?raw=true", 14.0);
    }


    public static void main(String[] args) {
        launch(args);
    }

}
