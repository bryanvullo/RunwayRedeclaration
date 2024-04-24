package uk.ac.soton.comp2211.scene;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.logging.Logger;

import uk.ac.soton.comp2211.UI.AppWindow;
import uk.ac.soton.comp2211.UI.AppPane;
import uk.ac.soton.comp2211.component.ActiveObstacle;
import uk.ac.soton.comp2211.component.CalculationBreakdownBox;
import uk.ac.soton.comp2211.component.CalculationTab;
import uk.ac.soton.comp2211.component.CustomObstacleDialog;
import uk.ac.soton.comp2211.component.MenuBar;
import uk.ac.soton.comp2211.component.ObstacleLocationDialog;
import uk.ac.soton.comp2211.component.ObstaclesBox;
import uk.ac.soton.comp2211.component.RunwayBox;
import uk.ac.soton.comp2211.component.RunwayViewBox;
import uk.ac.soton.comp2211.component.SystemMessageBox;
import uk.ac.soton.comp2211.control.ImportAirportController;
import uk.ac.soton.comp2211.control.ImportObstacleController;
import uk.ac.soton.comp2211.dataStructure.CustomObstacleLocation;
import uk.ac.soton.comp2211.dataStructure.ObstacleLocation;
import uk.ac.soton.comp2211.model.Airport;
import uk.ac.soton.comp2211.model.Runway;
import uk.ac.soton.comp2211.model.Tool;
import uk.ac.soton.comp2211.model.obstacles.*;

public class MainScene extends BaseScene {
  private static MainScene instance = null;
  private static final Logger logger = Logger.getLogger(MainScene.class.getName());

  //Backend Fields
  private static Tool tool;
  private static Runway selectedRunway = null;
  private AdvancedObstacle selectedObstacle = null;
  private static ObstaclesBox obstacleBox = new ObstaclesBox();

  //Collapse panel variables
  private Boolean leftPanelCollapsed = false;
  private Boolean rightPanelCollapsed = false;
  private Button leftCollapseButton;
  private VBox leftCollapsibleBar;
  private Button rightCollapseButton;
  private VBox rightCollapsibleBar;
  private HBox rightPanel;
  private HBox leftPanel;
  private VBox leftBar;
  private VBox activeBar;
  BorderPane mainPane;
  private ActiveObstacle activeObstacle;
  private static RunwayBox runwayBox = new RunwayBox();

  ObservableList<Airport> airportList = FXCollections.observableArrayList();
  private static SystemMessageBox systemMessageBox;
  private static RunwayViewBox runwayViewBox;
  private CalculationBreakdownBox calculationBreakdownBox;

  public MainScene(AppWindow appWindow) {
    super(appWindow); // Make sure this controller is instantiated correctly
    if (obstacleBox == null) {
      obstacleBox = new ObstaclesBox();
    }
  }

  public static MainScene getInstance(AppWindow appWindow) {
    if (instance == null) {
      instance = new MainScene(appWindow);
    }
    return instance;
  }

  @Override
  public void initialise() {
    logger.info("initialising the menu scene");
  }

