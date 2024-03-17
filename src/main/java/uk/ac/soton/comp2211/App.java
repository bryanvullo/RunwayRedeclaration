package uk.ac.soton.comp2211;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.UI.AppWindow;

/**
 * JavaFX App
 */
public class App extends Application {
    
    private static App instance;
    private static final Logger logger = LogManager.getLogger(App.class);
    private Stage stage;
    
    /**
     * Base resolution width
     */
    private final int width = 1000;
    
    /**
     * Base resolution height
     */
    private final int height = 800;
    
//    /**
//     * Start the application
//     * @param stage the stage
//     * @throws IOException if the fxml file is not found
//     */
//    @Override
//    public void start(Stage stage) throws IOException {
//        var fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login-page.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setTitle("Runway Re-declaration Tool");
//        stage.setScene(scene);
//        stage.show();
//    }
    
    /**
     * Shutdown the tool
     */
    public void shutdown() {
        logger.info("Shutting down");
        System.exit(0);
    }
    
    /**
     * Get the singleton App instance
     * @return the app
     */
    public static App getInstance() {
        return instance;
    }
    
    /**
     * Called by JavaFX with the primary stage as a parameter. Begins the game by opening the App Window
     * @param stage the default stage, main window
     */
    @Override
    public void start(Stage stage) throws IOException {
        instance = this;
        this.stage = stage;

        //Open app window
        openApp();
    }
    
    public void openApp() throws IOException {
        logger.info("Opening game window");
        
        //Change the width and height in this class to change the base rendering resolution for all game parts
        var appWindow = new AppWindow(stage,width,height);
        
        //Display the GameWindow
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}