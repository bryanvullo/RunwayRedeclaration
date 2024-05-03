package uk.ac.soton.comp2211.component;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
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
    private double oldtoda;
    private double oldlda;
    private double oldasda;
    private double oldtora;
    private double oldclearway;
    private double oldstopway;
    private double olddisplacedThreshold;
    private double newtoda;
    private double newlda;
    private double newasda;
    private double newtora;
    private double newclearway;
    private double newstopway;
    private double newdisplacedThreshold;
    private double lThreshold;
    private String runwayName;
    private Arrow clearance;
    private double altVal;
    private double oldLDALength;
    private double newcomparisonValue;
    private double obstacleHeight;
    private Arrow ALS;
    private Boolean isRotated;
    private double flip;




    public SideRunway() {
        logger.info("Creating Side Runway View");
        build();
        this.obstacleWidth = 0;
        this.oldtoda = 0;
        this.oldasda = 0;
        this.oldtora = 0;
        this.oldlda = 0;
        this.olddisplacedThreshold = 0;
        this.lThreshold = 0;
        this.oldclearway = 0;
        this.oldstopway = 0;
        this.altVal = 0;
        this.oldLDALength = 0;
        this.flip = 1;
        this.isRotated = false;
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

        this.oldtoda = toda;
        this.oldasda = asda;
        this.oldtora = tora;
        this.oldlda = lda;
        this.olddisplacedThreshold = displacedThreshold;
        this.oldclearway = clearway;
        this.oldstopway = stopway;
        this.runwayName = runwayName;
        this.oldLDALength = oldtora*scalingFactor - 30 - olddisplacedThreshold*scalingFactor;


        arrowhead1 = createArrowhead(tora, scalingFactor, tipY, baseY, baseWidth, 30);
        arrowhead1.setTranslateX(340);

        LDAarrow = new Arrow(displacedThreshold*scalingFactor, 20, tora*scalingFactor - 30, 20, 12);
        LDAarrow.setTranslateY(30);
        LDAarrow.setTranslateX((LDAarrow.getWidth() - runway.getWidth())/2 + displacedThreshold*scalingFactor + 15);

        arrowhead2 = createArrowhead(tora, scalingFactor, tipY, baseY, baseWidth, 30);
        arrowhead2.setRotate(-90);
        arrowhead2.setTranslateX((LDAarrow.getWidth() - runway.getWidth())/2 + displacedThreshold*scalingFactor + 15 - LDAarrow.getWidth()/2);

        LDALabel = new Label("LDA: " + lda + "m");
        LDALabel.setFont(Font.font("Arial", FontWeight.BLACK, 12));
        LDALabel.setTranslateY(45);
        LDALabel.setTranslateX(runway.getWidth()/2 - (lda/(lda + 1200))*runway.getWidth()/2 - 3 + 15);
        LDALabel.setTextFill(Color.WHITE);

        SixtyLabel = new Label("");
        RESALabel = new Label("");

        this.getChildren().addAll(arrowhead1, LDAarrow, arrowhead2);
        this.getChildren().addAll(LDALabel);
        addLeftRunwayDirection();

// Assuming runwayName is a string like "09R/27L"
        if (runwayName.endsWith("R)") && !isRotated) {
            flipRunway();
            isRotated = true;
        }
        else if (runwayName.endsWith("L)") && isRotated) {
            flipRunway();
            isRotated = false;
        }
        setLabels();

    }

    public void addObstacle(Double height, Double width, Double length, Double lThreshold, Double rThreshold, Double cThreshold) {

        this.lThreshold = lThreshold;
        this.obstacleWidth = length*2;
        this.altVal = - (-runway.getWidth()/2 + lThreshold*scalingFactor + obstacleWidth/2);
        this.obstacleHeight = height*2;
        this.getChildren().removeAll(obstacle);
        if(this.getChildren().contains(Sixty)) {
            this.getChildren().removeAll(arrowhead1, LDAarrow, arrowhead2);
            this.getChildren().removeAll(arrowhead3, Sixty,  arrowhead4);
            this.getChildren().removeAll(arrowhead5, RESA, arrowhead6);
            this.getChildren().removeAll(LDALabel, SixtyLabel, RESALabel);
            this.getChildren().removeAll(ALS);
        }

        obstacle = new Rectangle(length * 2, height*2);
        obstacle.setFill(Color.RED);
        obstacle.setTranslateY(-runway.getHeight() - height + 5);
        obstacle.setTranslateX(-runway.getWidth()/2 + lThreshold*scalingFactor + length);
        this.getChildren().add(obstacle);
        clearance = new Arrow(0,height*2 + 10, 50, 0, 12);
        setLabels();
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

    public void updateRunwayWithouScale(double toda, double tora, double asda, double lda, double clearway, double stopway, double displacedThreshold, String runwayName) {

        if(this.getChildren().contains(LDAarrow)) {
            this.getChildren().removeAll(arrowhead1, LDAarrow, arrowhead2);
            this.getChildren().removeAll(arrowhead3, Sixty,  arrowhead4);
            this.getChildren().removeAll(arrowhead5, RESA, arrowhead6);
            this.getChildren().removeAll(LDALabel, SixtyLabel, RESALabel);
            this.getChildren().removeAll(ALS);
        }

        double baseWidth = 15;
        double tipY = 0;
        double baseY = 15;

        this.newtoda = toda;
        this.newasda = asda;
        this.newtora = tora;
        this.newlda = lda;
        this.newdisplacedThreshold = displacedThreshold;
        this.newclearway = clearway;
        this.newstopway = stopway;
        this.runwayName = runwayName;
        this.newcomparisonValue = (newlda/(newlda + 1200 )*runway.getWidth() - 60);
        var remainder = runway.getWidth() - ((newlda/oldlda)* oldLDALength) - 50 - obstacleWidth - lThreshold*scalingFactor;


        arrowhead1 = createArrowhead(tora, scalingFactor, tipY, baseY, baseWidth, 30);
        arrowhead1.setTranslateX(340);

        LDAarrow = new Arrow(0, 20, ((newlda/oldlda)* oldLDALength) - 50, 20, 12);
        LDAarrow.setTranslateY(30);
        LDAarrow.setTranslateX((runway.getWidth() - LDAarrow.getWidth())/2 - 3 - 15);
        System.out.println(((newlda/oldlda)* oldLDALength));
        var remainder2 = oldLDALength - LDAarrow.getWidth();
        var total = runway.getWidth() - lThreshold*scalingFactor - obstacleWidth - 50 - LDAarrow.getWidth();

                arrowhead2 = createArrowhead(0, scalingFactor, tipY, baseY, baseWidth, 30);
        arrowhead2.setRotate(-90);
        arrowhead2.setTranslateX(-LDAarrow.getWidth()/2 + (runway.getWidth() - LDAarrow.getWidth())/2 - 3 - 15);


        arrowhead3 = createArrowhead(0, scalingFactor, tipY, baseY, baseWidth, 30);
        arrowhead3.setRotate(90);
        arrowhead3.setTranslateX(-LDAarrow.getWidth()/2 + (runway.getWidth() - LDAarrow.getWidth())/2 - 3 - 30);


        Sixty = new Arrow(0, 20, remainder/5, 20, 12);
        Sixty.setTranslateY(30);
        Sixty.setTranslateX(-LDAarrow.getWidth()/2 + (runway.getWidth() - LDAarrow.getWidth())/2 + 3 - 45 - Sixty.getWidth()/2);

        arrowhead4 = createArrowhead(0, scalingFactor, tipY, baseY, baseWidth, 30);
        arrowhead4.setRotate(-90);
        arrowhead4.setTranslateX(-LDAarrow.getWidth()/2 + (runway.getWidth() - LDAarrow.getWidth())/2 + 3 - 60 - Sixty.getWidth() + 5);

        arrowhead5 = createArrowhead(0, scalingFactor, tipY, baseY, baseWidth, 30);
        arrowhead5.setRotate(90);
        arrowhead5.setTranslateX(-LDAarrow.getWidth()/2 + (runway.getWidth() - LDAarrow.getWidth())/2 - 3 - 60 - Sixty.getWidth() - 5);

        RESA = new Arrow(0, 20, (remainder/5)*4, 20, 12);
        RESA.setTranslateY(30);
        RESA.setTranslateX(-LDAarrow.getWidth()/2 + (runway.getWidth() - LDAarrow.getWidth())/2 - 3 - 70 - Sixty.getWidth() - 5 - RESA.getWidth()/2);

        arrowhead6 = createArrowhead(tora, scalingFactor, tipY, baseY, baseWidth, 30);
        arrowhead6.setRotate(-90);
        arrowhead6.setTranslateX(-LDAarrow.getWidth()/2 + (runway.getWidth() - LDAarrow.getWidth())/2 - 3 - 70 - Sixty.getWidth() - 5 - RESA.getWidth());

        LDALabel = new Label("LDA: " + newlda + "m");
        LDALabel.setFont(Font.font("Arial", FontWeight.BLACK, 12));
        LDALabel.setTranslateY(45);
        LDALabel.setTranslateX((runway.getWidth() - LDAarrow.getWidth())/2 - 3 - 15);
        LDALabel.setTextFill(Color.WHITE);

        SixtyLabel = new Label("60m");
        SixtyLabel.setFont(Font.font("Arial", FontWeight.BLACK, 12));
        SixtyLabel.setTranslateY(45);
        SixtyLabel.setTranslateX(-LDAarrow.getWidth()/2 + (runway.getWidth() - LDAarrow.getWidth())/2 + 3 - 45 - Sixty.getWidth()/2);
        SixtyLabel.setTextFill(Color.WHITE);

        RESALabel = new Label("RESA: 240m (min)");
        RESALabel.setFont(Font.font("Arial", FontWeight.BLACK, 12));
        RESALabel.setTranslateY(55);
        RESALabel.setTranslateX(-LDAarrow.getWidth()/2 + (runway.getWidth() - LDAarrow.getWidth())/2 - 3 - 70 - Sixty.getWidth() - 5 - RESA.getWidth()/2);
        RESALabel.setTextFill(Color.WHITE);

        var adjacent = RESA.getWidth() + Sixty.getWidth() + obstacleWidth + lThreshold*scalingFactor + 100;

        ALS = new Arrow(0, 0, adjacent, 0, 12);
        ALS.setTranslateX(-LDAarrow.getWidth()/2 + (runway.getWidth() - LDAarrow.getWidth())/2 + 3 - 45 - Sixty.getWidth()/2 - ALS.getWidth()/2 + 21);
        ALS.setTranslateY(-(runway.getHeight()/2) - (2.5));
        this.getChildren().add(ALS);


        var adjacent2 = runway.getWidth() - LDAarrow.getWidth();
        var opposite = obstacleHeight;
        var angle = Math.atan(opposite/adjacent2) + Math.toRadians(2);
        ALS.setRotate(Math.toDegrees(angle));
        var movement = Math.cos(angle)*ALS.getWidth();
        ALS.setTranslateX(-LDAarrow.getWidth()/2 + (runway.getWidth() - LDAarrow.getWidth())/2 + 3 - 45 - Sixty.getWidth()/2 - ALS.getWidth()/2 + 21 + ((ALS.getWidth() - movement)/2));
        var movement2 = Math.sin(angle)*ALS.getWidth()/2;
        ALS.setTranslateY(-(runway.getHeight()/2) - (2.5) - movement2);
        this.getChildren().addAll(arrowhead1, LDAarrow, arrowhead2, LDALabel, arrowhead3, Sixty, arrowhead4, arrowhead5, RESA, arrowhead6, SixtyLabel, RESALabel);
        addLeftRunwayDirection();

        if (runwayName.endsWith("R)") && !isRotated) {
            flipRunway();
            isRotated = true;
        }
        else if (runwayName.endsWith("L)") && isRotated) {
            flipRunway();
            isRotated = false;
        }
        setLabels();
    }

    public void flipRunway() {
        this.setScaleX(-flip);
        this.flip = -flip;
        setLabels();
    }

    public void setLabels() {
        if(this.getChildren().contains(LDALabel)) {
            this.LDALabel.setScaleX(this.getScaleX());
        }
        if(this.getChildren().contains(Sixty)) {
            this.SixtyLabel.setScaleX(this.getScaleX());
        }
        if(this.getChildren().contains(RESALabel)) {
            this.RESALabel.setScaleX(this.getScaleX());
        }
        if(this.getChildren().contains(directionLabel)) {
            this.directionLabel.setScaleX(this.getScaleX());
        }
    }

    public Boolean getIsRotated() {
        return isRotated;
    }



}