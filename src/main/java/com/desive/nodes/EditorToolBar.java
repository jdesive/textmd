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

import com.desive.utilities.Http;
import com.desive.utilities.Utils;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;


/*
 Created by Jack DeSive on 10/8/2017 at 1:56 PM
*/
public class EditorToolBar extends MenuBar {

    Stage primaryStage;
    TabFactory tabFactory;

    public EditorToolBar(TabFactory tabFactory, Stage primaryStage) {

        this.tabFactory = tabFactory;
        this.primaryStage = primaryStage;

        Menu file = new Menu("File"),
                edit = new Menu("Edit"),
                export = new Menu("Import/Export");

        file.getItems().addAll(
                createNewItem(),
                createOpenItem(),
                createOpenFromURLItem(),
                createSaveItem(),
                createSaveAsItem(),
                createExitItem()
        );
        edit.getItems().addAll(
                createUndoItem(),
                createRedoItem()
        );
        export.getItems().addAll(
                createImportFromFileItem(),
                createImportFromUrlItem(),
                createExportHtmlItem(),
                createExportHtmlWithStyleItem()
        );

        this.getMenus().addAll(
                file,
                edit,
                export)
        ;
    }

    private MenuItem createUndoItem() {
        MenuItem item = new MenuItem("Undo");
        item.setAccelerator(KeyCombination.valueOf("SHORTCUT+Z"));
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            if(currTab.getEditorPane().getEditor().isUndoAvailable())
                currTab.getEditorPane().getEditor().undo();
        });
        return item;
    }

    private MenuItem createRedoItem() {
        MenuItem item = new MenuItem("Redo");
        item.setAccelerator(KeyCombination.valueOf("SHORTCUT+SHIFT+Z"));
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            if(currTab.getEditorPane().getEditor().isUndoAvailable())
                currTab.getEditorPane().getEditor().redo();
        });
        return item;
    }

    private MenuItem createNewItem() {
        MenuItem item = new MenuItem("New");
        item.setAccelerator(KeyCombination.valueOf("SHORTCUT+N"));
        item.setOnAction(e -> {
            EditorPane editorPane = new EditorPane("# New page");
            EditorTab newTab = new EditorTab(editorPane);
            newTab.getEditorPane().setFile(new File(Utils.getDefaultFileName()));
            this.tabFactory.addNewEditorTab(newTab);
        });
        return item;
    }

    private MenuItem createSaveItem() {
        MenuItem item = new MenuItem("Save");
        item.setAccelerator(KeyCombination.valueOf("SHORTCUT+S"));
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            try {
                currTab.getEditorPane().save(primaryStage);
            } catch (IOException e1) {
                Utils.getExceptionDialogBox(
                        "Oops, an exception!",
                        "Error saving markdown",
                        e1.getMessage(),
                        e1,
                        primaryStage
                ).showAndWait();
            }
        });
        return item;
    }

    private MenuItem createSaveAsItem() {
        MenuItem item = new MenuItem("Save As");
        item.setAccelerator(KeyCombination.valueOf("SHORTCUT+SHIFT+S"));
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            try {
                currTab.getEditorPane().saveAs(primaryStage);
            } catch (IOException e1) {
                Utils.getExceptionDialogBox(
                        "Oops, an exception!",
                        "Error saving markdown",
                        e1.getMessage(),
                        e1,
                        primaryStage
                ).showAndWait();
            }
        });
        return item;
    }

    private MenuItem createImportFromUrlItem() {
        MenuItem item = new MenuItem("Import from URL");
        item.setOnAction(e -> {
            TextInputDialog input = Utils.getTextInputDialog(
                    "Import from URL",
                    "Enter a url to pull markdown from.",
                    primaryStage
            );
            Optional<String> result = input.showAndWait();
            result.ifPresent(url -> {
                try {
                    EditorTab tab = ((EditorTab)this.tabFactory.getSelectedTab());
                    tab.getEditorPane().setContent(tab.getEditorPane().getContent() + "\n" + Http.request(url + "", null, null, null, "GET"));
                } catch (IOException e1) {
                    Utils.getExceptionDialogBox(
                            "Oops, an exception!",
                            "Error importing markdown",
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
        MenuItem item = new MenuItem("Import from File");
        item.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Md files (*.md)", "*.md");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(primaryStage);
            if(file != null){
                try {
                    EditorTab tab = ((EditorTab)this.tabFactory.getSelectedTab());
                    tab.getEditorPane().setContent(tab.getEditorPane().getContent() + "\n" + new Scanner(file).useDelimiter("\\Z").next());
                } catch (IOException e1) {
                    Utils.getExceptionDialogBox(
                            "Oops, an exception!",
                            "Error importing markdown",
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
        MenuItem item = new MenuItem("Export HTML");
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            try {
                currTab.getEditorPane().saveHtml(primaryStage, false);
            } catch (IOException e1) {
                Utils.getExceptionDialogBox(
                        "Oops, an exception!",
                        "Error exporting html",
                        e1.getMessage(),
                        e1,
                        primaryStage
                ).showAndWait();
            }
        });
        return item;
    }

    private MenuItem createExportHtmlWithStyleItem() {
        MenuItem item = new MenuItem("Export HTML/CSS");
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            try {
                currTab.getEditorPane().saveHtml(primaryStage, true);
            } catch (IOException e1) {
                Utils.getExceptionDialogBox(
                        "Oops, an exception!",
                        "Error exporting html/css",
                        e1.getMessage(),
                        e1,
                        primaryStage
                ).showAndWait();
            }
        });
        return item;
    }

    private MenuItem createExitItem() {
        MenuItem item = new MenuItem("Exit");
        item.setOnAction(e -> {
            EditorTab currTab = ((EditorTab) tabFactory.getSelectedTab());
            if(!currTab.getEditorPane().isSaved()){
                Optional<ButtonType> save = Utils.getYesNoDialogBox(currTab.getEditorPane().getFile().getPath(),
                        "File is not saved!",
                        "Would you like to save this document before you exit?",
                        primaryStage).showAndWait();
                switch (save.get().getButtonData()){
                    case YES:
                        try {
                            currTab.getEditorPane().save(primaryStage);
                        } catch (IOException e1) {
                            Utils.getExceptionDialogBox(
                                    "Oops, an exception!",
                                    "Error saving markdown",
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
        MenuItem item = new MenuItem("Open");
        item.setAccelerator(KeyCombination.valueOf("SHORTCUT+O"));
        item.setOnAction(e ->{
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Md files (*.md)", "*.md");
            fileChooser.getExtensionFilters().add(extFilter);
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
                            "Oops, an exception!",
                            "Error opening markdown file",
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
        MenuItem item = new MenuItem("Open URL");
        item.setAccelerator(KeyCombination.valueOf("SHORTCUT+SHIFT+O"));
        item.setOnAction(e -> {
            TextInputDialog input = Utils.getTextInputDialog(
                    "Open from URL",
                    "Enter a url to pull markdown from.",
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
                            "Oops, an exception!",
                            "Error opening markdown file from " + url,
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
