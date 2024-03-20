package uk.ac.soton.comp2211.scene;

import java.io.File;
import java.io.IOException;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;
import java.util.logging.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import uk.ac.soton.comp2211.UI.AppWindow;
import uk.ac.soton.comp2211.UI.AppPane;
import uk.ac.soton.comp2211.component.ActiveObstacle;
import uk.ac.soton.comp2211.component.CalculationBreakdown;
import uk.ac.soton.comp2211.component.CalculationTab;
import uk.ac.soton.comp2211.component.CustomObstacleDialog;
import uk.ac.soton.comp2211.component.MenuBar;
import uk.ac.soton.comp2211.component.ObstacleLocationDialog;
import uk.ac.soton.comp2211.component.ObstaclesBox;
import uk.ac.soton.comp2211.component.RunwayBox;
import uk.ac.soton.comp2211.component.RunwayViewBox;
import uk.ac.soton.comp2211.component.SystemMessageBox;
import uk.ac.soton.comp2211.dataStructure.CustomObstacleLocation;
import uk.ac.soton.comp2211.dataStructure.ObstacleLocation;
import uk.ac.soton.comp2211.model.Runway;
import uk.ac.soton.comp2211.model.Tool;
import uk.ac.soton.comp2211.model.obstacles.AdvancedObstacle;
import uk.ac.soton.comp2211.model.obstacles.Container;
import uk.ac.soton.comp2211.model.obstacles.Obstacle;
import uk.ac.soton.comp2211.model.obstacles.Plane;
import uk.ac.soton.comp2211.model.obstacles.ShuttleBus;

public class MainScene extends BaseScene {
    
    private static final Logger logger = Logger.getLogger(MainScene.class.getName());
    
    //Backend Fields
    private Tool tool;
    private Runway selectedRunway = null;
    private AdvancedObstacle selectedObstacle = null;
    
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
    private ActiveObstacle activeObstacle;
    private RunwayBox runwayBox;
    
    private String selectedDirection = null;
    
    
    public MainScene(AppWindow appWindow) {
        super(appWindow);
    }
    
    @Override
    public void initialise() {
        logger.info("initialising the menu scene");
        //this is for all backend stuff
    }
    