  @Override
  public void build() {
    logger.info("Building " + this.getClass().getName());

    tool = new Tool();

    root = new AppPane(appWindow.getWidth(), appWindow.getHeight());

    mainPane = new BorderPane();
    mainPane.setMaxWidth(appWindow.getWidth());
    mainPane.setMaxHeight(appWindow.getHeight());
    root.getChildren().add(mainPane);

    runwayViewBox = new RunwayViewBox();
    Button sideButton = runwayViewBox.getSideButton();
    Button simultaneousButton = runwayViewBox.getSimultaneousButton();
    Button topDownButton = runwayViewBox.getTopdownButton();
    sideButton.setOnAction(event -> runwayViewBox.changeViewToSide());
    topDownButton.setOnAction(event -> runwayViewBox.changeViewToTopdown());
    simultaneousButton.setOnAction(event -> runwayViewBox.changeViewToSimultaneous());
    //this is for all backend stuff
    mainPane.setCenter(runwayViewBox);

    //Todo: tool bar MenuItems and functionality
    //Toolbar at the top
    var toolbar = new MenuBar();
    mainPane.setTop(toolbar);

    //Left Panel
    leftPanel = new HBox();
    leftPanel.setBackground(new Background(new BackgroundFill(Color.valueOf("0598ff"), null, null)));
    leftPanel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    leftPanel.setAlignment(Pos.CENTER);
    leftCollapseButton = new Button("<");
    leftCollapsibleBar = new VBox(leftCollapseButton);
    leftCollapsibleBar.setAlignment(Pos.CENTER);

    leftBar = new VBox();
    leftBar.setAlignment(Pos.TOP_CENTER);
    leftBar.getChildren().addAll(runwayBox, obstacleBox);
    VBox.setVgrow(runwayBox, Priority.SOMETIMES);
    VBox.setVgrow(obstacleBox, Priority.SOMETIMES);
    leftPanel.getChildren().addAll(leftBar, leftCollapsibleBar);
    mainPane.setLeft(leftPanel);

    if (runwayBox.getAirportSelection().getItems().isEmpty()) {
      ImportAirportController.loadInitialeAirports();
    }

    if (obstacleBox.getObstacleChooser().getItems().isEmpty()) {
      System.out.println("Loading initial obstacles");
      ImportObstacleController.loadInitialeObstacles();
    }

    //Todo set calculation breakdowns
    //Right Panel
    rightPanel = new HBox();
    rightPanel.setBackground(new Background(new BackgroundFill(Color.valueOf("0598ff"), null, null)));
    rightPanel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    rightPanel.setAlignment(Pos.CENTER);
    rightCollapseButton = new Button(">");
    rightCollapsibleBar = new VBox(rightCollapseButton);
    rightCollapsibleBar.setAlignment(Pos.CENTER);

//    importController.getLoadedObstaclesNames().addListener((ListChangeListener.Change<? extends String> c) -> {
//      while (c.next()) {
//        if (c.wasAdded()) {
//          c.getAddedSubList().forEach(obstacleName -> {
//            Platform.runLater(() -> {
//              logger.info("Adding obstacle to ObstacleBox: " + obstacleName);
//              obstacleBox.addObstacleOption(obstacleName);
//            });
//          });
//        }
//      }
//    });

    activeBar = new VBox();
    activeBar.setAlignment(Pos.TOP_CENTER);
    activeObstacle = new ActiveObstacle();
    calculationBreakdownBox = new CalculationBreakdownBox();
    activeBar.getChildren().addAll(activeObstacle, calculationBreakdownBox);
    VBox.setVgrow(activeObstacle, Priority.ALWAYS);
    VBox.setVgrow(calculationBreakdownBox, Priority.ALWAYS);
    rightPanel.getChildren().addAll(rightCollapsibleBar, activeBar);
    mainPane.setRight(rightPanel);

    //TODO system messages
    //Bottom bar: System message and calculation tab
    var bottomBar = new HBox();
    systemMessageBox = new SystemMessageBox();
    bottomBar.getChildren().add(systemMessageBox);
    var calcTab = new CalculationTab();
    bottomBar.getChildren().add(calcTab);
    HBox.setHgrow(systemMessageBox, Priority.ALWAYS);
    HBox.setHgrow(calcTab, Priority.ALWAYS);
    mainPane.setBottom(bottomBar);

    ///////////////////////Configure the UI///////////////////////
    toolbar.userButton().getItems().get(0).setDisable(true);
//        toolbar.userButton().getItems().get(1).setDisable(true);

    //calculation tab binding
    calcTab.orignalTora.bind(tool.tora);
    calcTab.originalToda.bind(tool.toda);
    calcTab.originalAsda.bind(tool.asda);
    calcTab.originalLda.bind(tool.lda);
    calcTab.recalculatedTora.bind(tool.revisedTora);
    calcTab.recalculatedToda.bind(tool.revisedToda);
    calcTab.recalculatedAsda.bind(tool.revisedAsda);
    calcTab.recalculatedLda.bind(tool.revisedlLda);
    calcTab.previousTora.bind(tool.previousTora);
    calcTab.previousToda.bind(tool.previousToda);
    calcTab.previousAsda.bind(tool.previousAsda);
    calcTab.previousLda.bind(tool.previousLda);

    runwayBox.clearwayProperty().bind(tool.clearway);
    runwayBox.stopwayProperty().bind(tool.stopway);
    runwayBox.displacedThresholdProperty().bind(tool.displacedThreshold);

    leftCollapseButton.setOnAction(this::collapseLeftPanel);
    rightCollapseButton.setOnAction(this::collapseRightPanel);

    //Adding back end functionality
    calcTab.recalculateButton().setOnAction((e) -> {
      logger.info("Recalculate button pressed");
      if (selectedObstacle == null) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No Obstacle Selected");
        alert.setContentText("Please select an obstacle before calculating");
        alert.showAndWait();
      } else if (selectedRunway == null) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No Runway Selected");
        alert.setContentText("Please select a runway before calculating");
        alert.showAndWait();
      } else {
        recalculate();
      }

    });


    obstacleBox.getObstacleChooser().valueProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        handleObstacleSelection(newVal);
      }
    });

