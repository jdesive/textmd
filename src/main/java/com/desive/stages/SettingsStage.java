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

package com.desive.stages;

import com.desive.nodes.SettingsTabPane;
import com.desive.utilities.Dictionary;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/*
 Created by Jack DeSive on 10/20/2017 at 1:30 AM
*/
public class SettingsStage extends Stage {

    Scene scene;
    SettingsTabPane tabPane;

    public SettingsStage() {

        this.tabPane = new SettingsTabPane();
        this.scene = new Scene(this.tabPane, 600, 600);
        scene.getStylesheets().add("css/settings.css");

        this.setTitle(Dictionary.STAGE_SETTINGS_TITLE);
        this.getIcons().add(new Image("assets/favicon.png"));
        this.setScene(this.scene);
        this.setResizable(false);
    }
}
