package uk.ac.soton.comp2211.component;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.model.Airport;
import uk.ac.soton.comp2211.model.Database;
import uk.ac.soton.comp2211.model.Runway;
import uk.ac.soton.comp2211.model.User;
import uk.ac.soton.comp2211.scene.MainScene;

import java.util.List;

public class RunwayBox extends VBox {

  private static final Logger logger = LogManager.getLogger(RunwayBox.class);

  private DoubleProperty clearway;
  private DoubleProperty stopway;
  private DoubleProperty displacedThreshold;
  private static ComboBox<Airport> airportSelection = new ComboBox<>();
  private static ComboBox<Runway> runwaySelection = new ComboBox<>();
  private ToggleGroup directionToggleGroup;
  private ToggleButton leftButton;
  private ToggleButton rightButton;
  private Button editAirportButton;
  private User currentUser;


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
    leftButton = new ToggleButton("TOALO");
    rightButton = new ToggleButton("TOTLT");
    leftButton.setToggleGroup(directionToggleGroup);
    rightButton.setToggleGroup(directionToggleGroup);
    leftButton.setSelected(true);
    runwaySelection.valueProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        MainScene.getRunwayViewBox().getTopDownRunway().updateRunway(newVal.getToda(), newVal.getToda(), newVal.getTora(), newVal.getLda(), newVal.getClearway(), newVal.getStopway(), newVal.getDisplacedThreshold(), newVal.getName());
      }


    });

    directionToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        String direction = ((ToggleButton) newValue).getText().equalsIgnoreCase("left") ? "TOTLT" : "TOALO";
        // TODO: Use the direction value to perform direction-specific logic
        System.out.println("Direction selected: " + direction);
      }
    });

    editAirportButton = new Button("Edit/Add Airports");
    editAirportButton.getStyleClass().add("mainSceneButton");


    editAirportButton.setOnAction(MenuBar::editAirportFXML);

    //editAirportButton.setOnMousePressed(e -> MenuBar.loadFXML(new ActionEvent(), "edit-airport.fxml"));

    getChildren().add(editAirportButton);

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
        SystemMessageBox.addMessage("Airport selected: " + selectedAirport.getAirportName());
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
    Airport selectedAirport = airportSelection.getSelectionModel().getSelectedItem();
    if (selectedAirport != null) {
      System.out.println("Airport: " + selectedAirport.getAirportName() + " with " + selectedAirport.getRunways().size() + " runways.");
      List<Runway> runways = selectedAirport.getRunways();
      runwaySelection.getItems().clear();
      runwaySelection.getItems().addAll(FXCollections.observableArrayList(runways));
      runwaySelection.setDisable(false);
      logger.info("Airport selected: " + selectedAirport.getAirportName());
      SystemMessageBox.addMessage("Airport selected: " + selectedAirport.getAirportName());
    } else {
      System.out.println("Selected airport is null");
      logger.info("No airport selected.");
      runwaySelection.setDisable(true);
    }
  }

  private void showAlertDialog(Alert.AlertType alertType, String message) {
    Alert alert = new Alert(alertType);
    alert.setContentText(message);
    alert.show();
  }

  public void updateAirports(List<Airport> newAirports) {
    Platform.runLater(() -> {
      airportSelection.getItems().addAll(newAirports);
    });
  }


  private void selectRunway(Event event) {
    Runway selectedRunway = runwaySelection.getSelectionModel().getSelectedItem();
    if (selectedRunway != null) {
      logger.info("Runway selected: " + selectedRunway.getName());
      SystemMessageBox.addMessage("Runway selected: " + selectedRunway.getName());
      MainScene.updateRunway(selectedRunway);
      // You may also update any UI elements to display runway details
    } else {
      logger.info("No runway selected or selection is null.");
    }

    if (selectedRunway == null) {
      return;
    } else {
      MainScene.getRunwayViewBox().getTopDownRunway().updateRunway(selectedRunway.getToda(), selectedRunway.getTora(), selectedRunway.getAsda(), selectedRunway.getLda(), selectedRunway.getClearway(), selectedRunway.getStopway(), selectedRunway.getDisplacedThreshold(), selectedRunway.getName());
      MainScene.getRunwayViewBox().getSideRunway().updateRunway(selectedRunway.getToda(), selectedRunway.getTora(), selectedRunway.getAsda(), selectedRunway.getLda(), selectedRunway.getClearway(), selectedRunway.getStopway(), selectedRunway.getDisplacedThreshold(), selectedRunway.getName());
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

  public static ComboBox<Runway> getRunwaySelection() {
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

  public void updateRunway(Runway selectedRunway) {
    MainScene.getRunwayViewBox().getTopDownRunway().updateRunway(selectedRunway.getToda(), selectedRunway.getTora(), selectedRunway.getAsda(), selectedRunway.getLda(), selectedRunway.getClearway(), selectedRunway.getStopway(), selectedRunway.getDisplacedThreshold(), selectedRunway.getName());
    MainScene.getRunwayViewBox().getSideRunway().updateRunway(selectedRunway.getToda(), selectedRunway.getTora(), selectedRunway.getAsda(), selectedRunway.getLda(), selectedRunway.getClearway(), selectedRunway.getStopway(), selectedRunway.getDisplacedThreshold(), selectedRunway.getName());

  }

  public ComboBox<Runway> getRunnwayBox() {
    return runwaySelection;
  }
}
