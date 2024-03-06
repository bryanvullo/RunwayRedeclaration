package uk.ac.soton.comp2211.scene;

import javafx.scene.Scene;
import javafx.scene.paint.Color;

import uk.ac.soton.comp2211.UI.AppPane;
import uk.ac.soton.comp2211.UI.AppWindow;

/**
 * A Base Scene used in the App. Handles common functionality between all scenes.
 */
public abstract class BaseScene {
    
    /**
     * The AppWindow of the Scene
     */
    protected final AppWindow appWindow;
    
    /**
     * The root AppPane of the Scene
     */
    protected AppPane root;
    /**
     * The current Scene
     */
    protected Scene scene;
    
    /**
     * Create a new scene, passing in the AppWindow the scene will be displayed in
     * @param appWindow the app window
     */
    public BaseScene(AppWindow appWindow) {
        this.appWindow = appWindow;
    }
    
    /**
     * Initialise this scene. Called after creation
     */
    public abstract void initialise();
    
    /**
     * Build the layout of the scene
     */
    public abstract void build();
    
    /**
     * Create a new JavaFX scene using the root contained within this scene
     * @return JavaFX scene
     */
    public Scene setScene() {
        var previous = appWindow.getScene();
        Scene scene = new Scene(root, previous.getWidth(), previous.getHeight(), Color.BLACK);
        this.scene = scene;
        return scene;
    }
    
    /**
     * Get the JavaFX scene contained inside
     * @return JavaFX scene
     */
    public Scene getScene() {
        return this.scene;
    }
    
}