//    obstacleBox.getCustomButton().setOnAction((e) -> {
//      logger.info("Custom Button Pressed");
//
//      var obstacle = new AdvancedObstacle();
//
//      getInputAdvancedObstacle(obstacle);
//
//      updateObstacle(obstacle);
//      if (obstacle.getWidth() * 1.5 > runwayViewBox.getTopDownRunway().getRunway().getHeight()) {
//        Alert alert = new Alert(AlertType.ERROR);
//        alert.setTitle("Error");
//        alert.setHeaderText("Invalid Width");
//        alert.setContentText("Obstacle Width is larger than runway width");
//        alert.showAndWait();
//        System.out.println(obstacle.getWidth());
//        System.out.println(runwayViewBox.getTopDownRunway().getRunway().getHeight());
//      } else if (obstacle.getLength() * 1.5 > runwayViewBox.getTopDownRunway().getRunway().getWidth()) {
//        Alert alert = new Alert(AlertType.ERROR);
//        alert.setTitle("Error");
//        alert.setHeaderText("Invalid Length");
//        alert.setContentText("Obstacle Length is larger than runway length");
//        alert.showAndWait();
//        System.out.println(obstacle.getWidth());
//        System.out.println(runwayViewBox.getTopDownRunway().getRunway().getHeight());
//      } else {
//        runwayViewBox.getTopDownRunway().addObstacle(obstacle.getHeight(), obstacle.getWidth(), obstacle.getLength());
//        runwayViewBox.getSideRunway().addObstacle(obstacle.getHeight(), obstacle.getWidth(), obstacle.getLength());
//        System.out.println(obstacle.getWidth());
//        System.out.println(runwayViewBox.getTopDownRunway().getRunway().getHeight());
//      }
//    });


  }


//  private static void updateAirportSelectionUI() {
//    Platform.runLater(() -> {
//      // Clear existing items in the ComboBoxes
//      runwayBox.getAirportSelection().getItems().clear();
//      runwayBox.getRunwaySelection().getItems().clear();
//
//      // Add all airports to the airport selection ComboBox
//      airportRunways.values().forEach(airports -> {
//        runwayBox.getAirportSelection().getItems().addAll(airports);
//      });
//
//      // Log the update
//      System.out.println("Updated UI with new airport and runway data.");
//    });
//  }


  public static void updateAirportsList(Airport newAirport) {
    Platform.runLater(() -> {
      runwayBox.getAirportSelection().getItems().add(newAirport);
    });
  }


  private void handleObstacleSelection(AdvancedObstacle obstacle) {
    if (obstacle != null) {
      System.out.println("Selected Obstacle: " + obstacle.getObstacleName());
      System.out.println("Height: " + obstacle.getHeight());
      System.out.println("Width: " + obstacle.getWidth());
      System.out.println("Length: " + obstacle.getLength());
      System.out.println("Distance Left Threshold: " + obstacle.getDistanceLeftThreshold());
      System.out.println("Distance Right Threshold: " + obstacle.getDistanceRightThreshold());
      System.out.println("Distance from Centre: " + obstacle.getDistanceFromCentre());

      updateObstacle(obstacle);
      runwayViewBox.getTopDownRunway().addObstacle(obstacle.getHeight(), obstacle.getWidth(), obstacle.getLength(), obstacle.getDistanceLeftThreshold(), obstacle.getDistanceRightThreshold(), obstacle.getDistanceFromCentre());
      runwayViewBox.getSideRunway().addObstacle(obstacle.getHeight(), obstacle.getWidth(), obstacle.getLength(), obstacle.getDistanceLeftThreshold(), obstacle.getDistanceRightThreshold(), obstacle.getDistanceFromCentre());
    } else {
      System.out.println("No obstacle selected or obstacle is null");
    }
  }


