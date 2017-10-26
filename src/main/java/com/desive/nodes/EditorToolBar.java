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

package com.desive.nodes;

import com.desive.nodes.tabs.EditorTab;
import com.desive.utilities.*;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.docx4j.openpackaging.exceptions.Docx4JException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;


/*
 Created by Jack DeSive on 10/8/2017 at 1:56 PM
*/
public class EditorToolBar extends MenuBar {

    Dictionary dict = Dictionary.getInstance();

    Stage primaryStage, settingsStage;
    TabFactory tabFactory;

    public EditorToolBar(TabFactory tabFactory, Stage primaryStage, Stage settingsStage) {

        this.tabFactory = tabFactory;
        this.primaryStage = primaryStage;
        this.settingsStage = settingsStage;

        Menu file = new Menu(dict.TOOLBAR_EDITOR_FILE_MENU),
                edit = new Menu(dict.TOOLBAR_EDITOR_EDIT_MENU),
                view = new Menu(dict.TOOLBAR_EDITOR_VIEW_MENU),
                help = new Menu(dict.TOOLBAR_EDITOR_HELP_MENU),
                imports = new Menu(dict.TOOLBAR_EDITOR_IMPORT_MENU),
                export = new Menu(dict.TOOLBAR_EDITOR_EXPORT_MENU),
                open = new Menu(dict.TOOLBAR_EDITOR_OPEN_MENU);

        file.getItems().addAll(
                createNewItem(),
                open,
                imports,
                export,
                createSaveItem(),
                createSaveAsItem(),
                createExitItem()
        );
        edit.getItems().addAll(
                createUndoItem(),
                createRedoItem()
        );
        view.getItems().addAll(
                createRefreshViewItem(),
                createPrettifyItem()
        );

        imports.getItems().addAll(
                createImportFromFileItem(),
                createImportFromUrlItem()
        );
        export.getItems().addAll(
                createExportDocxItem(),
                createExportPdfItem(),
                createExportPdfWithCssItem(),
                createExportJiraItem(),
                createExportConfluenceItem(),
                createExportYoutrackItem(),
                createExportTextItem(),
                createExportHtmlItem(),
                createExportHtmlWithStyleItem()
        );
        open.getItems().addAll(
                createOpenItem(),
                createOpenFromURLItem()
        );
        //help.getItems().add(createSettingsItem());

        this.getMenus().addAll(
                file,
                edit,
                view,
                help
        );
    }

