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

package com.desive.nodes.menus.items.editor.extensions;

import com.desive.markdown.MarkdownParser;
import com.desive.nodes.menus.MdExtensionMenuItem;
import com.desive.utilities.Dictionary;
import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import javafx.scene.control.Tooltip;

import java.util.List;

/*
 Created by Jack DeSive on 11/5/2017 at 9:52 PM
*/
public class EditorExtAutoLinkItem extends MdExtensionMenuItem {

    private Extension autoLinkExtension;

    public EditorExtAutoLinkItem(Dictionary dictionary, MarkdownParser markdownParser, boolean defaultValue) {
        super(dictionary.TOOLBAR_EDITOR_EXTENSIONS_AUTO_LINK_ITEM);
        autoLinkExtension = AutolinkExtension.create();
        setSelected(defaultValue);
        if(defaultValue)
            markdownParser.addExtension(autoLinkExtension);
        setToolTip(new Tooltip(dictionary.TOOLBAR_EDITOR_EXTENSIONS_AUTO_LINK_TOOLTIP));
        setOnAction(event -> getClickAction(markdownParser));
    }

    @Override
    protected void getClickAction(MarkdownParser markdownParser) {
        List<Extension> extensions = markdownParser.getExtensions();
        if(isSelected() && !extensions.contains(autoLinkExtension)) {
            extensions.add(autoLinkExtension);
        }else if(extensions.contains(autoLinkExtension)) {
            extensions.remove(autoLinkExtension);
        }
        markdownParser.setExtensions(extensions);
    }

}