//  private void selectAirport(Event event) {
//    logger.info("Airport Selected");
//
//    var airport = (String) runwayBox.getAirportSelection().getSelectionModel().getSelectedItem();
//    runwayBox.getRunwaySelection().getItems().clear();
//    runwayBox.getRunwaySelection().setPromptText("Select Runway");
//
//    try {
//      var builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//      var resource = getClass().getResourceAsStream("/xml/newAirports.xml");
//      var doc = builder.parse(resource);
//      var xpath = XPathFactory.newInstance().newXPath();
//      var search = "//airport[name='" + airport + "']/runways/runway";
//      var nodes = (NodeList) xpath.compile(search).evaluate(doc, XPathConstants.NODESET);
//
//      for (int i = 0; i < nodes.getLength(); i++) {
//        var node = nodes.item(i);
//        var name = (String) xpath.compile("name/text()").evaluate(node, XPathConstants.STRING);
//        runwayBox.getRunwaySelection().getItems().add(name);
//      }
//      runwayBox.getRunwaySelection().setDisable(nodes.getLength() == 0);
//
//    } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
//      Alert alert = new Alert(AlertType.ERROR);
//      alert.setTitle("Error");
//      alert.setHeaderText("Unable to fetch runway data");
//      alert.setContentText("Error: " + ex.getMessage());
//      alert.showAndWait();
//    }
//  }


//  private void selectAirport(Event event) {
//    logger.info("Airport Selected");
//
//    var airport = (String) runwayBox.getAirportSelection().getSelectionModel().getSelectedItem();
//    runwayBox.getRunwaySelection().getItems().clear();
//    runwayBox.getRunwaySelection().setPromptText("Select Runway");
//
//    try {
//      var builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//      var resource = getClass().getResourceAsStream("/xml/newAirports.xml"); // Make sure the XML file name is correct
//      System.out.println(resource);
//
//      var doc = builder.parse(resource);
//      var xpath = XPathFactory.newInstance().newXPath();
//      var search = "//airport[name='" + airport + "']/runways/runway";
//      var nodes = (NodeList) xpath.compile(search).evaluate(doc, XPathConstants.NODESET);
//
//      for (int i = 0; i < nodes.getLength(); i++) {
//        var node = nodes.item(i);
//        var name = (String) xpath.compile("name/text()").evaluate(node, XPathConstants.STRING);
//        runwayBox.getAirportSelection().getItems().add(name);
//      }
//      runwayBox.getRunwaySelection().setDisable(nodes.getLength() == 0);
//
//    } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
//      // Popup error message in case of failure
//      Alert alert = new Alert(AlertType.ERROR);
//      alert.setTitle("Error");
//      alert.setHeaderText("Unable to fetch airport data");
//      alert.setContentText("Error: " + ex.getMessage());
//      alert.showAndWait();
//    }
//  }

  public static RunwayViewBox getRunwayViewBox() {
    return runwayViewBox;
  }


