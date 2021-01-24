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

        primaryStage.setTitle("GitLab Analyzer");
        // Primary stage is the entire window of the application.
        // The Scenes are different scenes that can be showed on this window.

        VBox empty_layout = new VBox(20);
        VBox empty_layout2 = new VBox(20);
        VBox empty_layout3 = new VBox(20);

        Scene homeScene = new Scene(empty_layout,500, 500);

        Scene databaseScene = new Scene(empty_layout2,500, 500);

        Scene gitlabScene = new Scene(empty_layout3,500, 500);

        homeScene = createHomeScene(primaryStage, databaseScene, gitlabScene);

        primaryStage.setScene(homeScene); // The screen initally shown when starting the app.
        primaryStage.show();
    }

    private Scene createHomeScene(Stage primaryStage, Scene databaseScene,
                                  Scene gitlabScene) {
        // Add button functionality will be added for the home scene here.
        // Using lambdas, the action when clicking one of the buttons is to
        // switch to another screen (for now, just the DB and Gitlab screens).

        Button databaseButton = new Button("Access/Modify the Database");
        Button gitlabButton = new Button("Retrieve info from gitlab");

        databaseButton.setOnAction(e -> primaryStage.setScene(databaseScene));
        gitlabButton.setOnAction(e -> primaryStage.setScene(gitlabScene));

        VBox layout = new VBox(20);
        layout.getChildren().addAll(databaseButton, gitlabButton);
        // Can add more stuff (buttons, labels, etc) here.

        return (new Scene(layout, 500, 500));
    }

    private void createDatabaseScene(Stage primaryStage, Scene databaseScene,
                                     VBox databaseLayout) {

    }

    private void createGitLabScene(Stage primaryStage, Scene GitLabScene,
                                   VBox gitlabLayout) {

    }
}
