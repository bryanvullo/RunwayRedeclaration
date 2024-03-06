package uk.ac.soton.comp2211.scene;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.UI.AppWindow;
import uk.ac.soton.comp2211.UI.AppPane;

public class MainScene extends BaseScene {
    
    private static final Logger logger = LogManager.getLogger(MainScene.class);
    
    public MainScene(AppWindow appWindow) {
        super(appWindow);
    }
    
    @Override
    public void initialise() {
        logger.info("initialising the menu scene");
        
    }
    
    @Override
    public void build() {
        logger.info("Building " + this.getClass().getName());
        
        root = new AppPane(appWindow.getWidth(),appWindow.getHeight());
        
        var menuPane = new StackPane();
        menuPane.setMaxWidth(appWindow.getWidth());
        menuPane.setMaxHeight(appWindow.getHeight());
        root.getChildren().add(menuPane);
        
        var testText = new Text("This is the main scene");
        testText.setFill(javafx.scene.paint.Color.WHITE);
        menuPane.getChildren().add(testText);
    }
    
}
