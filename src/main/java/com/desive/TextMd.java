package com.desive;

import com.desive.nodes.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/*
 Created by Jack DeSive on 10/7/2017 at 9:28 PM
*/
public class TextMd extends Application{

    private TabFactory tabFactory;

    @Override
    public void start(Stage primaryStage) throws Exception {

        loadFonts();

        BorderPane root = new BorderPane();
        StackPane container = new StackPane();
        EditorTabPane tabPane = new EditorTabPane();
        this.tabFactory = new TabFactory(tabPane);
        EditorToolBar toolbar = new EditorToolBar(this.tabFactory, primaryStage);
        EditorPane editorPane = new EditorPane();
        EditorTab tempTab = new EditorTab(editorPane);


        this.tabFactory.addNewEditorTab(tempTab);

        root.setTop(toolbar);
        root.setCenter(tabPane);
        container.getChildren().add(root);

        Scene scene = new Scene(container, 800, 800);
        scene.getStylesheets().add("css/window.css");
        primaryStage.setScene(scene);

        primaryStage.setTitle("TextMd - Markdown editor");
        primaryStage.getIcons().add(new Image("assets/favicon.png"));
        primaryStage.show();
        primaryStage.centerOnScreen();

        primaryStage.widthProperty().addListener((event) -> {
            toolbar.setPrefSize(primaryStage.getWidth(), 25);
            editorPane.getEditor().setPrefSize(primaryStage.getWidth(), primaryStage.getHeight()-25);
            editorPane.getWebView().setPrefSize(primaryStage.getWidth(), primaryStage.getHeight()-25);
        });

    }

    private void loadFonts(){
        Font.loadFont("https://github.com/localredhead/courier-primal/blob/master/Courier%20Primal.ttf?raw=true", 14.0);
        Font.loadFont("https://github.com/localredhead/courier-primal/blob/master/Courier%20Primal%20Italic.ttf?raw=true", 14.0);
        Font.loadFont("https://github.com/localredhead/courier-primal/blob/master/Courier%20Primal%20Bold.ttf?raw=true", 14.0);
        Font.loadFont("https://github.com/localredhead/courier-primal/blob/master/Courier%20Primal%20Bold%20Italic.ttf?raw=true", 14.0);
    }


    public static void main(String[] args) {
        launch(args);
    }

}
