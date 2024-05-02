package uk.ac.soton.comp2211.component;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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

    var viewSelectionBox = new HBox();
    viewSelectionBox.setAlignment(Pos.CENTER);
    viewSelectionBox.setSpacing(10);
    getChildren().add(viewSelectionBox);

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
    runwayVBox = new VBox();
    runwayVBox.setSpacing(100);
    runwayVBox.setAlignment(Pos.CENTER);
    runwayVBox.getChildren().addAll(topDownRunway, sideRunway);
    runwayView.getChildren().add(runwayVBox);
  }


  public VBox getSimulataneaousView() {
    return runwayVBox;
  }

  public SideRunway getSideRunway() {
    return sideRunway;
  }
  public TopDownRunway getTopDownRunway() {
    return topDownRunway;
  }
}