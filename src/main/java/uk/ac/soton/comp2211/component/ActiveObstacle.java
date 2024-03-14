package uk.ac.soton.comp2211.component;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
        
        name = new SimpleStringProperty("No obstacle Selected: ");
        height = new SimpleDoubleProperty(0.0);
        width = new SimpleDoubleProperty(0.0);
        length = new SimpleDoubleProperty(0.0);
        distanceFromLeftThreshold = new SimpleDoubleProperty(0.0);
        distanceFromRightThreshold = new SimpleDoubleProperty(0.0);
        distanceFromCenter = new SimpleDoubleProperty(0.0);
        
        build();
    }
    
    private void build() {
        logger.info("Building Active Obstacle Box");
        setAlignment(Pos.TOP_CENTER);
        setSpacing(10);
        setPadding(new Insets(20));
        
        var title = new Text("Active Obstacle");
        title.getStyleClass().add("componentTitle");
        getChildren().add(title);
        
        var nameText = new Text();
        nameText.textProperty().bind(name);
        
        var heightBox = new HBox();
        var heightLabel = new Text("Height: ");
        heightLabel.getStyleClass().add("subtitle");
        var heightText = new Text();
        heightText.textProperty().bind(height.asString());
        heightBox.getChildren().addAll(heightLabel, heightText);
        
        var widthBox = new HBox();
        var widthLabel = new Text("Width: ");
        widthLabel.getStyleClass().add("subtitle");
        var widthText = new Text();
        widthText.textProperty().bind(width.asString());
        widthBox.getChildren().addAll(widthLabel, widthText);
        
        var lengthBox = new HBox();
        var lengthLabel = new Text("Length: ");
        lengthLabel.getStyleClass().add("subtitle");
        var lengthText = new Text();
        lengthText.textProperty().bind(length.asString());
        lengthBox.getChildren().addAll(lengthLabel, lengthText);
        
        var distanceFromLeftThresholdBox = new HBox();
        var distanceFromLeftThresholdLabel = new Text("Distance from Left Threshold: ");
        distanceFromLeftThresholdLabel.getStyleClass().add("subtitle");
        var distanceFromLeftThresholdText = new Text();
        distanceFromLeftThresholdText.textProperty().bind(distanceFromLeftThreshold.asString());
        distanceFromLeftThresholdBox.getChildren().addAll(distanceFromLeftThresholdLabel, distanceFromLeftThresholdText);
        
        var distanceFromRightThresholdBox = new HBox();
        var distanceFromRightThresholdLabel = new Text("Distance from Right Threshold: ");
        distanceFromRightThresholdLabel.getStyleClass().add("subtitle");
        var distanceFromRightThresholdText = new Text();
        distanceFromRightThresholdText.textProperty().bind(distanceFromRightThreshold.asString());
        distanceFromRightThresholdBox.getChildren().addAll(distanceFromRightThresholdLabel, distanceFromRightThresholdText);
        
        var distanceFromCenterBox = new HBox();
        var distanceFromCenterLabel = new Text("Distance from Center: ");
        distanceFromCenterLabel.getStyleClass().add("subtitle");
        var distanceFromCenterText = new Text();
        distanceFromCenterText.textProperty().bind(distanceFromCenter.asString());
        distanceFromCenterBox.getChildren().addAll(distanceFromCenterLabel, distanceFromCenterText);
        
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
