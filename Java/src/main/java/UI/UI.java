package main.java.UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UI extends Application {

    public static final int WIDTH_SIZE = 500;
    public static final int HEIGHT_SIZE = 500;
    public static final String TEXT_FOR_HOME_BUTTON = "Return to home screen";
    public static final String TEXT_FOR_DATABASE_BUTTON = "Access/Modify the Database";
    public static final String TEXT_FOR_GITLAB_BUTTON = "Retrieve info from GitLab";

    Scene homeScene = new Scene(new VBox(20),WIDTH_SIZE, HEIGHT_SIZE);

    Scene databaseScene = new Scene(new VBox(20), WIDTH_SIZE, HEIGHT_SIZE);

    Scene gitlabScene = new Scene(new VBox(20),WIDTH_SIZE, HEIGHT_SIZE);

    public static void start_UI(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("GitLab Analyzer");
        // Primary stage is the entire window of the application.
        // The Scenes are different scenes that can be shown on this window.

        createHomeScene(primaryStage);

        createDatabaseScene(primaryStage);

        createGitLabScene(primaryStage);

        primaryStage.setScene(homeScene); // The screen initially shown when starting the app.

        primaryStage.show();
    }

    private void createHomeScene(Stage primaryStage) {
        // Button functionality will be added for the home scene here.
        // Using lambdas, the action when clicking one of the buttons is to
        // switch to another screen (for now, just the DB and Gitlab screens).

        Label homeSceneTitle = new Label("This is the Home Scene");

        Button databaseButton = new Button(TEXT_FOR_DATABASE_BUTTON);
        Button gitlabButton = new Button(TEXT_FOR_GITLAB_BUTTON);

        databaseButton.setOnAction(e -> primaryStage.setScene(databaseScene));
        gitlabButton.setOnAction(e -> primaryStage.setScene(gitlabScene));

        VBox layout = new VBox(20);
        layout.getChildren().addAll(homeSceneTitle, databaseButton, gitlabButton);
        // Can add more stuff (buttons, labels, etc) here.

        homeScene = new Scene(layout, WIDTH_SIZE, HEIGHT_SIZE);
    }

    private void createDatabaseScene(Stage primaryStage) {
        Label databaseSceneTitle = new Label("This is the Database Scene");

        // Buttons for this database scene include buttons to go to the
        // home or gitlab scenes, as well as buttons to interact with the DB.

        Button homeButton = new Button(TEXT_FOR_HOME_BUTTON);
        Button gitlabButton = new Button(TEXT_FOR_GITLAB_BUTTON);

        homeButton.setOnAction(e -> primaryStage.setScene(homeScene));
        gitlabButton.setOnAction(e -> primaryStage.setScene(gitlabScene));

        // Continue here - add buttons to interact w/ DB.

        VBox layout = new VBox(20);
        layout.getChildren().addAll(databaseSceneTitle, homeButton,
                                    gitlabButton); // Add more buttons here.

        databaseScene = new Scene(layout, WIDTH_SIZE, HEIGHT_SIZE);
    }

    private void createGitLabScene(Stage primaryStage) {
        Label gitlabSceneTitle = new Label("This is the GitLab Scene");

        // Like in the createDatabase function, this function will have buttons
        // taking it to the other screens (home & DB), as well as additional
        // buttons for functionality.

        Button homeButton = new Button(TEXT_FOR_HOME_BUTTON);
        Button databaseButton = new Button(TEXT_FOR_DATABASE_BUTTON);

        homeButton.setOnAction(e -> primaryStage.setScene(homeScene));
        databaseButton.setOnAction(e -> primaryStage.setScene(databaseScene));

        // Continue here - add buttons for getting data from GitLab, and textfields
        // (such as for giving a token if needed).

        // From here, also maybe call some logic class, that then checks
        // if the user's token is already in the DB.

        VBox layout = new VBox(20);
        layout.getChildren().addAll(gitlabSceneTitle, homeButton, databaseButton);

        gitlabScene = new Scene(layout, WIDTH_SIZE, HEIGHT_SIZE);
    }
}
