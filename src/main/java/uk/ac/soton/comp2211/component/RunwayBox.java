package uk.ac.soton.comp2211.component;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.model.Runway;

public class RunwayBox extends VBox {
    
    private static final Logger logger = LogManager.getLogger(RunwayBox.class);
    
    private DoubleProperty clearway;
    private DoubleProperty stopway;
    private DoubleProperty displacedThreshold;
    
    
    public RunwayBox() {
        logger.info("Creating Runway Box");
        
        clearway.setValue(0.0);
        stopway.setValue(0.0);
        displacedThreshold.setValue(0.0);
        
        build();
    }
    
    private void build() {
        logger.info("Building Runway Box");
        
        var title = new Text("Runway Selection");
        getChildren().add(title);
        
        var airports = new MenuButton("Airports");
        airports.getItems().addAll(
            new MenuItem("Heathrow"),
            new MenuItem("Gatwick"),
            new MenuItem("Manchester"),
            new MenuItem("Birmingham"),
            new MenuItem("Stansted"),
            new MenuItem("Luton") );
        getChildren().add(airports);
        
        var runways = new MenuButton("Runways");
        runways.getItems().addAll(
            new MenuItem("09L/27R"),
            new MenuItem("09R/27L") );
        getChildren().add(runways);
        
        var clearwayBox = new HBox();
        var clearwayLabel = new Text("Clearway: ");
        var clearwayText = new Text();
        clearwayText.textProperty().bind(clearway.asString());
        clearwayBox.getChildren().addAll(clearwayLabel, clearwayText);
        
        var stopwayBox = new HBox();
        var stopwayLabel = new Text("Stopway: ");
        var stopwayText = new Text();
        stopwayText.textProperty().bind(stopway.asString());
        stopwayBox.getChildren().addAll(stopwayLabel, stopwayText);
        
        var displacedThresholdBox = new HBox();
        var displacedThresholdLabel = new Text("Displaced Threshold: ");
        var displacedThresholdText = new Text();
        displacedThresholdText.textProperty().bind(displacedThreshold.asString());
        displacedThresholdBox.getChildren().addAll(displacedThresholdLabel, displacedThresholdText);
        
        getChildren().addAll(clearwayBox, stopwayBox, displacedThresholdBox);
        
        var logicalRunwayBox = new HBox();
        var logicalLeftButton = new Button("Logical Left");
        var logicalRightButton = new Button("Logical Right");
        logicalRunwayBox.getChildren().addAll(logicalLeftButton, logicalRightButton);
        getChildren().add(logicalRunwayBox);
        
    }
    
    public void updateRunway(Runway runway) {
        logger.info("Updating Active Runway in Runway Box");
        
        clearway.setValue(runway.getClearway());
        stopway.setValue(runway.getStopway());
        displacedThreshold.setValue(runway.getDisplacedThreshold());
    }
    
}
