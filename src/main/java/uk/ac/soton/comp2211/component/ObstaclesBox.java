package uk.ac.soton.comp2211.component;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ObstaclesBox extends VBox {
    
    private static final Logger logger = LogManager.getLogger(ObstaclesBox.class);
    
    public ObstaclesBox() {
        logger.info("Creating Obstacles Box");
        
        build();
    }
    
    private void build() {
        logger.info("Building Obstacles Box");
        
        var title = new Text("Obstacles");
        getChildren().add(title);
        
        var obstacleBox = new ScrollPane();
        getChildren().add(obstacleBox);
        
        var buttonBox = new VBox();
        obstacleBox.setContent(buttonBox);
        
        var planeButton = new Button("Plane");
        var containerButton = new Button("Container");
        var shuttleBusButton = new Button("Shuttle Bus");
        var customButton = new Button("Custom");
        
        buttonBox.getChildren().addAll(planeButton, containerButton, shuttleBusButton);
    }
    
}
