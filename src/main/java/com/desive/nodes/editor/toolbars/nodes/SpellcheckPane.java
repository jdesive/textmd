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

import com.desive.nodes.TabFactory;
import com.desive.nodes.editor.ImageViewPane;
import com.desive.utilities.constants.Dictionary;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

import static javafx.geometry.Pos.CENTER;

/*
 Created by Jack DeSive on 11/12/2017 at 2:29 PM
*/
public class SpellcheckPane extends HBox {

    private ImageViewPane imageView;

    private Image okImage = new Image("assets/icons/spellcheck-ok-icon.png"),
            badImage = new Image("assets/icons/spellcheck-bad-icon.png");

    public SpellcheckPane(Dictionary dictionary, TabFactory tabFactory) {

        imageView = new ImageViewPane(okImage);

        imageView.setImage(okImage);
        imageView.setTooltip(new Tooltip("Spellcheck document"));
        imageView.setOnMouseClicked(event -> {
            if(!tabFactory.spellcheckSelectedTab()) {
                imageView.setImage(badImage);
            }else{
                imageView.setImage(okImage);
            }
        });
        setAlignment(CENTER);
        getChildren().add(imageView);
    }



}