    @Override
    public void build() {
        logger.info("Building " + this.getClass().getName());
        
        tool = new Tool();
        
        root = new AppPane(appWindow.getWidth(),appWindow.getHeight());
        
        var mainPane = new BorderPane();
        mainPane.setMaxWidth(appWindow.getWidth());
        mainPane.setMaxHeight(appWindow.getHeight());
        root.getChildren().add(mainPane);
        
        var runwayViewBox = new RunwayViewBox();
        mainPane.setCenter(runwayViewBox);
        
        //Todo: tool bar MenuItems and functionality
        //Toolbar at the top
        var toolbar = new MenuBar();
        mainPane.setTop(toolbar);

        //Todo: add runway selection functionality
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
        runwayBox = new RunwayBox();
        var obstacleBox = new ObstaclesBox();
        leftBar.getChildren().addAll(runwayBox,obstacleBox);
        VBox.setVgrow(runwayBox, Priority.ALWAYS);
        VBox.setVgrow(obstacleBox, Priority.ALWAYS);
        leftPanel.getChildren().addAll(leftBar, leftCollapsibleBar);
        mainPane.setLeft(leftPanel);

        //Todo set calculation breakdowns
        //Right Panel
        rightPanel = new HBox();
        rightPanel.setBackground(new Background(new BackgroundFill(Color.valueOf("0598ff"), null, null)));
        rightPanel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        rightPanel.setAlignment(Pos.CENTER);
        rightCollapseButton = new Button(">");
        rightCollapsibleBar = new VBox(rightCollapseButton);
        rightCollapsibleBar.setAlignment(Pos.CENTER);
        
        activeBar = new VBox();
        activeBar.setAlignment(Pos.TOP_CENTER);
        activeObstacle = new ActiveObstacle();
        var calculationBreakdownBox = new CalculationBreakdown();
        activeBar.getChildren().addAll(activeObstacle,calculationBreakdownBox);
        VBox.setVgrow(activeObstacle, Priority.ALWAYS);
        VBox.setVgrow(calculationBreakdownBox, Priority.ALWAYS);
        rightPanel.getChildren().addAll(rightCollapsibleBar, activeBar);
        mainPane.setRight(rightPanel);
        
        //TODO system messages
        //Bottom bar: System message and calculation tab
        var bottomBar = new HBox();
        var systemMessageBox = new SystemMessageBox();
        bottomBar.getChildren().add(systemMessageBox);
        var calcTab = new CalculationTab();
        bottomBar.getChildren().add(calcTab);
        HBox.setHgrow(systemMessageBox, Priority.ALWAYS);
        HBox.setHgrow(calcTab, Priority.ALWAYS);
        mainPane.setBottom(bottomBar);
        
        ///////////////////////Configure the UI///////////////////////
        toolbar.userButton().getItems().get(0).setDisable(true);
        toolbar.userButton().getItems().get(1).setDisable(true);
        
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
            } else  {
                recalculate();
            }
            
        });
        
        obstacleBox.getBoeingButton().setOnAction((e) -> {
            logger.info("Boeing Button Pressed");
            
            var obstacle = Plane.createPlane("Boeing 747");
            
            updateObstacleLocation(obstacle);
            
            updateObstacle( obstacle );
        });
        obstacleBox.getAirbusButton().setOnAction((e) -> {
            logger.info("Airbus Button Pressed");
            
            var obstacle = Plane.createPlane("Airbus A380");
            
            updateObstacleLocation(obstacle);
            
            updateObstacle( obstacle );
        });
        obstacleBox.getContainerButton().setOnAction((e) -> {
            logger.info("Container Button Pressed");
            
            var obstacle = Container.createContainer();
            
            updateObstacleLocation(obstacle);
            
            updateObstacle( obstacle );
        });
        obstacleBox.getShuttleBusButton().setOnAction((e) -> {
            logger.info("Shuttle Bus Button Pressed");
            
            var obstacle = ShuttleBus.createShuttleBus();
            
            updateObstacleLocation(obstacle);
            
            updateObstacle( obstacle );
        });
        obstacleBox.getCustomButton().setOnAction((e) -> {
            logger.info("Custom Button Pressed");
            
            var obstacle = new AdvancedObstacle();
            
            getInputAdvancedObstacle(obstacle);
            
            updateObstacle( obstacle );
        });
        
        runwayBox.getAirportSelection().setOnAction(this::selectAirport);
        runwayBox.getRunwaySelection().setOnAction(this::selectRunway);
    }
    
    private void selectAirport(Event event) {
        logger.info("Airport Selected");
        
        var airport = (String) runwayBox.getAirportSelection().getSelectionModel().getSelectedItem();
        runwayBox.getRunwaySelection().getItems().clear();
        runwayBox.getRunwaySelection().setPromptText("Select Runway");
        
        try {
            //fetching the airport data
            var builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            var doc = builder.parse(new File(getClass().getResource("/xml/airports.xml").getFile()));
            var xpath = XPathFactory.newInstance().newXPath();
            var search = "//airport/name[text()=\"" + airport + "\"]/parent::airport/runways/runway";
            var nodes = (NodeList) xpath.compile(search).evaluate(doc, XPathConstants.NODESET);
            
            for (int i = 0; i < nodes.getLength(); i++) {
                var node = nodes.item(i);
                var name = (String) xpath.compile("name/text()").evaluate(node, XPathConstants.STRING);
                runwayBox.getRunwaySelection().getItems().add(name);
            }
            runwayBox.getRunwaySelection().setDisable(false);
            
        } catch (ParserConfigurationException | SAXException | IOException |
                 XPathExpressionException ex) {
            //popup error message in case of failure
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to fetch airport data");
            alert.setContentText("Error: " + ex.getMessage());
            alert.showAndWait();
        }
    }
    
    private void selectRunway(Event e) {
        logger.info("Runway Selected");
        
        var airportName = (String) runwayBox.getAirportSelection().getSelectionModel().getSelectedItem();
        var runwayName = (String) runwayBox.getRunwaySelection().getSelectionModel().getSelectedItem();
        try {
            var builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            var doc = builder.parse(new File(getClass().getResource("/xml/airports.xml").getFile()));
            XPath xpath = XPathFactory.newInstance().newXPath();
            var search = "//airport/name[text()=\"" + airportName + "\"]/"
                + "parent::airport/runways/runway/name[text()=\"" + runwayName + "\"]"
                + "/parent::runway/logicalRunway";
            var nodes = (NodeList) xpath.compile(search).evaluate(doc, XPathConstants.NODESET);
            
            if (nodes.getLength() == 1) {
                runwayBox.getLogicalLeftButton().setDisable(true);
                runwayBox.getLogicalRightButton().setDisable(true);
                
                var node = nodes.item(0);
                
                var name = (String) xpath.compile("name").evaluate(node, XPathConstants.STRING);
                var tora = (Double) xpath.compile("tora").evaluate(node, XPathConstants.NUMBER);
                var toda = (Double) xpath.compile("toda").evaluate(node, XPathConstants.NUMBER);
                var asda = (Double) xpath.compile("asda").evaluate(node, XPathConstants.NUMBER);
                var lda = (Double) xpath.compile("lda").evaluate(node, XPathConstants.NUMBER);
                var clearway = (Double) xpath.compile("clearway").evaluate(node, XPathConstants.NUMBER);
                var stopway = (Double) xpath.compile("stopway").evaluate(node, XPathConstants.NUMBER);
                var displacedThreshold = (Double) xpath.compile("displacedThreshold").evaluate(node, XPathConstants.NUMBER);
                
                var runway = new Runway(name, tora, toda, asda, lda);
                runway.setClearway(clearway);
                runway.setStopway(stopway);
                runway.setDisplacedThreshold(displacedThreshold);
                
                updateRunway(runway);
            } else if (nodes.getLength() == 2) {
                runwayBox.getLogicalLeftButton().setDisable(true);
                runwayBox.getLogicalRightButton().setDisable(false);
                selectedDirection = "left";
                
                search = search + "[@dir = \"left\"]";
                var node = (Node) xpath.compile(search).evaluate(doc, XPathConstants.NODE);
                
                var name = (String) xpath.compile("name").evaluate(node, XPathConstants.STRING);
                var tora = (Double) xpath.compile("tora").evaluate(node, XPathConstants.NUMBER);
                var toda = (Double) xpath.compile("toda").evaluate(node, XPathConstants.NUMBER);
                var asda = (Double) xpath.compile("asda").evaluate(node, XPathConstants.NUMBER);
                var lda = (Double) xpath.compile("lda").evaluate(node, XPathConstants.NUMBER);
                var clearway = (Double) xpath.compile("clearway").evaluate(node, XPathConstants.NUMBER);
                var stopway = (Double) xpath.compile("stopway").evaluate(node, XPathConstants.NUMBER);
                var displacedThreshold = (Double) xpath.compile("displacedThreshold").evaluate(node, XPathConstants.NUMBER);
                
                var runway = new Runway(name, tora, toda, asda, lda);
                runway.setClearway(clearway);
                runway.setStopway(stopway);
                runway.setDisplacedThreshold(displacedThreshold);
                
                updateRunway(runway);
            }
            
        } catch (ParserConfigurationException | SAXException | IOException |
            XPathExpressionException ex) {
            //popup error message in case of failure
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to fetch runway data");
            alert.setContentText("Error: " + ex.getMessage());
            alert.showAndWait();
        }
        
    }
    
    private void collapseLeftPanel(Event event) {
        if (leftPanelCollapsed) {
            leftCollapseButton.setText("<");
            leftPanel.getChildren().clear();
            leftPanel.getChildren().addAll(leftBar, leftCollapsibleBar);
            leftPanelCollapsed = false;
        }
        else {
            leftCollapseButton.setText(">");
            leftPanel.getChildren().clear();
            leftPanel.getChildren().addAll(leftCollapsibleBar);
            leftPanelCollapsed = true;
        }
    }
    
    private void collapseRightPanel(Event event) {
        if (rightPanelCollapsed) {
            rightCollapseButton.setText(">");
            rightPanel.getChildren().clear();
            rightPanel.getChildren().addAll(rightCollapsibleBar, activeBar);
            rightPanelCollapsed = false;
        }
        else {
            rightCollapseButton.setText("<");
            rightPanel.getChildren().clear();
            rightPanel.getChildren().addAll(rightCollapsibleBar);
            rightPanelCollapsed = true;
        }
    }
    
    private void recalculate() {
        logger.info("Recalculating");
        
        if (selectedDirection.equals("left")) {
            selectedObstacle.setDistanceFromThreshold(selectedObstacle.getDistanceLeftThreshold());
            
            if (selectedObstacle.getDistanceLeftThreshold() > selectedObstacle.getDistanceRightThreshold()) {
                tool.recalculate(selectedObstacle, "TOTLT");
            } else {
                tool.recalculate(selectedObstacle, "TOALO");
            }
            
        } else if (selectedDirection.equals("right")) {
            selectedObstacle.setDistanceFromThreshold(selectedObstacle.getDistanceRightThreshold());
            
            if (selectedObstacle.getDistanceRightThreshold() > selectedObstacle.getDistanceLeftThreshold()) {
                tool.recalculate(selectedObstacle, "TOTLT");
            } else {
                tool.recalculate(selectedObstacle, "TOALO");
            }
        }
        
    }
    
    private void updateRunway(Runway runway) {
        selectedRunway = runway;
        tool.setRunway(runway);
    }
    
    private void updateObstacleLocation(AdvancedObstacle obstacle) {
        var locationDialog = new ObstacleLocationDialog();
        locationDialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                return new ObstacleLocation(
                    Double.parseDouble(locationDialog.getDistanceFromLeftThreshold()),
                    Double.parseDouble(locationDialog.getDistanceFromRightThreshold()),
                    Double.parseDouble(locationDialog.getDistanceFromCentre()));
            }
            return null;
            });
        var optionalResult = locationDialog.showAndWait();
        optionalResult.ifPresent( (ObstacleLocation result) -> {
            obstacle.setDistanceLeftThreshold(result.getDistanceFromLeftThreshold());
            obstacle.setDistanceRightThreshold(result.getDistanceFromRightThreshold());
            obstacle.setDistanceFromCentre(result.getDistanceFromCentre());
        });
    }
    
    private void getInputAdvancedObstacle(AdvancedObstacle customObstacle) {
        
        var customObstacleDialog = new CustomObstacleDialog();
        customObstacleDialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                return new CustomObstacleLocation(
                    customObstacleDialog.getName(),
                    Double.parseDouble(customObstacleDialog.getHeightValue()),
                    Double.parseDouble(customObstacleDialog.getWidthValue()),
                    Double.parseDouble(customObstacleDialog.getLength()),
                    Double.parseDouble(customObstacleDialog.getDistanceFromLeftThreshold()),
                    Double.parseDouble(customObstacleDialog.getDistanceFromRightThreshold()),
                    Double.parseDouble(customObstacleDialog.getDistanceFromCentre()));
            }
            return null;
            });
        var optionalResult = customObstacleDialog.showAndWait();
        optionalResult.ifPresent( (CustomObstacleLocation result) -> {
            customObstacle.setObstacleName(result.getName());
            customObstacle.setHeight(result.getHeight());
            customObstacle.setWidth(result.getWidth());
            customObstacle.setLength(result.getLength());
            customObstacle.setDistanceLeftThreshold(result.getDistanceFromLeftThreshold());
            customObstacle.setDistanceRightThreshold(result.getDistanceFromRightThreshold());
            customObstacle.setDistanceFromCentre(result.getDistanceFromCentre());
        });
    }
    
    private void updateObstacle(AdvancedObstacle obstacle) {
        selectedObstacle = obstacle;
        activeObstacle.update(obstacle);
    }

  public Parent getRoot() {
    return root;
  }
}
