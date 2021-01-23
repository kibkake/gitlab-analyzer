package main.java.UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class UI extends Application {

    public static void start_UI(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button testButton = new Button("Please click");

        StackPane layout = new StackPane();
        layout.getChildren().add(testButton);

        Scene scene = new Scene(layout, 500, 500);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
