package uk.ac.soton.comp2211.component;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

  private ToggleGroup directionToggleGroup; // Toggle group for left/right direction
  private ToggleButton leftButton;
  private ToggleButton rightButton;

  public RunwayBox() {
    logger.info("Creating Runway Box");

    clearway = new SimpleDoubleProperty(0.0);
    stopway = new SimpleDoubleProperty(0.0);
    displacedThreshold = new SimpleDoubleProperty(0.0);
    applyStyles();
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
    getChildren().add(airportSelection);

    var runwayOptions = FXCollections.observableArrayList();
    runwaySelection = new ComboBox(runwayOptions);
    runwaySelection.setPromptText("Select Runway");
    runwaySelection.setDisable(true);
    getChildren().add(runwaySelection);

    airportSelection.getStyleClass().add("custom-combo-box");
    runwaySelection.getStyleClass().add("custom-combo-box");

    directionToggleGroup = new ToggleGroup();
    leftButton = new ToggleButton("Left");
    rightButton = new ToggleButton("Right");
    leftButton.setToggleGroup(directionToggleGroup);
    rightButton.setToggleGroup(directionToggleGroup);
    leftButton.setSelected(true);

    directionToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        String direction = ((ToggleButton) newValue).getText().equalsIgnoreCase("left") ? "TOTLT" : "TOALO";
        // TODO: Use the direction value to perform direction-specific logic
        System.out.println("Direction selected: " + direction);
      }
    });

    HBox directionBox = new HBox(10, leftButton, rightButton);
    directionBox.setAlignment(Pos.CENTER);
    getChildren().add(directionBox);
    applyStyles();
    leftButton.getStyleClass().add("direction-button");
    rightButton.getStyleClass().add("direction-button");

    var clearwayBox = new HBox();
    var clearwayLabel = new Text("Clearway: ");
    clearwayLabel.getStyleClass().add("subtitle");
    var clearwayText = new Text();
    clearwayText.textProperty().bind(clearway.asString());
    var unitText = new Text("m");
    clearwayBox.getChildren().addAll(clearwayLabel, clearwayText, unitText);

    var stopwayBox = new HBox();
    var stopwayLabel = new Text("Stopway: ");
    stopwayLabel.getStyleClass().add("subtitle");
    var stopwayText = new Text();
    stopwayText.textProperty().bind(stopway.asString());
    unitText = new Text("m");
    stopwayBox.getChildren().addAll(stopwayLabel, stopwayText, unitText);

    var displacedThresholdBox = new HBox();
    var displacedThresholdLabel = new Text("Displaced Threshold: ");
    displacedThresholdLabel.getStyleClass().add("subtitle");
    var displacedThresholdText = new Text();
    displacedThresholdText.textProperty().bind(displacedThreshold.asString());
    unitText = new Text("m");
    displacedThresholdBox.getChildren().addAll(displacedThresholdLabel, displacedThresholdText, unitText);


    getChildren().addAll(clearwayBox, stopwayBox, displacedThresholdBox);
  }

  private void applyStyles() {
    // Check if the scene is already available
    if (this.getScene() != null) {
      // If the scene is already available, directly add the stylesheet
      this.getScene().getStylesheets().add(getClass().getResource("/css/stylesheet.css").toExternalForm());
    } else {
      // If the scene is not available, add a listener to the scene property
      this.sceneProperty().addListener((observable, oldScene, newScene) -> {
        // When the scene becomes available, add the stylesheet
        if (newScene != null) {
          newScene.getStylesheets().add(getClass().getResource("/css/stylesheet.css").toExternalForm());
        }
      });
    }
  }


  public void updateRunway(Runway runway) {
    logger.info("Updating Active Runway in Runway Box");

    clearway.setValue(runway.getClearway());
    stopway.setValue(runway.getStopway());
    displacedThreshold.setValue(runway.getDisplacedThreshold());
  }

  public String getSelectedDirection() {
    ToggleButton selectedToggle = (ToggleButton) directionToggleGroup.getSelectedToggle();
    return selectedToggle != null ? selectedToggle.getText() : null;
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
}
   