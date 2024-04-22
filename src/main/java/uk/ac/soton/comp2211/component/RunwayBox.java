package uk.ac.soton.comp2211.component;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.ac.soton.comp2211.model.Airport;
import uk.ac.soton.comp2211.model.Runway;
import uk.ac.soton.comp2211.scene.MainScene;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RunwayBox extends VBox {

  private static final Logger logger = LogManager.getLogger(RunwayBox.class);

  private DoubleProperty clearway;
  private DoubleProperty stopway;
  private DoubleProperty displacedThreshold;
  private static ComboBox <Airport> airportSelection = new ComboBox<>();
  private ComboBox <Runway> runwaySelection = new ComboBox<>();
  private ToggleGroup directionToggleGroup;
  private ToggleButton leftButton;
  private ToggleButton rightButton;



  public RunwayBox() {
    logger.info("Creating Runway Box");

    clearway = new SimpleDoubleProperty(0.0);
    stopway = new SimpleDoubleProperty(0.0);
    displacedThreshold = new SimpleDoubleProperty(0.0);
    applyStyles();
//    loadAirports();
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




    airportSelection.setPromptText("Select Airport");
    getChildren().add(airportSelection);


    runwaySelection.setPromptText("Select Runway");
    runwaySelection.setDisable(true);
    runwaySelection.setCellFactory(lv -> new ListCell<Runway>() {
      @Override
      protected void updateItem(Runway item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty ? null : item.getName());
      }
    });
    runwaySelection.setButtonCell(new ListCell<Runway>() {
      @Override
      protected void updateItem(Runway item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText("Select Runway");
        } else {
          setText(item.getName());
        }
      }
    });
    getChildren().add(runwaySelection);

    airportSelection.setOnAction(this::selectAirport);

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

    runwaySelection.setOnAction(this::selectRunway);


    airportSelection.setOnAction(e -> {
      Airport selectedAirport = airportSelection.getValue();
      if (selectedAirport != null) {
        System.out.println("Airport: " + selectedAirport.getAirportName() + " with " + selectedAirport.getRunways().size() + " runways.");
        runwaySelection.getItems().clear();
        runwaySelection.getItems().addAll(FXCollections.observableArrayList(selectedAirport.getRunways()));
        runwaySelection.setDisable(false);
      } else {
        System.out.println("Selected airport is null");
        runwaySelection.setDisable(true);
      }
    });

    getChildren().addAll(clearwayBox, stopwayBox, displacedThresholdBox);
  }



  private void selectAirport(Event event) {
    Airport selectedAirport = airportSelection.getSelectionModel().getSelectedItem(); // Directly obtain the selected Airport object
    if (selectedAirport != null) {
      System.out.println("Airport: " + selectedAirport.getAirportName() + " with " + selectedAirport.getRunways().size() + " runways.");
      List<Runway> runways = selectedAirport.getRunways();
      runwaySelection.getItems().clear();
      runwaySelection.getItems().addAll(FXCollections.observableArrayList(runways));
      runwaySelection.setDisable(false);
      logger.info("Airport selected: " + selectedAirport.getAirportName());
    } else {
      System.out.println("Selected airport is null");
      logger.info("No airport selected.");
      runwaySelection.setDisable(true);
    }
  }

  public void updateAirports(List<Airport> newAirports) {
    Platform.runLater(() -> {
      airportSelection.getItems().addAll(newAirports);
    });
  }


  private void selectRunway(Event event) {
    Runway selectedRunway = runwaySelection.getSelectionModel().getSelectedItem(); // Directly obtain the selected Runway object
    if (selectedRunway != null) {
      logger.info("Runway selected: " + selectedRunway.getName());
      MainScene.updateRunway(selectedRunway);
      // You may also update any UI elements to display runway details
      MainScene.getRunwayViewBox().getTopDownRunway().updateArrows(
          selectedRunway.getTora(), selectedRunway.getToda(),
          selectedRunway.getAsda(), selectedRunway.getLda()
      );
    } else {
      logger.info("No runway selected or selection is null.");
    }
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


//  public void updateRunway(Runway runway) {
//    logger.info("Updating Active Runway in Runway Box");
//
//    clearway.setValue(runway.getClearway());
//    stopway.setValue(runway.getStopway());
//    displacedThreshold.setValue(runway.getDisplacedThreshold());
//  }

  public String getSelectedDirection() {
    ToggleButton selectedToggle = (ToggleButton) directionToggleGroup.getSelectedToggle();
    return selectedToggle != null ? selectedToggle.getText() : null;
  }

  public ComboBox<Runway> getRunwaySelection() {
    return runwaySelection;
  }

  public static ComboBox<Airport> getAirportSelection() {
    return airportSelection;
  }

  public Airport getSelectedAirport() {
    return airportSelection.getValue();
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
