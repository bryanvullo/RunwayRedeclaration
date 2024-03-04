package uk.ac.soton.comp2211;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        var fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TopDownView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Runway Re-declaration Tool");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}