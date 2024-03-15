package uk.ac.soton.comp2211.scene;

import javafx.event.Event;
import javafx.geometry.Pos;
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
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    
    private static final Logger logger = LogManager.getLogger(MainScene.class);
    
    //Backend Fields
    private Tool tool;
    private Runway selectedRunway = null;
    private Obstacle selectedObstacle = null;
    
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
        leftPanel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
        leftPanel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        leftPanel.setAlignment(Pos.CENTER);
        leftCollapseButton = new Button("<");
        leftCollapsibleBar = new VBox(leftCollapseButton);
        leftCollapsibleBar.setAlignment(Pos.CENTER);
        
        leftBar = new VBox();
        leftBar.setAlignment(Pos.TOP_CENTER);
        var runwayBox = new RunwayBox();
        var obstacleBox = new ObstaclesBox();
        leftBar.getChildren().addAll(runwayBox,obstacleBox);
        VBox.setVgrow(runwayBox, Priority.ALWAYS);
        VBox.setVgrow(obstacleBox, Priority.ALWAYS);
        leftPanel.getChildren().addAll(leftBar, leftCollapsibleBar);
        mainPane.setLeft(leftPanel);

        //Todo set calculation breakdowns
        //Right Panel
        rightPanel = new HBox();
        rightPanel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
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
                recalculate(selectedRunway, selectedObstacle);
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
    
    private void recalculate(Runway runway, Obstacle obstacle) {
        tool.recalculate(obstacle);
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
    
}
