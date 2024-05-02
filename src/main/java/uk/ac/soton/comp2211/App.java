package uk.ac.soton.comp2211;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.logging.Logger;
import uk.ac.soton.comp2211.UI.AppWindow;

/**
 * JavaFX App
 */
public class App extends Application {

  private static App instance;
  private static final Logger logger = Logger.getLogger(App.class.getName());
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
    logger.info("Application shutting down.");
    System.exit(0);
  }

  /**
   * Get the singleton App instance
   *
   * @return the app
   */
  public static App getInstance() {
    return instance;
  }

  /**
   * Called by JavaFX with the primary stage as a parameter. Begins the game by opening the App Window
   *
   * @param stage the default stage, main window
   */
  @Override
  public void start(Stage stage) throws IOException {
    instance = this;
    this.stage = stage;

    //Open app window
    openApp();
  }

  private static void configureLogging() {
    try {
      // Create a file handler to log messages to a file
      FileHandler fileHandler = new FileHandler("application.log");

      // Optionally, set log formatter
      fileHandler.setFormatter(new SimpleFormatter());

      // Add the file handler to the logger
      logger.addHandler(fileHandler);
    } catch (Exception e) {
      logger.severe("Error configuring logging: " + e.getMessage());
    }
  }


  public void openApp() throws IOException {
    logger.info("Opening login page");

    //Change the width and height in this class to change the base rendering resolution for all game parts
    var appWindow = new AppWindow(stage, width, height);

    //Display the GameWindow
    stage.show();
  }

  public static void main(String[] args) {
    LoggerConfig.configure();
    logger.info("Application started.");
    launch();
  }

}