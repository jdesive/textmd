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

package com.desive.nodes.editor.toolbars.nodes;

import com.desive.utilities.Utils;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/*
 Created by Jack DeSive on 11/11/2017 at 2:40 AM
*/
public class ActionTextPane extends StackPane {

    private Label actionText;
    private ImageView graphic;

    private FadeTransition ft;

    public ActionTextPane() {

        actionText = new Label("Test");
        graphic = new ImageView(new Image("assets/icons/editor_action_info_icon.png"));
        actionText.setGraphic(graphic);
        actionText.setTextFill(Utils.getDefaultTextColor());
        actionText.setAlignment(Pos.CENTER);
        actionText.setPadding(new Insets(0, 0, 0, 5));
        getChildren().add(actionText);
        setAlignment(Pos.CENTER);

        ft = new FadeTransition(Duration.millis(500), graphic);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setAutoReverse(true);
        ft.setCycleCount(4);

    }

    public void setActionText(String text) {
        actionText.setText(text);
        startTransition();
    }

    private void startTransition(){
        ft.playFromStart();
    }

}
