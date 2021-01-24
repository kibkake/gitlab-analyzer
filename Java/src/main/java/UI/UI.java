package main.java.UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UI extends Application {

    public static final int WIDTH_SIZE = 500;
    public static final int HEIGHT_SIZE = 500;

    VBox empty_layout = new VBox(20);
    VBox empty_layout2 = new VBox(20);
    VBox empty_layout3 = new VBox(20);

    Scene homeScene = new Scene(empty_layout,500, 500);

    Scene databaseScene = new Scene(empty_layout2, WIDTH_SIZE, HEIGHT_SIZE);

    Scene gitlabScene = new Scene(empty_layout3,WIDTH_SIZE, HEIGHT_SIZE);

    public static void start_UI(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("GitLab Analyzer");
        // Primary stage is the entire window of the application.
        // The Scenes are different scenes that can be showed on this window.

        createHomeScene(primaryStage);

        createDatabaseScene(primaryStage);

        primaryStage.setScene(homeScene); // The screen initially shown when starting the app.
        primaryStage.show();
    }

    private void createHomeScene(Stage primaryStage) {
        // Button functionality will be added for the home scene here.
        // Using lambdas, the action when clicking one of the buttons is to
        // switch to another screen (for now, just the DB and Gitlab screens).

        Button databaseButton = new Button("Access/Modify the Database");
        Button gitlabButton = new Button("Retrieve info from gitlab");

        databaseButton.setOnAction(e -> primaryStage.setScene(databaseScene));
        gitlabButton.setOnAction(e -> primaryStage.setScene(gitlabScene));

        VBox layout = new VBox(20);
        layout.getChildren().addAll(databaseButton, gitlabButton);
        // Can add more stuff (buttons, labels, etc) here.

        homeScene = new Scene(layout, WIDTH_SIZE, HEIGHT_SIZE);
    }

    private void createDatabaseScene(Stage primaryStage) {

        // CONTINUE HERE - This DB scene with buttons isn't showing up
        // when pressing the DB button in home.

        // Buttons for this database scene include buttons to go to the
        // home or gitlab scenes, as well as buttons to interact with the DB.

        Button homeButton = new Button("Return to home screen");
        Button gitlabButton = new Button("Retrieve info from gitlab");

        homeButton.setOnAction(e -> primaryStage.setScene(homeScene));
        gitlabButton.setOnAction(e -> primaryStage.setScene(gitlabScene));

        // Continue here - add buttons to interact w/ DB.

        VBox layout = new VBox(20);
        layout.getChildren().addAll(homeButton, gitlabButton); // Add more buttons here.

        databaseScene = new Scene(layout, WIDTH_SIZE, HEIGHT_SIZE);
    }

    private void createGitLabScene(Stage primaryStage, Scene GitLabScene,
                                   VBox gitlabLayout) {

        // Continue here - make the return value type Scene
    }
}
