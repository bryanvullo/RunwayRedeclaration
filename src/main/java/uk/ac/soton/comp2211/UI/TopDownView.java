package uk.ac.soton.comp2211.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TopDownView extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("UI/TopDownView.fxml"));
            primaryStage.setTitle("TopDownView");
            primaryStage.setScene(new Scene(root, 1100, 670));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}