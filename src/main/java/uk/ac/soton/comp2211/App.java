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
    
    /**
     * Base resolution width
     */
    private final int width = 800;
    
    /**
     * Base resolution height
     */
    private final int height = 600;
    
    private static final Logger logger = LogManager.getLogger(App.class);
    private Stage stage;

//    @Override
//    public void start(Stage stage) throws IOException {
//        var fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TopDownView.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1110, 720);
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
    public void start(Stage stage) {
        instance = this;
        this.stage = stage;

        //Open app window
        openApp();
    }
    
    /**
     * Create the AppWindow with the specified width and height
     */
    public void openApp() {
        logger.info("Opening app window");
        
        //Change the width and height in this class to change the base rendering resolution
        var appWindow = new AppWindow(stage,width,height);
        
        //Display the AppWindow
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}