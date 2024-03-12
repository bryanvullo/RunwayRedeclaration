package uk.ac.soton.comp2211.scene;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

public class MainScene extends BaseScene {
    
    private static final Logger logger = LogManager.getLogger(MainScene.class);
    
    public MainScene(AppWindow appWindow) {
        super(appWindow);
    }
    
    @Override
    public void initialise() {
        logger.info("initialising the menu scene");
        
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
        testText.setFill(javafx.scene.paint.Color.WHITE);
        mainPane.setCenter(testText);
        
        //Todo: tool bar MenuItems
        var toolbar = new MenuBar();
        mainPane.setTop(toolbar);
        
        //Todo: add button functionality
        var leftBar = new VBox();
        var runwayBox = new RunwayBox();
        leftBar.getChildren().add(runwayBox);
        var obstacleBox = new ObstaclesBox();
        leftBar.getChildren().add(obstacleBox);
        mainPane.setLeft(leftBar);
        
        //Todo set active obstacle and calculation breakdowns
        var activeBar = new VBox();
        var activeObstacle = new ActiveObstacle();
        activeBar.getChildren().add(activeObstacle);
        var calculationBreakdownBox = new CalculationBreakdown();
        activeBar.getChildren().add(calculationBreakdownBox);
        mainPane.setRight(activeBar);
        
        //Todo: system messages and calculation tab interaction
        var bottomBar = new HBox();
        var systemMessageBox = new SystemMessageBox();
        bottomBar.getChildren().add(systemMessageBox);
        var calcTab = new CalculationTab();
        bottomBar.getChildren().add(calcTab);
        mainPane.setBottom(bottomBar);
    }
    
}
