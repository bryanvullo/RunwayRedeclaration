package uk.ac.soton.comp2211.component;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TopDownRunway extends StackPane {
    private static final Logger logger = LogManager.getLogger(TopDownRunway.class);
    private VBox obstacleVBox;


    public TopDownRunway() {
        logger.info("Creating Top Down Runway View");
        build();
    }
    private void build() {
        logger.info("Building Top Down Runway View");

        // Code for green background
        Rectangle greenBackground = new Rectangle(900, 400);
        greenBackground.setFill(Color.LIMEGREEN);

        // Code for Blue stripe
        Rectangle purpleBackground = new Rectangle(800 ,350);
        purpleBackground.setFill(Color.web("#7f3d9e"));

        // Code for blue background
        Rectangle blueBackground = new Rectangle(800, 250);
        blueBackground.setFill(Color.web("#1e90ff"));

        // Code for Runway Strip
        Rectangle runway = new Rectangle(700, 125);
        runway.setFill(Color.web("#262626"));

        // This code adds 9 center line white stripes to the runway
        int numberOfCenterLineStripes = 9;
        HBox runwayCenterLineStripeHBox = new HBox(20);
        runwayCenterLineStripeHBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < numberOfCenterLineStripes; i++) {
            Rectangle whiteStripe = new Rectangle(22, 5);
            whiteStripe.setFill(Color.WHITE);
            runwayCenterLineStripeHBox.getChildren().add(whiteStripe);
        }

        // This code adds 10 left white stripes to the runway in a VBOX
        int numberOfStripes = 10;
        VBox leftRunwayStripeVBox = new VBox(6);
        leftRunwayStripeVBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < numberOfStripes; i++) {
            Rectangle whiteStripe = new Rectangle(40, 6);
            whiteStripe.setFill(Color.WHITE);
            leftRunwayStripeVBox.getChildren().add(whiteStripe);
        }
        leftRunwayStripeVBox.setPadding(new Insets(0, runway.getWidth()*2/3 + 50, 0, 0));

        // This code adds 10 right white stripes to the runway in a VBOX
        VBox rightRunwayStripeVBox = new VBox(6);
        rightRunwayStripeVBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < numberOfStripes; i++) {
            Rectangle whiteStripe = new Rectangle(40, 6);
            whiteStripe.setFill(Color.WHITE);
            rightRunwayStripeVBox.getChildren().add(whiteStripe);
        }
        rightRunwayStripeVBox.setPadding(new Insets(0, 0, 0, runway.getWidth()*2/3 + 50));



        this.getChildren().addAll(greenBackground, purpleBackground, blueBackground, runway, runwayCenterLineStripeHBox, leftRunwayStripeVBox, rightRunwayStripeVBox);
    }

    public void updateRunway(double toda, double tora, double asda, double lda, double clearway, double stopway, double displacedThreshold) {

    }

    public void addObstacle(Double height, Double width, Double length, Double lThreshold, Double rThreshold, Double cThreshold) {
        if (this.getChildren().contains(obstacleVBox)) {
            this.getChildren().remove(obstacleVBox);
        }
        Rectangle obstacle = new Rectangle(length, width);
        obstacle.setFill(Color.RED);
        obstacleVBox = new VBox();
        obstacleVBox.getChildren().add(obstacle);
        obstacleVBox.setAlignment(Pos.CENTER_LEFT);
        obstacleVBox.setPadding(new Insets(-cThreshold ,0,0,lThreshold + 100));
        this.getChildren().add(obstacleVBox);
    }
}