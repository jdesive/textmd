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

package com.desive.nodes.menus;

import com.desive.nodes.TabFactory;
import com.desive.stages.dialogs.DialogFactory;
import com.desive.utilities.Dictionary;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/*
 Created by Jack DeSive on 11/2/2017 at 8:59 PM
*/
public abstract class MdPageMenuItem extends MenuItem {

    protected MdPageMenuItem(String text) {
        super(text);
    }

    public abstract void getClickAction(final Dictionary dictionary, final Stage stage, final TabFactory tabFactory, final DialogFactory dialogFactory);
}
