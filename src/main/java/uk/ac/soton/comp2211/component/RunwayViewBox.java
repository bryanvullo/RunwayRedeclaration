package uk.ac.soton.comp2211.component;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

  public RunwayViewBox() {
    logger.info("Creating Runway View");
    sideRunway = new SideRunway();
    topDownRunway = new TopDownRunway();
    build();
  }

  private void build() {
    logger.info("Building Runway View");

    setAlignment(Pos.CENTER);
    setSpacing(10);
    setPadding(new Insets(10));

    Rectangle clip = new Rectangle(925, 495);
    this.setClip(clip);

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
    runwayView.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    VBox.setVgrow(runwayView, Priority.ALWAYS);
    getChildren().add(runwayView);

    var topDownRunwayPane = new TopDownRunway();
    runwayView.getChildren().add(topDownRunwayPane);


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
    var panButton = new Button("Pan");
    var resetButton = new Button("Reset");

    runwayViewTools.getChildren().addAll(
        zoomInButton, zoomOutButton, rotateButton, alignButton, panButton, resetButton);

    zoomInButton.setOnAction(event -> {
      // Increase the scale by 10%
      double scaleFactor = 1.05;
      topDownRunway.setScaleX(topDownRunway.getScaleX() * scaleFactor);
      topDownRunway.setScaleY(topDownRunway.getScaleY() * scaleFactor);
      sideRunway.setScaleX(topDownRunway.getScaleX() * scaleFactor);
      sideRunway.setScaleY(topDownRunway.getScaleY() * scaleFactor);
    });

    zoomOutButton.setOnAction(event -> {
      // Increase the scale by 10%
      double scaleFactor = 0.95;
      topDownRunway.setScaleX(topDownRunway.getScaleX() * scaleFactor);
      topDownRunway.setScaleY(topDownRunway.getScaleY() * scaleFactor);
      sideRunway.setScaleX(topDownRunway.getScaleX() * scaleFactor);
      sideRunway.setScaleY(topDownRunway.getScaleY() * scaleFactor);
    });

    rotateButton.setOnAction(event -> {
      // Rotate the topDownRunway by 10 degrees more
      topDownRunway.setRotate(topDownRunway.getRotate() + 10);
      sideRunway.setRotate(sideRunway.getRotate() + 10);
    });

    resetButton.setOnAction(event -> {
      // Reset scale, rotation, and translation to default
      if(topDownRunway.getIsRotated()) {
        topDownRunway.setScaleX(-1.0);
        topDownRunway.setScaleX(-1.0);
      }
      else {
        topDownRunway.setScaleX(1.0);

      }
      topDownRunway.setScaleY(1.0);
      topDownRunway.setRotate(0);
      topDownRunway.setTranslateX(0);
      topDownRunway.setTranslateY(0);
      topDownRunway.setLabels();

      sideRunway.setScaleY(1.0);
      sideRunway.setRotate(0);
      sideRunway.setTranslateX(0);
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
    runwayView.getChildren().clear();
    runwayView.getChildren().add(sideRunway);
    sideRunway.setScaleX(1);
    sideRunway.setScaleY(1);
  }
  public void changeViewToTopdown() {
    // Clear the existing runway view
    runwayView.getChildren().clear();
    runwayView.getChildren().add(topDownRunway);
    topDownRunway.setScaleX(1);
    topDownRunway.setScaleY(1);
  }
  public void changeViewToSimultaneous() {
    // Clear the existing runway view
    runwayView.getChildren().clear();
    StackPane tempStack = new StackPane();
    tempStack.setPrefWidth(900);
    tempStack.setPrefHeight(400);
    tempStack.setAlignment(Pos.CENTER);
    topDownRunway.setScaleX(0.5);
    topDownRunway.setScaleY(0.5);
    topDownRunway.setLabels();
    sideRunway.setScaleX(0.5);
    sideRunway.setScaleY(0.5);
    sideRunway.setTranslateY(100);
    topDownRunway.setTranslateY(-100);
    tempStack.getChildren().addAll(topDownRunway, sideRunway);
    runwayView.getChildren().add(tempStack);
  }


  public SideRunway getSideRunway() {
    return sideRunway;
  }
  public TopDownRunway getTopDownRunway() {
    return topDownRunway;
  }
}