package uk.ac.soton.comp2211.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SideRunway extends StackPane {
    private static final Logger logger = LogManager.getLogger(SideRunway.class);
    private Rectangle runway;
    private Rectangle obstacle;
    private VBox obstacleContainer;
    private Rectangle blueBackground;


    public SideRunway() {
        logger.info("Creating Side Runway View");
        build();
    }
    private void build() {
        logger.info("Building Side Runway View");
        // Code for green background
        Rectangle greenBackground = new Rectangle(900, 400);
        greenBackground.setFill(Color.LIMEGREEN);
        greenBackground.setTranslateX(this.getWidth()/2);

        // Code for Blue stripe
        Rectangle purpleBackground = new Rectangle(800 ,350);
        purpleBackground.setFill(Color.web("#7f3d9e"));
        purpleBackground.setTranslateX(this.getWidth()/2);

        // Code for blue background
        blueBackground = new Rectangle(800, 250);
        blueBackground.setFill(Color.web("#1e90ff"));
        blueBackground.setTranslateX(this.getWidth()/2);

        this.getChildren().addAll(greenBackground, purpleBackground, blueBackground);

        obstacleContainer = new VBox();
        this.getChildren().add(obstacleContainer);
        this.setAlignment(Pos.CENTER_LEFT);
        runway = new Rectangle(0, 0, 730, 10);
        runway.setFill(Color.web("#262626"));

        this.getChildren().add(runway);

    }

    public void addObstacle(Double height, Double width, Double length, Double lThreshold, Double rThreshold, Double cThreshold) {
        this.getChildren().remove(obstacleContainer);
        obstacle = new Rectangle(length * 1.5, height * 1.5);
        obstacle.setFill(Color.RED);
        obstacle.setY(runway.getY());
        obstacleContainer = new VBox();
        obstacleContainer.getChildren().add(obstacle);
        obstacleContainer.setPadding(new Insets(0, 0,   runway.getHeight() + obstacle.getHeight(), lThreshold));
        this.getChildren().add(obstacleContainer);
    }

}