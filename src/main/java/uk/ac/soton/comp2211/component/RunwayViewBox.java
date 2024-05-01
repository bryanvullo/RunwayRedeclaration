package uk.ac.soton.comp2211.component;

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

  private Button topdownButton;
  private Button simultaneousButton;
  private Button sideButton;
  private HBox runwayView;
  private SideRunway sideRunway;
  private TopDownRunway topDownRunway;

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

    topdownButton = new Button("Top-Down View");
    simultaneousButton = new Button("Simultaneous View");
    sideButton = new Button("Side View");

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
    });

    zoomOutButton.setOnAction(event -> {
      // Increase the scale by 10%
      double scaleFactor = 0.95;
      topDownRunway.setScaleX(topDownRunway.getScaleX() * scaleFactor);
      topDownRunway.setScaleY(topDownRunway.getScaleY() * scaleFactor);
    });

    rotateButton.setOnAction(event -> {
      // Rotate the topDownRunway by 10 degrees more
      topDownRunway.setRotate(topDownRunway.getRotate() + 10);
    });

    resetButton.setOnAction(event -> {
      // Reset scale, rotation, and translation to default
      if(topDownRunway.getIsRotated()) {
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
    });
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
  }
  public void changeViewToTopdown() {
    // Clear the existing runway view
    runwayView.getChildren().clear();
    runwayView.getChildren().add(topDownRunway);
  }
  public void changeViewToSimultaneous() {
    // Clear the existing runway view
    runwayView.getChildren().clear();
    VBox runwayVBox = new VBox();
    runwayVBox.setAlignment(Pos.CENTER);
    runwayVBox.getChildren().addAll(topDownRunway, sideRunway);
    runwayView.getChildren().add(runwayVBox);
  }

  public SideRunway getSideRunway() {
    return sideRunway;
  }
  public TopDownRunway getTopDownRunway() {
    return topDownRunway;
  }
}