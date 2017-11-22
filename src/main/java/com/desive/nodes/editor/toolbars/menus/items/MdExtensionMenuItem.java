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

package com.desive.nodes.editor.toolbars.menus.items;

import com.desive.markdown.MarkdownParser;
import com.desive.nodes.TabFactory;
import com.vladsch.flexmark.Extension;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

import java.util.List;

/*
 Created by Jack DeSive on 11/5/2017 at 10:54 PM
*/
public abstract class MdExtensionMenuItem extends CheckMenuItem {

    private Label label;

    public MdExtensionMenuItem(String text) {
        super(null);

        label = new Label(text);
        setGraphic(label);

    }

    protected abstract void getClickAction(final MarkdownParser markdownParser, final TabFactory tabFactory);

    protected void setToolTip(Tooltip toolTip) {
        label.setTooltip(toolTip);
    }

    protected  void addExtension(final MarkdownParser markdownParser, Extension extension) {
        List<Extension> extensions = markdownParser.getExtensions();
        if(isSelected() && !extensions.contains(extension)) {
            extensions.add(extension);
        }else if(extensions.contains(extension)) {
            extensions.remove(extension);
        }
        markdownParser.setExtensions(extensions);
    }

}
