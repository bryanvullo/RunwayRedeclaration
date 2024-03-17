package uk.ac.soton.comp2211.UI;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.scene.BaseScene;
import uk.ac.soton.comp2211.App;
import uk.ac.soton.comp2211.scene.MainScene;

import java.io.IOException;

/**
 * The AppWindow is the single window for the App where everything takes place. To move between screens in the App,
 * we simply change the scene.
 *
 * The AppWindow has methods to launch each of the different parts of the App by switching scenes. You can add more
 * methods here to add more screens to the App.
 */
public class AppWindow {
    
    private static final Logger logger = LogManager.getLogger(AppWindow.class);
    
    private final int width;
    private final int height;
    
    private final Stage stage;
    
    private BaseScene currentScene;
    private Scene scene;
    
    /**
     * Create a new AppWindow attached to the given stage with the specified width and height
     * @param stage stage
     * @param width width
     * @param height height
     */
    public AppWindow(Stage stage, int width, int height) throws IOException {
        this.width = width;
        this.height = height;
        
        this.stage = stage;
        
        //Setup window
        setupStage();
        
        //Setup default scene
        setupDefaultScene();
        
        startTool();
    }
    
    public void startTool() throws IOException {
        logger.info("Starting the tool");
        loadScene(new MainScene(this));
    }
    
    /**
     * Set up the default settings for the stage itself (the window), such as the title and minimum width and height.
     */
    public void setupStage() {
        stage.setTitle("Runway Redeclaration Tool");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setOnCloseRequest(ev -> App.getInstance().shutdown());
    }
    
    /**
     * Load a given scene which extends BaseScene and switch over.
     * @param newScene new scene to load
     */
    public void loadScene(BaseScene newScene) throws IOException {
        //Cleanup remains of the previous scene
        cleanup();
        
        //Create the new scene and set it up
        newScene.build();
        currentScene = newScene;
        scene = newScene.setScene();
        stage.setScene(scene);
        
        //Initialise the scene when ready
        Platform.runLater(() -> currentScene.initialise());
    }
    
    /**
     * Set up the default scene (an empty black scene) when no scene is loaded
     */
    public void setupDefaultScene() {
        this.scene = new Scene(new Pane(),1000,800, Color.BLACK);
        stage.setScene(this.scene);
    }
    
    /**
     * When switching scenes, perform any cleanup needed, such as removing previous listeners
     */
    public void cleanup() {
        logger.info("Clearing up previous scene");
    }
    
    /**
     * Get the current scene being displayed
     * @return scene
     */
    public Scene getScene() {
        return scene;
    }
    
    /**
     * Get the width of the App Window
     * @return width
     */
    public int getWidth() {
        return this.width;
    }
    
    /**
     * Get the height of the App Window
     * @return height
     */
    public int getHeight() {
        return this.height;
    }
    
}
