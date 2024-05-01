package uk.ac.soton.comp2211.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SideRunway extends StackPane {
    private static final Logger logger = LogManager.getLogger(SideRunway.class);
    private Rectangle runway;
    private Rectangle obstacle;
    private VBox obstacleContainer;
    private Rectangle blueBackground;
    private double scalingFactor;
    private Arrow RESA;
    private Arrow Sixty;
    private Arrow Hx50;
    private Arrow LDAarrow;
    private Polygon arrowhead1;
    private Polygon arrowhead2;
    private Polygon arrowhead3;
    private Polygon arrowhead4;
    private Polygon arrowhead5;
    private Polygon arrowhead6;
    private Label LDALabel;
    private Label SixtyLabel;
    private Label RESALabel;
    private Polygon directionArrowHead;
    private Arrow directionArrow;
    private Label directionLabel;
    private double obstacleWidth;
    private double toda;
    private double lda;
    private double asda;
    private double tora;
    private double clearway;
    private double stopway;
    private double displacedThreshold;
    private double lThreshold;
    private String runwayName;
    private Arrow clearance;
    private double altVal;




    public SideRunway() {
        logger.info("Creating Side Runway View");
        build();
        this.obstacleWidth = 0;
        this.toda = 0;
        this.asda = 0;
        this.tora = 0;
        this.lda = 0;
        this.displacedThreshold = 0;
        this.lThreshold = 0;
        this.clearway = 0;
        this.stopway = 0;
        this.altVal = 0;
    }
    private void build() {
        logger.info("Building Side Runway View");
        // Code for green background
        Rectangle greenBackground = new Rectangle(900, 400);
        greenBackground.setFill(Color.LIMEGREEN);
        // Code for Blue stripe
        Rectangle purpleBackground = new Rectangle(800 ,350);
        purpleBackground.setFill(Color.web("#7f3d9e"));
        // Code for blue background
        blueBackground = new Rectangle(800, 250);
        blueBackground.setFill(Color.web("#1e90ff"));
        this.getChildren().addAll(greenBackground, purpleBackground, blueBackground);
        obstacleContainer = new VBox();
        this.getChildren().add(obstacleContainer);
        runway = new Rectangle(0, 0, 700, 10);
        runway.setFill(Color.web("#262626"));
        this.getChildren().add(runway);
        this.obstacleWidth = 0;

    }
    public void updateRunway(double toda, double tora, double asda, double lda, double clearway, double stopway, double displacedThreshold, String runwayName) {

        scalingFactor = runway.getWidth()/tora;

        if(this.getChildren().contains(LDAarrow)) {
            this.getChildren().removeAll(arrowhead1, LDAarrow, arrowhead2);
            this.getChildren().removeAll(arrowhead3, Sixty,  arrowhead4);
            this.getChildren().removeAll(arrowhead5, RESA, arrowhead6);
            this.getChildren().removeAll(LDALabel, SixtyLabel, RESALabel);
        }

        double baseWidth = 15;
        double tipY = 0;
        double baseY = 15;

        this.toda = toda;
        this.asda = asda;
        this.tora = tora;
        this.lda = lda;
        this.displacedThreshold = displacedThreshold;
        this.clearway = clearway;
        this.stopway = stopway;
        this.runwayName = runwayName;


        arrowhead1 = createArrowhead(tora, scalingFactor, tipY, baseY, baseWidth, 30);
        arrowhead1.setTranslateX(340);

        LDAarrow = new Arrow(0, 20, (lda/(lda + 1200 )*runway.getWidth() - 60), 20, 12);
        LDAarrow.setTranslateY(30);
        LDAarrow.setTranslateX(runway.getWidth()/2 - (lda/(lda + 1200))*runway.getWidth()/2 - 3 + 15);

        arrowhead2 = createArrowhead(tora, scalingFactor, tipY, baseY, baseWidth, 30);
        arrowhead2.setRotate(-90);
        arrowhead2.setTranslateX(runway.getWidth()/2 - (lda/(lda + 1200))*runway.getWidth()/2 - 3 + 15 - LDAarrow.getWidth()/2);

        arrowhead3 = createArrowhead(tora, scalingFactor, tipY, baseY, baseWidth, 30);
        arrowhead3.setRotate(90);
        arrowhead3.setTranslateX(runway.getWidth()/2 - (lda/(lda + 1200))*runway.getWidth()/2 - 3  - LDAarrow.getWidth()/2);


        Sixty = new Arrow(0, 20, (240/(lda + 1200))*runway.getWidth(), 20, 12);
        Sixty.setTranslateY(30);
        Sixty.setTranslateX(runway.getWidth()/2 - (lda/(lda + 1200))*runway.getWidth()/2 + 3 - 15 - LDAarrow.getWidth()/2 - Sixty.getWidth()/2);

        arrowhead4 = createArrowhead(tora, scalingFactor, tipY, baseY, baseWidth, 30);
        arrowhead4.setRotate(-90);
        arrowhead4.setTranslateX(runway.getWidth()/2 - (lda/(lda + 1200))*runway.getWidth()/2 + 3 - 15 - LDAarrow.getWidth()/2 - Sixty.getWidth());

        arrowhead5 = createArrowhead(tora, scalingFactor, tipY, baseY, baseWidth, 30);
        arrowhead5.setRotate(90);
        arrowhead5.setTranslateX(runway.getWidth()/2 - (lda/(lda + 1200))*runway.getWidth()/2 + 3 - 30 - LDAarrow.getWidth()/2 - Sixty.getWidth());

        RESA = new Arrow(0, 20, (960/(lda + 1200))*runway.getWidth() - 20, 20, 12);
        RESA.setTranslateY(30);
        RESA.setTranslateX(runway.getWidth()/2 - (lda/(lda + 1200))*runway.getWidth()/2 + 9 - 45 - LDAarrow.getWidth()/2 - Sixty.getWidth() - RESA.getWidth()/2);

        arrowhead6 = createArrowhead(tora, scalingFactor, tipY, baseY, baseWidth, 30);
        arrowhead6.setRotate(-90);
        arrowhead6.setTranslateX(runway.getWidth()/2 - (lda/(lda + 1200))*runway.getWidth()/2 + 9 - 45 - LDAarrow.getWidth()/2 - Sixty.getWidth() - RESA.getWidth());

        LDALabel = new Label("LDA: " + lda + "m");
        LDALabel.setFont(Font.font("Arial", FontWeight.BLACK, 12));
        LDALabel.setTranslateY(45);
        LDALabel.setTranslateX(runway.getWidth()/2 - (lda/(lda + 1200))*runway.getWidth()/2 - 3 + 15);
        LDALabel.setTextFill(Color.WHITE);

        SixtyLabel = new Label("60m");
        SixtyLabel.setFont(Font.font("Arial", FontWeight.BLACK, 12));
        SixtyLabel.setTranslateY(45);
        SixtyLabel.setTranslateX(runway.getWidth()/2 - (lda/(lda + 1200))*runway.getWidth()/2 + 3 - 15 - LDAarrow.getWidth()/2 - Sixty.getWidth()/2 + 5);
        SixtyLabel.setTextFill(Color.WHITE);

        RESALabel = new Label("RESA: 240m (min)");
        RESALabel.setFont(Font.font("Arial", FontWeight.BLACK, 12));
        RESALabel.setTranslateY(45);
        RESALabel.setTranslateX(runway.getWidth()/2 - (lda/(lda + 1200))*runway.getWidth()/2 + 9 - 45 - LDAarrow.getWidth()/2 - Sixty.getWidth() - RESA.getWidth()/2 + 10);
        RESALabel.setTextFill(Color.WHITE);



        this.getChildren().addAll(arrowhead1, LDAarrow, arrowhead2);
        this.getChildren().addAll(arrowhead3, Sixty,  arrowhead4);
        this.getChildren().addAll(arrowhead5, RESA, arrowhead6);
        this.getChildren().addAll(LDALabel, SixtyLabel, RESALabel);
        addLeftRunwayDirection();
    }

    public void addObstacle(Double height, Double width, Double length, Double lThreshold, Double rThreshold, Double cThreshold) {

        this.lThreshold = lThreshold;
        this.obstacleWidth = length*2;
        this.altVal = - (-runway.getWidth()/2 + lThreshold*scalingFactor + obstacleWidth/2);

        this.getChildren().removeAll(obstacle);
        if(this.getChildren().contains(Sixty)) {
            this.getChildren().removeAll(arrowhead1, LDAarrow, arrowhead2);
            this.getChildren().removeAll(arrowhead3, Sixty,  arrowhead4);
            this.getChildren().removeAll(arrowhead5, RESA, arrowhead6);
            this.getChildren().removeAll(LDALabel, SixtyLabel, RESALabel);
        }

        updateRunway(toda, asda, tora, lda, clearway, stopway, displacedThreshold, runwayName);

        obstacle = new Rectangle(length * 2, height*2);
        obstacle.setFill(Color.RED);
        obstacle.setTranslateY(-runway.getHeight() - height + 5);
        obstacle.setTranslateX(-runway.getWidth()/2 + lThreshold*scalingFactor + length);
        this.getChildren().add(obstacle);
        clearance = new Arrow(0,height*2 + 10, 50, 0, 12);

    }

    public Polygon createArrowhead(double distance, double scalingFactor, double tipY, double baseY, double width, double translateY) {
        double tipX = distance * scalingFactor + 5; // Tip of the arrowhead at the end of the line
        double baseLeftX = tipX - width / 2; // Left base X coordinate
        double baseRightX = tipX + width / 2; // Right base X coordinate
        Polygon arrowhead = new Polygon();
        arrowhead.getPoints().addAll(tipX, tipY, // Tip
                baseLeftX, baseY, // Base left
                baseRightX, baseY // Base right
        );
        arrowhead.setFill(Color.WHITE);
        arrowhead.setRotate(90); // Rotate the arrowhead to point downwards
        arrowhead.setTranslateX((distance * scalingFactor) / 2 + (-runway.getWidth() + distance * scalingFactor) / 2 + 130);
        arrowhead.setTranslateY(translateY);
        return arrowhead;
    }

    public void addLeftRunwayDirection() {
        if(this.getChildren().contains(directionArrow)) {
            this.getChildren().removeAll(directionArrow, directionLabel, directionArrowHead);
        }
        directionArrow = new Arrow(0, 20, 100, 20, 12);
        directionArrow.setTranslateY(125);
        directionArrow.setTranslateX(-runway.getWidth()/2 + 50);
        directionArrowHead = new Polygon();
        directionArrowHead.getPoints().addAll(100.0, 20.0, // Tip
                80.0, 30.0, // Base left
                80.0, 10.0 // Base right
        );

        directionArrowHead.setFill(Color.WHITE);
        directionArrowHead.setTranslateX(-runway.getWidth()/2 + 100);
        directionArrowHead.setTranslateY(125);

        directionLabel = new Label("Take Off Away, Landing Over");
        directionLabel.setFont(Font.font("Arial", FontWeight.BLACK, 20));
        directionLabel.setTranslateY(125 + 20);
        directionLabel.setTranslateX(-runway.getWidth()/2 + 135);
        directionLabel.setTextFill(Color.WHITE);

        this.getChildren().addAll(directionArrow, directionArrowHead, directionLabel);
    }

    public void addRightRunwayDirection() {
        if(this.getChildren().contains(directionArrow)) {
            this.getChildren().removeAll(directionArrow, directionLabel, directionArrowHead);
            System.out.println(true);
        }
        directionArrow = new Arrow(0, 20, 100, 20, 12);
        directionArrow.setTranslateY(125);
        directionArrow.setTranslateX(runway.getWidth()/2 + - 200);
        directionArrowHead = new Polygon();
        directionArrowHead.getPoints().addAll(100.0, 20.0, // Tip
                80.0, 30.0, // Base left
                80.0, 10.0 // Base right
        );

        directionArrowHead.setFill(Color.WHITE);
        directionArrowHead.setTranslateX(runway.getWidth()/2 - 250);
        directionArrowHead.setRotate(180);
        directionArrowHead.setTranslateY(125);

        directionLabel = new Label("Take Off Towards, Landing Away");
        directionLabel.setFont(Font.font("Arial", FontWeight.BLACK, 20));
        directionLabel.setTranslateY(125 + 20);
        directionLabel.setTranslateX(runway.getWidth()/2 - 135);
        directionLabel.setTextFill(Color.WHITE);

        this.getChildren().addAll(directionArrow, directionArrowHead, directionLabel);
    }



}