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

package com.desive.nodes.editor.toolbar;

import com.desive.nodes.editor.EditorPane;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.fxmisc.richtext.model.TwoDimensional;

import static javafx.geometry.Pos.CENTER;

/*
 Created by Jack DeSive on 11/16/2017 at 9:21 PM
*/
public class LineNumberPane extends HBox {

    private static Label paragraghNumber = new Label("0"),
    seperator = new Label(":"),
    columnNumber = new Label("0");

    public LineNumberPane() {

        getChildren().addAll(
                paragraghNumber,
                seperator,
                columnNumber
        );

        setAlignment(CENTER);
        paragraghNumber.getStyleClass().add("caret-pos-label");
        seperator.getStyleClass().add("caret-pos-label");
        columnNumber.getStyleClass().add("caret-pos-label");
    }


    public static void resetPosition(EditorPane editor){

        if(editor == null)
            return;

        TwoDimensional.Position pos = editor.getEditor().offsetToPosition(editor.getEditor().getCaretPosition(), TwoDimensional.Bias.Backward);

        paragraghNumber.setText(String.valueOf(pos.getMajor()));
        columnNumber.setText(String.valueOf(pos.getMinor()));
    }

}