    private MenuItem createSettingsItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_SETTINGS_ITEM);
        item.setAccelerator(Shortcut.OPEN_SETTINGS_STAGE);
        item.setOnAction(e -> {
            this.settingsStage.centerOnScreen();
            this.settingsStage.show();
        });

        return item;
    }

    private MenuItem createPrettifyItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_PRETTIFY_ITEM);
        item.setOnAction(e -> {
            ((EditorTab) this.tabFactory.getSelectedTab()).getEditorPane().prettifyWebViewCode();
        });
        return item;
    }

    private MenuItem createRefreshViewItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_REFRESH_VIEW_ITEM);
        item.setAccelerator(Shortcut.REFRESH_EDITOR_VIEW);
        item.setOnAction(e -> {
            ((EditorTab) this.tabFactory.getSelectedTab()).getEditorPane().refreshWebView();
        });
        return item;
    }

    private MenuItem createUndoItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_UNDO_ITEM);
        item.setAccelerator(Shortcut.UNDO_EDITOR);
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            if(currTab.getEditorPane().getEditor().isUndoAvailable())
                currTab.getEditorPane().getEditor().undo();
        });
        return item;
    }

    private MenuItem createRedoItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_REDO_ITEM);
        item.setAccelerator(Shortcut.REDO_EDITOR);
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            if(currTab.getEditorPane().getEditor().isUndoAvailable())
                currTab.getEditorPane().getEditor().redo();
        });
        return item;
    }

    private MenuItem createNewItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_NEW_ITEM);
        item.setAccelerator(Shortcut.NEW_FILE_EDITOR);
        item.setOnAction(e -> {
            EditorPane editorPane = new EditorPane("# New page");
            EditorTab newTab = new EditorTab(editorPane);
            newTab.getEditorPane().setFile(new File(Utils.getDefaultFileName()));
            this.tabFactory.addNewEditorTab(newTab);
        });
        return item;
    }

    private MenuItem createSaveItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_SAVE_ITEM);
        item.setAccelerator(Shortcut.SAVE_EDITOR);
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            try {
                currTab.getEditorPane().save(primaryStage);
            } catch (IOException e1) {
                Utils.getExceptionDialogBox(
                        dict.DIALOG_EXCEPTION_TITLE,
                        dict.DIALOG_EXCEPTION_SAVING_MARKDOWN_CONTENT,
                        e1.getMessage(),
                        e1,
                        primaryStage
                ).showAndWait();
            }
        });
        return item;
    }

    private MenuItem createSaveAsItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_SAVE_AS_ITEM);
        item.setAccelerator(Shortcut.SAVE_AS_EDITOR);
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            try {
                currTab.getEditorPane().saveAs(primaryStage);
                currTab.computeTabName();
            } catch (IOException e1) {
                Utils.getExceptionDialogBox(
                        dict.DIALOG_EXCEPTION_TITLE,
                        dict.DIALOG_EXCEPTION_SAVING_MARKDOWN_CONTENT,
                        e1.getMessage(),
                        e1,
                        primaryStage
                ).showAndWait();
            }
        });
        return item;
    }

    private MenuItem createImportFromUrlItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_IMPORT_URL_ITEM);
        item.setAccelerator(Shortcut.IMPORT_URL_EDITOR);
        item.setOnAction(e -> {
            TextInputDialog input = Utils.getTextInputDialog(
                    dict.DIALOG_IMPORT_URL_TITLE,
                    dict.DIALOG_IMPORT_URL_CONTENT,
                    primaryStage
            );
            Optional<String> result = input.showAndWait();
            result.ifPresent(url -> {
                try {
                    EditorTab tab = ((EditorTab)this.tabFactory.getSelectedTab());
                    tab.getEditorPane().setContent(tab.getEditorPane().getContent() + "\n" + Http.request(url + "", null, null, null, "GET"));
                } catch (IOException e1) {
                    Utils.getExceptionDialogBox(
                            dict.DIALOG_EXCEPTION_TITLE,
                            dict.DIALOG_EXCEPTION_IMPORT_CONTENT,
                            e1.getMessage(),
                            e1,
                            primaryStage
                    ).showAndWait();
                }
            });
        });
        return item;
    }

    private MenuItem createImportFromFileItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_IMPORT_FILE_ITEM);
        item.setAccelerator(Shortcut.IMPORT_FILE_EDITOR);
        item.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(FileExtensionFilters.MARKDOWN);
            File file = fileChooser.showOpenDialog(primaryStage);
            if(file != null){
                try {
                    EditorTab tab = ((EditorTab)this.tabFactory.getSelectedTab());
                    tab.getEditorPane().setContent(tab.getEditorPane().getContent() + "\n" + new Scanner(file).useDelimiter("\\Z").next());
                } catch (IOException e1) {
                    Utils.getExceptionDialogBox(
                            dict.DIALOG_EXCEPTION_TITLE,
                            dict.DIALOG_EXCEPTION_IMPORT_CONTENT,
                            e1.getMessage(),
                            e1,
                            primaryStage
                    ).showAndWait();
                }
            }
        });
        return item;
    }

    private MenuItem createExportHtmlItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_EXPORT_HTML_ITEM);
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            try {
                if(currTab.getEditorPane().saveHtml(primaryStage, false)) {
                    Utils.getConfirmationDialog(
                            dict.DIALOG_EXPORT_SUCCESS_TITLE,
                            dict.DIALOG_EXPORT_SUCCESS_HTML_CONTENT,
                            primaryStage
                    ).showAndWait();
                }
            } catch (IOException e1) {
                Utils.getExceptionDialogBox(
                        dict.DIALOG_EXCEPTION_TITLE,
                        dict.DIALOG_EXCEPTION_EXPORT_HTML_CONTENT,
                        e1.getMessage(),
                        e1,
                        primaryStage
                ).showAndWait();
            }
        });
        return item;
    }

    private MenuItem createExportHtmlWithStyleItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_EXPORT_HTML_CSS_ITEM);
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            try {
                if(currTab.getEditorPane().saveHtml(primaryStage, true)) {
                    Utils.getConfirmationDialog(
                            dict.DIALOG_EXPORT_SUCCESS_TITLE,
                            dict.DIALOG_EXPORT_SUCCESS_HTML_CSS_CONTENT,
                            primaryStage
                    ).showAndWait();
                }
            } catch (IOException e1) {
                Utils.getExceptionDialogBox(
                        dict.DIALOG_EXCEPTION_TITLE,
                        dict.DIALOG_EXCEPTION_EXPORT_HTML_CSS_CONTENT,
                        e1.getMessage(),
                        e1,
                        primaryStage
                ).showAndWait();
            }
        });
        return item;
    }

    private MenuItem createExportDocxItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_EXPORT_DOCX_ITEM);
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            try {
                if(currTab.getEditorPane().saveDocx(primaryStage)) {
                    Utils.getConfirmationDialog(
                            dict.DIALOG_EXPORT_SUCCESS_TITLE,
                            dict.DIALOG_EXPORT_SUCCESS_DOCX_CONTENT,
                            primaryStage
                    ).showAndWait();
                }
            } catch (IOException | Docx4JException e1) {
                Utils.getExceptionDialogBox(
                        dict.DIALOG_EXCEPTION_TITLE,
                        dict.DIALOG_EXCEPTION_EXPORT_DOCX_CONTENT,
                        e1.getMessage(),
                        e1,
                        primaryStage
                ).showAndWait();
            }
        });
        return item;
    }

    private MenuItem createExportPdfItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_EXPORT_PDF_ITEM);
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            try {
                if(currTab.getEditorPane().savePdf(primaryStage, false)) {
                    Utils.getConfirmationDialog(
                            dict.DIALOG_EXPORT_SUCCESS_TITLE,
                            dict.DIALOG_EXPORT_SUCCESS_PDF_CONTENT,
                            primaryStage
                    ).showAndWait();
                }
            } catch (IOException e1) {
                Utils.getExceptionDialogBox(
                        dict.DIALOG_EXCEPTION_TITLE,
                        dict.DIALOG_EXCEPTION_EXPORT_PDF_CONTENT,
                        e1.getMessage(),
                        e1,
                        primaryStage
                ).showAndWait();
            }
        });
        return item;
    }

    private MenuItem createExportPdfWithCssItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_EXPORT_PDF_CSS_ITEM);
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            try {
                if(currTab.getEditorPane().savePdf(primaryStage, true)) {
                    Utils.getConfirmationDialog(
                            dict.DIALOG_EXPORT_SUCCESS_TITLE,
                            dict.DIALOG_EXPORT_SUCCESS_PDF_CSS_CONTENT,
                            primaryStage
                    ).showAndWait();
                }
            } catch (IOException e1) {
                Utils.getExceptionDialogBox(
                        dict.DIALOG_EXCEPTION_TITLE,
                        dict.DIALOG_EXCEPTION_EXPORT_PDF_CSS_CONTENT,
                        e1.getMessage(),
                        e1,
                        primaryStage
                ).showAndWait();
            }
        });
        return item;
    }

    private MenuItem createExportJiraItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_EXPORT_JIRA_ITEM);
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            try {
                if(currTab.getEditorPane().saveJira(primaryStage)) {
                    Utils.getConfirmationDialog(
                            dict.DIALOG_EXPORT_SUCCESS_TITLE,
                            dict.DIALOG_EXPORT_SUCCESS_JIRA_CONTENT,
                            primaryStage
                    ).showAndWait();
                }
            } catch (IOException e1) {
                Utils.getExceptionDialogBox(
                        dict.DIALOG_EXCEPTION_TITLE,
                        dict.DIALOG_EXCEPTION_EXPORT_JIRA_CONTENT,
                        e1.getMessage(),
                        e1,
                        primaryStage
                ).showAndWait();
            }
        });
        return item;
    }

    private MenuItem createExportYoutrackItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_EXPORT_YOUTRACK_ITEM);
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            try {
                if(currTab.getEditorPane().saveYoutrack(primaryStage)) {
                    Utils.getConfirmationDialog(
                            dict.DIALOG_EXPORT_SUCCESS_TITLE,
                            dict.DIALOG_EXPORT_SUCCESS_YOUTRACK_CONTENT,
                            primaryStage
                    ).showAndWait();
                }
            } catch (IOException e1) {
                Utils.getExceptionDialogBox(
                        dict.DIALOG_EXCEPTION_TITLE,
                        dict.DIALOG_EXCEPTION_EXPORT_YOUTRACK_CONTENT,
                        e1.getMessage(),
                        e1,
                        primaryStage
                ).showAndWait();
            }
        });
        return item;
    }

    private MenuItem createExportTextItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_EXPORT_TEXT_ITEM);
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            try {
                if(currTab.getEditorPane().saveText(primaryStage)) {
                    Utils.getConfirmationDialog(
                            dict.DIALOG_EXPORT_SUCCESS_TITLE,
                            dict.DIALOG_EXPORT_SUCCESS_PLAIN_TEXT_CONTENT,
                            primaryStage
                    ).showAndWait();
                }
            } catch (IOException e1) {
                Utils.getExceptionDialogBox(
                        dict.DIALOG_EXCEPTION_TITLE,
                        dict.DIALOG_EXCEPTION_EXPORT_PLAIN_TEXT_CONTENT,
                        e1.getMessage(),
                        e1,
                        primaryStage
                ).showAndWait();
            }
        });
        return item;
    }

    private MenuItem createExportConfluenceItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_EXPORT_CONFLUENCE_ITEM);
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            try {
                if(currTab.getEditorPane().saveConfluenceMarkup(primaryStage)) {
                    Utils.getConfirmationDialog(
                            dict.DIALOG_EXPORT_SUCCESS_TITLE,
                            dict.DIALOG_EXPORT_SUCCESS_CONFLUENCE_CONTENT,
                            primaryStage
                    ).showAndWait();
                }
            } catch (IOException e1) {
                Utils.getExceptionDialogBox(
                        dict.DIALOG_EXCEPTION_TITLE,
                        dict.DIALOG_EXCEPTION_EXPORT_CONFLUENCE_CONTENT,
                        e1.getMessage(),
                        e1,
                        primaryStage
                ).showAndWait();
            }
        });
        return item;
    }

    private MenuItem createExitItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_EXIT_ITEM);
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            if(!currTab.getEditorPane().isSaved()){
                Optional<ButtonType> save = Utils.getYesNoDialogBox(currTab.getEditorPane().getFile().getPath(),
                        dict.DIALOG_FILE_NOT_SAVED_TITLE,
                        dict.DIALOG_FILE_NOT_SAVED_CONTENT,
                        primaryStage).showAndWait();
                switch (save.get().getButtonData()){
                    case YES:
                        try {
                            currTab.getEditorPane().save(primaryStage);
                        } catch (IOException e1) {
                            Utils.getExceptionDialogBox(
                                    dict.DIALOG_EXCEPTION_TITLE,
                                    dict.DIALOG_EXCEPTION_SAVING_MARKDOWN_CONTENT,
                                    e1.getMessage(),
                                    e1,
                                    primaryStage
                            ).showAndWait();
                        }
                        break;
                    case NO:
                        break;
                    case CANCEL_CLOSE:
                    default:
                        return;
                }
            }
            primaryStage.close();
        });
        return item;
    }

    private MenuItem createOpenItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_OPEN_FILE_ITEM);
        item.setAccelerator(Shortcut.OPEN_FILE_EDITOR);
        item.setOnAction(e ->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(FileExtensionFilters.MARKDOWN);
            File file = fileChooser.showOpenDialog(primaryStage);
            if(file != null){
                try {
                    String content =  new Scanner(file).useDelimiter("\\Z").next();
                    EditorPane editorPane = new EditorPane(content);
                    EditorTab newTab = new EditorTab(editorPane);
                    newTab.getEditorPane().setFile(file);
                    this.tabFactory.addNewEditorTab(newTab);
                } catch (FileNotFoundException e1) {
                    Utils.getExceptionDialogBox(
                            dict.DIALOG_EXCEPTION_TITLE,
                            dict.DIALOG_EXCEPTION_OPENING_MARKDOWN_CONTENT,
                            e1.getMessage(),
                            e1,
                            primaryStage
                    ).showAndWait();
                }
            }
        });
        return item;
    }

    private MenuItem createOpenFromURLItem() {
        MenuItem item = new MenuItem(dict.TOOLBAR_EDITOR_OPEN_URL_ITEM);
        item.setAccelerator(Shortcut.OPEN_URL_EDITOR);
        item.setOnAction(e -> {
            TextInputDialog input = Utils.getTextInputDialog(
                    dict.DIALOG_OPEN_URL_TITLE,
                    dict.DIALOG_OPEN_URL_CONTENT,
                    primaryStage
            );
            Optional<String> result = input.showAndWait();
            result.ifPresent(url -> {
                try {
                    EditorPane editorPane = new EditorPane(Http.request(url + "", null, null, null, "GET"));
                    EditorTab newTab = new EditorTab(editorPane);
                    newTab.getEditorPane().setFile(new File(Utils.getDefaultFileName()));
                    this.tabFactory.addNewEditorTab(newTab);
                } catch (IOException e1) {
                    Utils.getExceptionDialogBox(
                            dict.DIALOG_EXCEPTION_TITLE,
                            dict.DIALOG_EXCEPTION_OPENING_MARKDOWN_URL_CONTENT,
                            e1.getMessage(),
                            e1,
                            primaryStage
                    ).showAndWait();
                }
            });
        });
        return item;
    }

}
