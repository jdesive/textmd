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

package com.desive.nodes.toolbars.menus.items.editor.file.export;

import com.desive.nodes.TabFactory;
import com.desive.nodes.tabs.EditorTab;
import com.desive.nodes.toolbars.menus.MdStyledPageMenuItem;
import com.desive.stages.dialogs.DialogFactory;
import com.desive.utilities.Dictionary;
import javafx.stage.Stage;

import java.io.IOException;

/*
 Created by Jack DeSive on 11/1/2017 at 9:54 PM
*/
public class EditorExportPdfItem extends MdStyledPageMenuItem {

    public EditorExportPdfItem(Dictionary dictionary, Stage stage, TabFactory tabFactory, DialogFactory dialogFactory, boolean cssStyle) {

        if(cssStyle)
            this.setText(dictionary.TOOLBAR_EDITOR_EXPORT_PDF_CSS_ITEM);
        else
            this.setText(dictionary.TOOLBAR_EDITOR_EXPORT_PDF_ITEM);

        this.setOnAction(event -> getClickAction(dictionary, stage, tabFactory, dialogFactory, cssStyle));
    }

    @Override
    public void getClickAction(final Dictionary dictionary, final Stage stage, final TabFactory tabFactory, final DialogFactory dialogFactory, final boolean cssStyle) {
        EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
        try {
            if (currTab.getEditorPane().savePdf(cssStyle)) {
                dialogFactory.buildConfirmationDialogBox(
                        dictionary.DIALOG_EXPORT_SUCCESS_TITLE,
                        cssStyle ? dictionary.DIALOG_EXPORT_SUCCESS_PDF_CSS_CONTENT : dictionary.DIALOG_EXPORT_SUCCESS_PDF_CONTENT
                ).showAndWait();
            }
        } catch (IOException e1) {
            dialogFactory.buildExceptionDialogBox(
                    dictionary.DIALOG_EXCEPTION_TITLE,
                    cssStyle ? dictionary.DIALOG_EXCEPTION_EXPORT_PDF_CSS_CONTENT : dictionary.DIALOG_EXCEPTION_EXPORT_PDF_CONTENT,
                    e1.getMessage(),
                    e1
            ).showAndWait();
        }
    }

}
