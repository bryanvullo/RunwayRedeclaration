package uk.ac.soton.comp2211.component;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.model.obstacles.AdvancedObstacle;

public class ActiveObstacle extends VBox {
    
    private static final Logger logger = LogManager.getLogger(ActiveObstacle.class);
    
    private StringProperty name;
    private DoubleProperty height;
    private DoubleProperty width;
    private DoubleProperty length;
    private DoubleProperty distanceFromLeftThreshold;
    private DoubleProperty distanceFromRightThreshold;
    private DoubleProperty distanceFromCenter;
    
    public ActiveObstacle() {
        logger.info("Creating ActiveObstacle");
        
        name = new SimpleStringProperty();
        height = new SimpleDoubleProperty();
        width = new SimpleDoubleProperty();
        length = new SimpleDoubleProperty();
        distanceFromLeftThreshold = new SimpleDoubleProperty();
        distanceFromRightThreshold = new SimpleDoubleProperty();
        distanceFromCenter = new SimpleDoubleProperty();
        
        name.setValue("No obstacle Selected: ");
        height.setValue(0.0);
        width.setValue(0.0);
        length.setValue(0.0);
        distanceFromLeftThreshold.setValue(0.0);
        distanceFromRightThreshold.setValue(0.0);
        distanceFromCenter.setValue(0.0);
        
        build();
    }
    
    private void build() {
        logger.info("Building Active Obstacle Box");
        
        var title = new Text("Active Obstacle");
        getChildren().add(title);
        
        var nameText = new Text();
        nameText.textProperty().bind(name);
        
        var heightBox = new HBox();
        var heightText = new Text("Height: ");
        var height = new Text();
        height.textProperty().bind(height.textProperty());
        heightBox.getChildren().addAll(heightText, height);
        
        var widthBox = new HBox();
        var widthText = new Text("Width: ");
        var width = new Text();
        width.textProperty().bind(width.textProperty());
        widthBox.getChildren().addAll(widthText, width);
        
        var lengthBox = new HBox();
        var lengthText = new Text("Length: ");
        var length = new Text();
        length.textProperty().bind(length.textProperty());
        lengthBox.getChildren().addAll(lengthText, length);
        
        var distanceFromLeftThresholdBox = new HBox();
        var distanceFromLeftThresholdText = new Text("Distance from Left Threshold: ");
        var distanceFromLeftThreshold = new Text();
        distanceFromLeftThreshold.textProperty().bind(distanceFromLeftThreshold.textProperty());
        distanceFromLeftThresholdBox.getChildren().addAll(distanceFromLeftThresholdText, distanceFromLeftThreshold);
        
        var distanceFromRightThresholdBox = new HBox();
        var distanceFromRightThresholdText = new Text("Distance from Right Threshold: ");
        var distanceFromRightThreshold = new Text();
        distanceFromRightThreshold.textProperty().bind(distanceFromRightThreshold.textProperty());
        distanceFromRightThresholdBox.getChildren().addAll(distanceFromRightThresholdText, distanceFromRightThreshold);
        
        var distanceFromCenterBox = new HBox();
        var distanceFromCenterText = new Text("Distance from Center: ");
        var distanceFromCenter = new Text();
        distanceFromCenter.textProperty().bind(distanceFromCenter.textProperty());
        distanceFromCenterBox.getChildren().addAll(distanceFromCenterText, distanceFromCenter);
        
        getChildren().addAll(nameText, heightBox, widthBox, lengthBox,
            distanceFromLeftThresholdBox, distanceFromRightThresholdBox, distanceFromCenterBox);
    }
    
    public void update(AdvancedObstacle obstacle) {
        logger.info("Updating Active Obstacle Box ");
        
        name.setValue(obstacle.getObstacleName());
        height.setValue(obstacle.getHeight());
        width.setValue(obstacle.getWidth());
        length.setValue(obstacle.getLength());
        distanceFromLeftThreshold.setValue(obstacle.getDistanceLeftThreshold());
        distanceFromRightThreshold.setValue(obstacle.getDistanceRightThreshold());
        distanceFromCenter.setValue(obstacle.getDistanceFromCentre());
    }
    
}
