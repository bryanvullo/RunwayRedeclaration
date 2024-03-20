package uk.ac.soton.comp2211.component;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private ComboBox runwaySelection;
    private ComboBox airportSelection;
    private Button logicalLeftButton;
    private Button logicalRightButton;
    
    
    public RunwayBox() {
        logger.info("Creating Runway Box");
        
        clearway = new SimpleDoubleProperty(0.0);
        stopway = new SimpleDoubleProperty(0.0);
        displacedThreshold = new SimpleDoubleProperty(0.0);
        
        build();
    }
    
    private void build() {
        logger.info("Building Runway Box");
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(20));
        setSpacing(10);
        
        
        var title = new Text("Runway Selection");
        title.getStyleClass().add("componentTitle");
        getChildren().add(title);
        
        var airportOptions = FXCollections.observableArrayList();
        airportSelection = new ComboBox(airportOptions);
        airportSelection.setPromptText("Select Airport");
        airportSelection.getItems().addAll(
            "Heathrow",
            "Gatwick",
            "Manchester",
            "Birmingham",
            "Stansted",
            "Luton" );
        getChildren().add(airportSelection);
        
        var runwayOptions = FXCollections.observableArrayList();
        runwaySelection = new ComboBox(runwayOptions);
        runwaySelection.setPromptText("Select Runway");
        runwaySelection.setDisable(true);
        getChildren().add(runwaySelection);
        
        var clearwayBox = new HBox();
        var clearwayLabel = new Text("Clearway: ");
        clearwayLabel.getStyleClass().add("subtitle");
        var clearwayText = new Text();
        clearwayText.textProperty().bind(clearway.asString());
        clearwayBox.getChildren().addAll(clearwayLabel, clearwayText);
        
        var stopwayBox = new HBox();
        var stopwayLabel = new Text("Stopway: ");
        stopwayLabel.getStyleClass().add("subtitle");
        var stopwayText = new Text();
        stopwayText.textProperty().bind(stopway.asString());
        stopwayBox.getChildren().addAll(stopwayLabel, stopwayText);
        
        var displacedThresholdBox = new HBox();
        var displacedThresholdLabel = new Text("Displaced Threshold: ");
        displacedThresholdLabel.getStyleClass().add("subtitle");
        var displacedThresholdText = new Text();
        displacedThresholdText.textProperty().bind(displacedThreshold.asString());
        displacedThresholdBox.getChildren().addAll(displacedThresholdLabel, displacedThresholdText);
        
        getChildren().addAll(clearwayBox, stopwayBox, displacedThresholdBox);
        
        var logicalRunwayBox = new HBox();
        logicalLeftButton = new Button("Logical Left");
        logicalRightButton = new Button("Logical Right");
        logicalRunwayBox.getChildren().addAll(logicalLeftButton, logicalRightButton);
        getChildren().add(logicalRunwayBox);
        
    }
    
    public void updateRunway(Runway runway) {
        logger.info("Updating Active Runway in Runway Box");
        
        clearway.setValue(runway.getClearway());
        stopway.setValue(runway.getStopway());
        displacedThreshold.setValue(runway.getDisplacedThreshold());
    }
    
    public ComboBox getRunwaySelection() {
        return runwaySelection;
    }
    
    public ComboBox getAirportSelection() {
        return airportSelection;
    }
    
    public DoubleProperty clearwayProperty() {
        return clearway;
    }
    
    public DoubleProperty stopwayProperty() {
        return stopway;
    }
    
    public DoubleProperty displacedThresholdProperty() {
        return displacedThreshold;
    }
    
    public Button getLogicalLeftButton() {
        return logicalLeftButton;
    }
    
    public Button getLogicalRightButton() {
        return logicalRightButton;
    }
}
   