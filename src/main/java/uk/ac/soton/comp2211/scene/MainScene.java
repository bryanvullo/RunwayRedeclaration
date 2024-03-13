package uk.ac.soton.comp2211.scene;

import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
import javafx.scene.layout.StackPane;
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
import uk.ac.soton.comp2211.component.MenuBar;
import uk.ac.soton.comp2211.component.ObstaclesBox;
import uk.ac.soton.comp2211.component.RunwayBox;
import uk.ac.soton.comp2211.component.SystemMessageBox;
import uk.ac.soton.comp2211.model.Tool;

public class MainScene extends BaseScene {
    
    private static final Logger logger = LogManager.getLogger(MainScene.class);
    
    private Tool tool;
    
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
    
    
    public MainScene(AppWindow appWindow) {
        super(appWindow);
    }
    
    @Override
    public void initialise() {
        logger.info("initialising the menu scene");
        //this is for all backend stuff
        tool = new Tool();
    }
    
    @Override
    public void build() {
        logger.info("Building " + this.getClass().getName());
        
        root = new AppPane(appWindow.getWidth(),appWindow.getHeight());
        
        var mainPane = new BorderPane();
        mainPane.setMaxWidth(appWindow.getWidth());
        mainPane.setMaxHeight(appWindow.getHeight());
        root.getChildren().add(mainPane);
        
        var testText = new Text("This is the main scene");
        testText.setFill(Color.BLACK);
        mainPane.setCenter(testText);
        
        //Todo: tool bar MenuItems
        var toolbar = new MenuBar();
        mainPane.setTop(toolbar);

        //Todo: add button functionality
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

        //Todo set active obstacle and calculation breakdowns
        rightPanel = new HBox();
        rightPanel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
        rightPanel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        rightPanel.setAlignment(Pos.CENTER);
        rightCollapseButton = new Button(">");
        rightCollapsibleBar = new VBox(rightCollapseButton);
        rightCollapsibleBar.setAlignment(Pos.CENTER);
        
        activeBar = new VBox();
        activeBar.setAlignment(Pos.TOP_CENTER);
        var activeObstacle = new ActiveObstacle();
        var calculationBreakdownBox = new CalculationBreakdown();
        activeBar.getChildren().addAll(activeObstacle,calculationBreakdownBox);
        VBox.setVgrow(activeObstacle, Priority.ALWAYS);
        VBox.setVgrow(calculationBreakdownBox, Priority.ALWAYS);
        rightPanel.getChildren().addAll(rightCollapsibleBar, activeBar);
        mainPane.setRight(rightPanel);

        //Todo: calculation tab interaction
        var bottomBar = new HBox();
        var systemMessageBox = new SystemMessageBox();
        bottomBar.getChildren().add(systemMessageBox);
        var calcTab = new CalculationTab();
        bottomBar.getChildren().add(calcTab);
        HBox.setHgrow(systemMessageBox, Priority.ALWAYS);
        HBox.setHgrow(calcTab, Priority.ALWAYS);
        mainPane.setBottom(bottomBar);
        
        //Configure the UI
        toolbar.userButton().getItems().get(0).setDisable(true);
        toolbar.userButton().getItems().get(1).setDisable(true);
        
        leftCollapseButton.setOnAction(this::collapseLeftPanel);
        rightCollapseButton.setOnAction(this::collapseRightPanel);
        
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
    
}
