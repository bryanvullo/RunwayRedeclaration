package uk.ac.soton.comp2211.component;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TopDownRunway extends StackPane {
    private static final Logger logger = LogManager.getLogger(TopDownRunway.class);
    private Rectangle runway;
    private VBox obstacleVBox;

    private Arrow TODAarrow;
    private Arrow LDAarrow;
    private Arrow TORAarrow;
    private Arrow ASDAarrow;
    private VBox DTLVBox;
    private VBox DTTVBox;
    private VBox RDNVBox;
    private VBox RDVLBox;
    private double scalingFactor;



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
        runway = new Rectangle(700, 125);
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
        leftRunwayStripeVBox.setPadding(new Insets(0, runway.getWidth()*5/6 + 50, 0, 0));

        // This code adds 10 right white stripes to the runway in a VBOX
        VBox rightRunwayStripeVBox = new VBox(6);
        rightRunwayStripeVBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < numberOfStripes; i++) {
            Rectangle whiteStripe = new Rectangle(40, 6);
            whiteStripe.setFill(Color.WHITE);
            rightRunwayStripeVBox.getChildren().add(whiteStripe);
        }
        rightRunwayStripeVBox.setPadding(new Insets(0, 0, 0, runway.getWidth()*5/6 + 50));

        this.getChildren().addAll(greenBackground, purpleBackground, blueBackground, runway, runwayCenterLineStripeHBox, leftRunwayStripeVBox, rightRunwayStripeVBox);
    }

    public void updateRunway(double toda, double tora, double asda, double lda, double clearway, double stopway, double displacedThreshold, String runwayName) {
        scalingFactor = runway.getWidth()/tora;
        if(this.getChildren().contains(TODAarrow)) {
            this.getChildren().removeAll(TODAarrow, ASDAarrow, TORAarrow, LDAarrow, DTLVBox, DTTVBox, RDNVBox, RDVLBox);
        }

        TODAarrow = new Arrow(0, 20, toda*scalingFactor - 5, 20);
        ASDAarrow = new Arrow(0, 20, asda*scalingFactor- 5, 20);
        TORAarrow = new Arrow(0, 20, tora*scalingFactor- 5, 20);
        LDAarrow = new Arrow(displacedThreshold*scalingFactor, 20, lda*scalingFactor- 5, 20);

        this.getChildren().addAll(TODAarrow, ASDAarrow, TORAarrow, LDAarrow);
        TODAarrow.setTranslateY(-160);
        ASDAarrow.setTranslateY(-130);
        TORAarrow.setTranslateY(-100);
        LDAarrow.setTranslateY(-70);

        TODAarrow.setTranslateX((TODAarrow.getWidth() - runway.getWidth())/2 + 2);
        ASDAarrow.setTranslateX((ASDAarrow.getWidth() - runway.getWidth())/2 + 2);
        TORAarrow.setTranslateX((TORAarrow.getWidth() - runway.getWidth())/2 + 2);
        LDAarrow.setTranslateX((LDAarrow.getWidth() - runway.getWidth())/2 + displacedThreshold*scalingFactor + 2);

        Rectangle DisplacementThresholdLine = new Rectangle(10, runway.getHeight());
        DTLVBox = new VBox(DisplacementThresholdLine);
        DisplacementThresholdLine.setFill(Color.YELLOW);
        DTLVBox.setTranslateY(200 - runway.getHeight()/2);
        DTLVBox.setTranslateX(displacedThreshold*scalingFactor + 100);

        Label DisplacementThresholdText = new Label("Displacement Threshold");
        DTTVBox = new VBox(DisplacementThresholdText);
        DisplacementThresholdText.setRotate(90);
        DisplacementThresholdText.setFont(Font.font("Arial", FontWeight.BLACK, 10));
        DTTVBox.setTranslateY(260 - runway.getHeight()/2);
        DTTVBox.setTranslateX(46 + displacedThreshold*scalingFactor);

        Label RunwayDirectionNumber = new Label(runwayName.substring(0,2));
        RDNVBox = new VBox(RunwayDirectionNumber);
        RunwayDirectionNumber.setTextFill(Color.WHITE);
        RunwayDirectionNumber.setRotate(90);
        RunwayDirectionNumber.setFont(Font.font("Arial", FontWeight.BLACK, 30));
        RDNVBox.setTranslateY(246 - runway.getHeight()/2);
        RDNVBox.setTranslateX(46 + 165);

        Label RunwayDirectionLetter = new Label(runwayName.substring(2,3));
        RDVLBox = new VBox(RunwayDirectionLetter);
        RunwayDirectionLetter.setTextFill(Color.WHITE);
        RunwayDirectionLetter.setRotate(90);
        RunwayDirectionLetter.setFont(Font.font("Arial", FontWeight.BLACK, 30));
        RDVLBox.setTranslateY(246 - runway.getHeight()/2 - RunwayDirectionLetter.getWidth()/2);
        RDVLBox.setTranslateX(46 + 145);

        this.getChildren().addAll(DTLVBox, DTTVBox, RDNVBox, RDVLBox);
    }

    public void addObstacle(Double height, Double width, Double length, Double lThreshold, Double rThreshold, Double cThreshold) {
        if (this.getChildren().contains(obstacleVBox)) {
            this.getChildren().remove(obstacleVBox);
        }
        Rectangle obstacle = new Rectangle(length*scalingFactor, width*scalingFactor);
        obstacle.setFill(Color.RED);
        obstacleVBox = new VBox();
        obstacleVBox.getChildren().add(obstacle);
        obstacleVBox.setAlignment(Pos.CENTER_LEFT);
        obstacleVBox.setPadding(new Insets(-cThreshold,0,0,lThreshold + 100));
        this.getChildren().add(obstacleVBox);
    }
}