//  private void selectRunway(Event e) {
//    logger.info("Runway Selected");
//
//    var airportName = (String) runwayBox.getAirportSelection().getSelectionModel().getSelectedItem();
//    var runwayName = (String) runwayBox.getRunwaySelection().getSelectionModel().getSelectedItem();
//    try {
//      var builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//      var resource = getClass().getResourceAsStream("/xml/newAirports.xml");
//      System.out.println(resource);
//      selectedDirection = "left";
//      var doc = builder.parse(resource);
//      XPath xpath = XPathFactory.newInstance().newXPath();
//      var search = "//airport[name='" + airportName + "']/runways/runway[name='" + runwayName + "']";
//      var node = (Node) xpath.compile(search).evaluate(doc, XPathConstants.NODE);
//
//      if (node != null) {
//        var name = (String) xpath.compile("name").evaluate(node, XPathConstants.STRING);
//        var tora = Double.parseDouble((String) xpath.compile("tora").evaluate(node, XPathConstants.STRING));
//        var toda = Double.parseDouble((String) xpath.compile("toda").evaluate(node, XPathConstants.STRING));
//        var asda = Double.parseDouble((String) xpath.compile("asda").evaluate(node, XPathConstants.STRING));
//        var lda = Double.parseDouble((String) xpath.compile("lda").evaluate(node, XPathConstants.STRING));
//        var clearway = Double.parseDouble((String) xpath.compile("clearway").evaluate(node, XPathConstants.STRING));
//        var stopway = Double.parseDouble((String) xpath.compile("stopway").evaluate(node, XPathConstants.STRING));
//        var displacedThreshold = Double.parseDouble((String) xpath.compile("displacedThreshold").evaluate(node, XPathConstants.STRING));
//
//        var runway = new Runway(name, tora, toda, asda, lda);
//        runway.setClearway(clearway);
//        runway.setStopway(stopway);
//        runway.setDisplacedThreshold(displacedThreshold);
//
//        updateRunway(runway);
//        runwayViewBox.getTopDownRunway().updateArrows(tora, toda, asda, lda);
//      } else {
//        // Handle the case where no matching runway is found
//        Alert alert = new Alert(AlertType.INFORMATION);
//        alert.setTitle("No Runway Found");
//        alert.setHeaderText(null);
//        alert.setContentText("No runway found matching the selection.");
//        alert.showAndWait();
//      }
//
//    } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
//      // Popup error message in case of failure
//      Alert alert = new Alert(AlertType.ERROR);
//      alert.setTitle("Error");
//      alert.setHeaderText("Unable to fetch runway data");
//      alert.setContentText("Error: " + ex.getMessage());
//      alert.showAndWait();
//    }
//  }


  private void collapseLeftPanel(Event event) {
    if (leftPanelCollapsed) {
      leftCollapseButton.setText("<");
      leftPanel.getChildren().clear();
      leftPanel.getChildren().addAll(leftBar, leftCollapsibleBar);
      leftPanel.setPrefWidth(Control.USE_COMPUTED_SIZE);
      leftPanelCollapsed = false;
    } else {
      leftCollapseButton.setText(">");
      leftPanel.getChildren().clear();
      leftPanel.getChildren().addAll(leftCollapsibleBar);
      leftPanel.setPrefWidth(50);
      leftPanelCollapsed = true;
    }
    mainPane.setLeft(leftPanel);
    mainPane.requestLayout();
  }

  private void collapseRightPanel(Event event) {
    if (rightPanelCollapsed) {
      rightCollapseButton.setText(">");
      rightPanel.getChildren().clear();
      rightPanel.getChildren().addAll(rightCollapsibleBar, activeBar);
      rightPanel.setPrefWidth(Control.USE_COMPUTED_SIZE);
      rightPanelCollapsed = false;
    } else {
      rightCollapseButton.setText("<");
      rightPanel.getChildren().clear();
      rightPanel.getChildren().addAll(rightCollapsibleBar);
      rightPanelCollapsed = true;
      rightPanel.setPrefWidth(50);
    }

    mainPane.setRight(rightPanel);
    mainPane.requestLayout(); // Request layout update
  }

  private void recalculate() {
    logger.info("Recalculating");

    // Ensure there is a selected obstacle
    if (selectedObstacle == null) {
      showAlert(AlertType.ERROR, "No Obstacle Selected", "Please select an obstacle before calculating");
      return;
    }

    // Ensure there is a selected runway
    if (selectedRunway == null) {
      showAlert(AlertType.ERROR, "No Runway Selected", "Please select a runway before calculating");
      return;
    }

    // Get the selected direction, defaulting to "Left" if null
    String direction = runwayBox.getSelectedDirection() != null ? runwayBox.getSelectedDirection() : "Left";

    // Set distance from threshold based on selected direction
    selectedObstacle.setDistanceFromThreshold(
        direction.equalsIgnoreCase("Left") ? selectedObstacle.getDistanceLeftThreshold() : selectedObstacle.getDistanceRightThreshold()
    );

    // Determine the calculation type based on the direction and obstacle distance
    String calculationType = direction.equalsIgnoreCase("Left") ?
        (selectedObstacle.getDistanceLeftThreshold() > selectedObstacle.getDistanceRightThreshold() ? "TOTLT" : "TOALO") :
        (selectedObstacle.getDistanceRightThreshold() > selectedObstacle.getDistanceLeftThreshold() ? "TOTLT" : "TOALO");

    // Perform recalculation
    tool.recalculate(selectedObstacle, calculationType);

    // Update the UI with the new calculations
    updateBreakdown();
  }


  private void showAlert(AlertType alertType, String headerText, String contentText) {
    Alert alert = new Alert(alertType);
    alert.setTitle("Calculation Error");
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);
    alert.showAndWait();
  }

  private void updateBreakdown() {
    var breakdown = tool.getRevisedCalculation().calculationBreakdown;
    calculationBreakdownBox.toraBreakdownProperty().bind(breakdown.getToraBreakdown());
    calculationBreakdownBox.todaBreakdownProperty().bind(breakdown.getTodaBreakdown());
    calculationBreakdownBox.asdaBreakdownProperty().bind(breakdown.getAsdaBreakdown());
    calculationBreakdownBox.ldaBreakdownProperty().bind(breakdown.getLdaBreakdown());
  }

  public static void updateRunway(Runway runway) {
    selectedRunway = runway;
    tool.setRunway(runway);
  }

  private void updateObstacleLocation(AdvancedObstacle obstacle) {
    var locationDialog = new ObstacleLocationDialog();
    locationDialog.setResultConverter(button -> {
      if (button == ButtonType.OK) {
        return new ObstacleLocation(
            locationDialog.getDistanceFromLeftThreshold(),
            locationDialog.getDistanceFromRightThreshold(),
            locationDialog.getDistanceFromCentre());
      }
      return null;
    });
    var optionalResult = locationDialog.showAndWait();
    optionalResult.ifPresent((ObstacleLocation result) -> {
      try {
        var distanceLeft = Double.parseDouble(result.getDistanceFromLeftThreshold());
        var distanceRight = Double.parseDouble(result.getDistanceFromRightThreshold());
        var distanceCentre = Double.parseDouble(result.getDistanceFromCentre());
        obstacle.setDistanceLeftThreshold(distanceLeft);
        obstacle.setDistanceRightThreshold(distanceRight);
        obstacle.setDistanceFromCentre(distanceCentre);
      } catch (Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText("Please enter a valid number: \n" + e.getMessage());
        alert.showAndWait();
      }
    });
  }

  private void getInputAdvancedObstacle(AdvancedObstacle customObstacle) {

    var customObstacleDialog = new CustomObstacleDialog();
    customObstacleDialog.setResultConverter(button -> {
      if (button == ButtonType.OK) {
        return new CustomObstacleLocation(
            customObstacleDialog.getName(),
            customObstacleDialog.getHeightValue(),
            customObstacleDialog.getWidthValue(),
            customObstacleDialog.getLength(),
            customObstacleDialog.getDistanceFromLeftThreshold(),
            customObstacleDialog.getDistanceFromRightThreshold(),
            customObstacleDialog.getDistanceFromCentre());
      }
      return null;
    });
    var optionalResult = customObstacleDialog.showAndWait();
    optionalResult.ifPresent((CustomObstacleLocation result) -> {
      try {
        var height = Double.parseDouble(result.getHeight());
        var width = Double.parseDouble(result.getWidth());
        var length = Double.parseDouble(result.getLength());
        var distanceLeft = Double.parseDouble(result.getDistanceFromLeftThreshold());
        var distanceRight = Double.parseDouble(result.getDistanceFromRightThreshold());
        var distanceCentre = Double.parseDouble(result.getDistanceFromCentre());
        customObstacle.setObstacleName(result.getName());
        customObstacle.setHeight(height);
        customObstacle.setWidth(width);
        customObstacle.setLength(length);
        customObstacle.setDistanceLeftThreshold(distanceLeft);
        customObstacle.setDistanceRightThreshold(distanceRight);
        customObstacle.setDistanceFromCentre(distanceCentre);
      } catch (Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText("Please enter a valid number: \n" + e.getMessage());
        alert.showAndWait();
      }
    });
  }

  private void updateObstacle(AdvancedObstacle obstacle) {
    selectedObstacle = obstacle;
    activeObstacle.update(obstacle);
  }

  public static ObstaclesBox getObstaclesBox() {
    return obstacleBox;
  }

  public Parent getRoot() {
    return root;
  }

  public static RunwayBox getRunwayBox() {
    return runwayBox;
  }

  public static SystemMessageBox getSystemMessageBox() {
    return systemMessageBox;
  }



  public static Runway getSelectedRunway() {
    return selectedRunway;
  }
}