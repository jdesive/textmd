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

package com.desive.nodes.editor;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
 Created by Jack DeSive on 11/12/2017 at 12:04 AM
*/
public class ImageViewPane extends Label {

    private ImageView imageView;

    public ImageViewPane() {
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    public ImageViewPane(Image image) {

        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        imageView = new ImageView(image);
        setGraphic(imageView);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImage(Image image) {
        imageView = new ImageView(image);
        setGraphic(imageView);
    }
}
