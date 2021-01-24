package main.java.UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UI extends Application {

    public static void start_UI(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene homeScene;

        Scene databaseScene;

        Scene gitlabScene;

        // Primary stage is the entire window of the application.
        // The Scenes are different scenes that can be showed on this window.

        primaryStage.setTitle("GitLab Analyzer");

        Button databaseButton = new Button("Access/Modify the Database");

        databaseButton.setOnAction(e -> System.out.println("Switch to DB scene"));

        StackPane layout = new StackPane(new VBox(databaseButton));

        homeScene = new Scene(layout, 500, 500);

        primaryStage.setScene(homeScene);

        primaryStage.show();
    }

    private void createHomeScene(Scene homeScene, VBox homeLayout) {


    }

    private void createDatabaseScene(Scene databaseScene, VBox databaseLayout) {

    }

    private void createGitLabScene(Scene GitLabScene, VBox gitlabLayout) {

    }
}
