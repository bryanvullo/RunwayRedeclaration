package uk.ac.soton.comp2211.component;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.model.obstacles.AdvancedObstacle;

public class ObstaclesBox extends VBox {

  private static final Logger logger = LogManager.getLogger(ObstaclesBox.class);
  private Button customButton;
  private ComboBox<AdvancedObstacle> obstacleChooser;

  public ObstaclesBox() {
    logger.info("Creating Obstacles Box");
    obstacleChooser = new ComboBox<>();
    build();
  }

  private void build() {
    logger.info("Building Obstacles Box");
    setAlignment(Pos.TOP_CENTER);
    setSpacing(10);
    setPadding(new Insets(20));

    var title = new Text("Obstacles");
    obstacleChooser.setPromptText("Select an obstacle");
    title.getStyleClass().add("componentTitle");
    getChildren().add(title);


    obstacleChooser.getItems().addAll();
    obstacleChooser.setPromptText("Choose an Obstacle");
    getChildren().add(obstacleChooser);
  }
//  public void addObstacleOptions(ObservableList<String> obstacleNames) {
//    obstacleChooser.getItems().addAll(obstacleNames);
//  }


  public void clearObstacleOptions() {
    obstacleChooser.getItems().clear();
  }

  public void addObstacleOption(AdvancedObstacle obstacle) {
    obstacleChooser.getItems().add(obstacle);
  }

  public void addObstacleOptions(ObservableList<AdvancedObstacle> obstacles) {
    obstacleChooser.getItems().addAll(obstacles);
  }

  public ComboBox<AdvancedObstacle> getObstacleChooser() {
    return obstacleChooser;
  }

  public Button getCustomButton() {
    return customButton;
  }

}