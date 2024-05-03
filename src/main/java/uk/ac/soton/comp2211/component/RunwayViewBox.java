package uk.ac.soton.comp2211.component;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RunwayViewBox extends VBox {

  private static final Logger logger = LogManager.getLogger(RunwayViewBox.class);

  private JFXButton topdownButton;
  private JFXButton simultaneousButton;
  private JFXButton sideButton;
  private HBox runwayView;
  private SideRunway sideRunway;
  private TopDownRunway topDownRunway;
  private VBox runwayVBox;
  public boolean isSimultaneous;
  public Boolean panEnabled;
  private ToggleButton togglePanButton;
  
  public RunwayViewBox() {
    logger.info("Creating Runway View");
    sideRunway = new SideRunway();
    topDownRunway = new TopDownRunway();
    panEnabled = false;
    build();
    isSimultaneous = false;
  }

  private void build() {
    logger.info("Building Runway View");

    setAlignment(Pos.CENTER);
    setSpacing(10);
    setPadding(new Insets(10));

    var viewSelectionBox = new HBox();
    viewSelectionBox.setAlignment(Pos.CENTER);
    viewSelectionBox.setSpacing(10);

    topdownButton = new JFXButton("Top-Down View");
    simultaneousButton = new JFXButton("Simultaneous View");
    sideButton = new JFXButton("Side View");

    topdownButton.getStyleClass().add("runwayBoxButton");
    simultaneousButton.getStyleClass().add("runwayBoxButton");
    sideButton.getStyleClass().add("runwayBoxButton");

    viewSelectionBox.getChildren().addAll(topdownButton, sideButton,simultaneousButton);

    //TODO add Runway View here

    runwayView = new HBox();
    runwayView.setAlignment(Pos.CENTER);
    VBox.setVgrow(runwayView, Priority.ALWAYS);
    getChildren().add(runwayView);

    var topDownRunwayPane = new TopDownRunway();
    runwayView.getChildren().add(topDownRunwayPane);
    Rectangle clip = new Rectangle(900, 400);
    runwayView.setBackground(new Background(new BackgroundFill(Color.LIMEGREEN, null, null))); // Set background color to Tomato
    runwayView.setClip(clip);


    //End of TEMP code
    getChildren().add(viewSelectionBox);

    var runwayViewTools = new HBox();
    runwayViewTools.setAlignment(Pos.CENTER);
    runwayViewTools.setSpacing(10);
    getChildren().add(runwayViewTools);

    var zoomInButton = new Button("+");
    var zoomOutButton = new Button("-");
    var rotateButton = new Button("Rotate");
    var alignButton = new Button("Align to Compass");
    var resetButton = new Button("Reset");
    togglePanButton = new ToggleButton("Pan");

    runwayViewTools.getChildren().addAll(
        zoomInButton, zoomOutButton, rotateButton, alignButton, togglePanButton, resetButton);
    
    togglePanButton.setOnAction(event -> {
      if(panEnabled) {
        togglePanButton.setSelected(false);
        panEnabled= false;
        topDownRunwayPane.setOnMouseDragged(null);
      }
      else {
        panEnabled = true;
        togglePanButton.setSelected(true);
        
        // Handle mouse press events
        topDownRunwayPane.setOnMousePressed(e -> {
          topDownRunwayPane.initialX = e.getSceneX() - topDownRunwayPane.getTranslateX();
          topDownRunwayPane.initialY = e.getSceneY() - topDownRunwayPane.getTranslateY();
        });
        
        // Handle mouse drag events
        topDownRunwayPane.setOnMouseDragged(e -> {
          topDownRunwayPane.setTranslateX(e.getSceneX() - topDownRunwayPane.initialX);
          topDownRunwayPane.setTranslateY(e.getSceneY() - topDownRunwayPane.initialY);
        });
      }
    });
    
    zoomInButton.setOnAction(event -> {
      // Increase the scale by 10%
      if(!isSimultaneous) {
        double scaleFactor = 1.05;
        topDownRunway.setScaleX(topDownRunway.getScaleX() * scaleFactor);
        topDownRunway.setScaleY(topDownRunway.getScaleY() * scaleFactor);
        sideRunway.setScaleX(topDownRunway.getScaleX() * scaleFactor);
        sideRunway.setScaleY(topDownRunway.getScaleY() * scaleFactor);
      }

    });

    zoomOutButton.setOnAction(event -> {
      // Increase the scale by 10%
      if(!isSimultaneous) {
        double scaleFactor = 0.95;
        topDownRunway.setScaleX(topDownRunway.getScaleX() * scaleFactor);
        topDownRunway.setScaleY(topDownRunway.getScaleY() * scaleFactor);
        sideRunway.setScaleX(topDownRunway.getScaleX() * scaleFactor);
        sideRunway.setScaleY(topDownRunway.getScaleY() * scaleFactor);
      }

    });

    rotateButton.setOnAction(event -> {
      // Rotate the topDownRunway by 10 degrees more
      if(!isSimultaneous) {
        topDownRunway.setRotate(topDownRunway.getRotate() + 10);
        sideRunway.setRotate(sideRunway.getRotate() + 10);
      }

    });

    resetButton.setOnAction(event -> {
      // Reset scale, rotation, and translation to default
      if(isSimultaneous) {
        changeViewToSimultaneous();
      }
      if(topDownRunway.getIsRotated()) {
        topDownRunway.setScaleX(-1.0);
        topDownRunway.setScaleX(-1.0);
      }
      else {
        topDownRunway.setScaleX(1.0);

      }

      if(sideRunway.getIsRotated()) {
        sideRunway.setScaleX(-1.0);
        sideRunway.setScaleX(-1.0);
      }
      else {
        sideRunway.setScaleX(1.0);

      }
      topDownRunway.setScaleY(1.0);
      topDownRunway.setRotate(0);
      topDownRunway.setTranslateX(0);
      topDownRunway.setTranslateY(0);
      topDownRunway.setLabels();

      sideRunway.setScaleY(1.0);
      sideRunway.setRotate(0);
      sideRunway.setTranslateX(1.0);
      sideRunway.setTranslateY(0);
      sideRunway.setLabels();
    });

    alignButton.setOnAction(event -> topDownRunway.setRotate(topDownRunway.calculateRunwayRotation() - 90));
  }



  public Button getSideButton() {
    return sideButton;
  }
  public Button getSimultaneousButton() {
    return simultaneousButton;
  }
  public Button getTopdownButton() {
    return topdownButton;
  }

  public void changeViewToSide() {
    // Clear the existing runway view
    sideRunway.setTranslateY(0);
    sideRunway.setTranslateX(0);

    if(sideRunway.getIsRotated()) {
      sideRunway.setScaleX(-1.0);
      sideRunway.setScaleY(1.0);
    }
    else {
      sideRunway.setScaleX(1.0);
      sideRunway.setScaleY(1.0);

    }
    sideRunway.returnLabelsToFullY();
    runwayView.getChildren().clear();
    runwayView.getChildren().add(sideRunway);
    sideRunway.setLabels();
    isSimultaneous = false;
  }
  public void changeViewToTopdown() {
    if (RunwayBox.getRunwaySelection().getSelectionModel().getSelectedItem() == null){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("No runway selected");
        alert.setContentText("Please select a runway to view");
        alert.showAndWait();
        return;
    }
    // Clear the existing runway view
    topDownRunway.setTranslateY(0);
    topDownRunway.setTranslateX(0);

    if(topDownRunway.getIsRotated()) {
      topDownRunway.setScaleX(-1.0);
      topDownRunway.setScaleY(1.0);
    }
    else {
      topDownRunway.setScaleX(1.0);
      topDownRunway.setScaleY(1.0);

    }
    topDownRunway.setLabelsToNormalY();
    runwayView.getChildren().clear();
    runwayView.getChildren().add(topDownRunway);

    topDownRunway.setLabels();
    setPanButtonFunctionality();
    isSimultaneous = false;
  }
  
  private void setPanButtonFunctionality() {
    togglePanButton.setOnAction(event -> {
      if(panEnabled) {
        togglePanButton.setSelected(false);
        panEnabled= false;
        topDownRunway.setOnMouseDragged(null);
      }
      else {
        panEnabled = true;
        togglePanButton.setSelected(true);
        
        // Handle mouse press events
        topDownRunway.setOnMousePressed(e -> {
          topDownRunway.initialX = e.getSceneX() - topDownRunway.getTranslateX();
          topDownRunway.initialY = e.getSceneY() - topDownRunway.getTranslateY();
        });
        
        // Handle mouse drag events
        topDownRunway.setOnMouseDragged(e -> {
          topDownRunway.setTranslateX(e.getSceneX() - topDownRunway.initialX);
          topDownRunway.setTranslateY(e.getSceneY() - topDownRunway.initialY);
        });
      }
    });
  }
  
  public void changeViewToSimultaneous() {
    // Clear the existing runway view
    runwayView.getChildren().clear();
    StackPane tempStack = new StackPane();
    tempStack.setPrefWidth(900);
    tempStack.setPrefHeight(400);
    tempStack.setAlignment(Pos.CENTER);

    if(topDownRunway.getIsRotated()) {
      topDownRunway.setScaleX(-0.5);
    }
    else {
      topDownRunway.setScaleX(0.5);
    }
    topDownRunway.setScaleY(0.5);

    if(sideRunway.getIsRotated()) {
      sideRunway.setScaleX(-0.5);
    }
    else {
      sideRunway.setScaleX(0.5);
    }
    sideRunway.setScaleY(0.5);

    sideRunway.setTranslateY(100);
    topDownRunway.setTranslateY(-100);
    topDownRunway.setLabelsToHalfYScale();
    sideRunway.setLabelsToHalfYScale();

    tempStack.getChildren().addAll(topDownRunway, sideRunway);
    runwayView.getChildren().add(tempStack);

    sideRunway.setLabels();
    topDownRunway.setLabels();
    isSimultaneous = true;
  }


  public SideRunway getSideRunway() {
    return sideRunway;
  }
  public TopDownRunway getTopDownRunway() {
    return topDownRunway;
  }

  public HBox getRunwayView() {
    return runwayView;
  }
}