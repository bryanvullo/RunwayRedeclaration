package uk.ac.soton.comp2211.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ObstaclesBox extends VBox {
    
    private static final Logger logger = LogManager.getLogger(ObstaclesBox.class);
    private Button boeingButton;
    private Button airbusButton;
    private Button containerButton;
    private Button shuttleBusButton;
    private Button customButton;
    
    public ObstaclesBox() {
        logger.info("Creating Obstacles Box");
        
        build();
    }
    
    private void build() {
        logger.info("Building Obstacles Box");
        setAlignment(Pos.TOP_CENTER);
        setSpacing(10);
        setPadding(new Insets(20));
        
        var title = new Text("Obstacles");
        title.getStyleClass().add("componentTitle");
        getChildren().add(title);
        
        var obstacleBox = new ScrollPane();
        obstacleBox.setFitToWidth(true);
        getChildren().add(obstacleBox);
        
        var buttonBox = new VBox();
        buttonBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
        buttonBox.setPadding(new Insets(3));
        buttonBox.setSpacing(3);
        obstacleBox.setContent(buttonBox);
        
        boeingButton = new Button("Boeing 747");
        airbusButton = new Button("Airbus A380");
        containerButton = new Button("Container");
        shuttleBusButton = new Button("Shuttle Bus");
        customButton = new Button("Custom");
        
        buttonBox.getChildren().addAll(boeingButton, airbusButton, containerButton, shuttleBusButton, customButton);
    }
    
    public Button getBoeingButton() {
        return boeingButton;
    }
    
    public Button getAirbusButton() {
        return airbusButton;
    }
    
    public Button getContainerButton() {
        return containerButton;
    }
    
    public Button getShuttleBusButton() {
        return shuttleBusButton;
    }
    
    public Button getCustomButton() {
        return customButton;
    }
    
}
