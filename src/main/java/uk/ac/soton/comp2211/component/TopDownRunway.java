package uk.ac.soton.comp2211.component;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TopDownRunway extends StackPane {
    private static final Logger logger = LogManager.getLogger(TopDownRunway.class);
    private VBox rightRunwayStripeVBox;
    private HBox runwayCenterLineStripeHBox;
    private VBox leftRunwayStripeVBox;
    private Rectangle blueBackground;
    private Rectangle runway;
    private VBox obstacleVBox;

    private Arrow TODAarrow;
    private Arrow LDAarrow;
    private Arrow TORAarrow;
    private Arrow ASDAarrow;
    private Label TODALabel;
    private Label ASDALabel;
    private Label TORALabel;
    private Label LDALabel;
    private Polygon arrowhead1;
    private Polygon arrowhead2;
    private Polygon arrowhead3;
    private Polygon arrowhead4;
    private Line todaLine1;
    private Line todaLine2;
    private Line asdaLine1;
    private Line asdaLine2;
    private Line toraLine1;
    private Line toraLine2;
    private Line ldaLine1;
    private Line ldaLine2;
    private VBox DTLVBox;
    private VBox DTTVBox;
    private VBox RDNVBox;
    private VBox RDVLBox;
    private double scalingFactor;
    private double scalingFactor2;
    private Rectangle clearwayRect;
    private Label clearwayLabel;
    private Rectangle clip;



    public TopDownRunway() {
        logger.info("Creating Top Down Runway View");
        build();
        // Set the StackPane size to match the Rectangle
        this.setMaxWidth(900);
        this.setMaxHeight(400);
    }
    private void build() {
        logger.info("Building Top Down Runway View");

        // Set up the clipping rectangle with the maximum size
        Rectangle clip = new Rectangle(900, 400);
        this.setClip(clip);

        // Handle scroll events for zooming
        this.setOnScroll(event -> handleZoom(event, this));



        // Code for green background
        Rectangle greenBackground = new Rectangle(900, 400);
        greenBackground.setFill(Color.LIMEGREEN);

        // Code for Blue stripe
        Rectangle purpleBackground = new Rectangle(800 ,350);
        purpleBackground.setFill(Color.web("#7f3d9e"));

        // Code for blue background
        blueBackground = new Rectangle(800, 250);
        blueBackground.setFill(Color.web("#1e90ff"));

        // Code for Runway Strip
        runway = new Rectangle(700, 125);
        runway.setFill(Color.web("#262626"));


        // This code adds 9 center line white stripes to the runway
        int numberOfCenterLineStripes = 9;
         runwayCenterLineStripeHBox = new HBox(20);
        runwayCenterLineStripeHBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < numberOfCenterLineStripes; i++) {
            Rectangle whiteStripe = new Rectangle(22, 5);
            whiteStripe.setFill(Color.WHITE);
            runwayCenterLineStripeHBox.getChildren().add(whiteStripe);
        }

        // This code adds 10 left white stripes to the runway in a VBOX
        int numberOfStripes = 10;
        leftRunwayStripeVBox = new VBox(6);
        leftRunwayStripeVBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < numberOfStripes; i++) {
            Rectangle whiteStripe = new Rectangle(40, 6);
            whiteStripe.setFill(Color.WHITE);
            leftRunwayStripeVBox.getChildren().add(whiteStripe);
        }
        leftRunwayStripeVBox.setPadding(new Insets(0, runway.getWidth()*5/6 + 50, 0, 0));

        // This code adds 10 right white stripes to the runway in a VBOX
        rightRunwayStripeVBox = new VBox(6);
        rightRunwayStripeVBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < numberOfStripes; i++) {
            Rectangle whiteStripe = new Rectangle(40, 6);
            whiteStripe.setFill(Color.WHITE);
            rightRunwayStripeVBox.getChildren().add(whiteStripe);
        }
        rightRunwayStripeVBox.setPadding(new Insets(0, 0, 0, runway.getWidth()*5/6 + 50));

        // This code adds all previous static elements to the build
        this.getChildren().addAll(greenBackground, purpleBackground, blueBackground, runway, runwayCenterLineStripeHBox, leftRunwayStripeVBox, rightRunwayStripeVBox);



    }

    public void updateRunway(double toda, double tora, double asda, double lda, double clearway, double stopway, double displacedThreshold, String runwayName) {

        scalingFactor = runway.getWidth()/tora;
        if(this.getChildren().contains(TODAarrow)) {
            this.getChildren().removeAll(TODAarrow, ASDAarrow, TORAarrow, LDAarrow, clearwayRect, clearwayLabel);
            this.getChildren().removeAll(todaLine1, todaLine2, asdaLine1, asdaLine2, toraLine1, toraLine2, ldaLine1, ldaLine2);
            this.getChildren().removeAll(DTLVBox, DTTVBox, RDNVBox, RDVLBox);
            this.getChildren().removeAll(arrowhead1, arrowhead2, arrowhead3, arrowhead4);
            this.getChildren().removeAll(TODALabel, ASDALabel, TORALabel, LDALabel);
        }
        moveRunwayDetails(displacedThreshold);
        clearwayRect = new Rectangle(clearway*scalingFactor, 150);
        clearwayRect.setFill(Color.web("#564f3d"));
        clearwayRect.setTranslateX(blueBackground.getWidth()/2 - 43);
        this.getChildren().add(clearwayRect);
        coverClearway();


        if(clearway == 0) {
            clearwayLabel = new Label("No Clearway");
        }
        else {
            clearwayLabel = new Label("Clearway");
        }
        clearwayLabel.setRotate(90);
        clearwayLabel.setFont(Font.font("Arial", FontWeight.BLACK, 25));
        clearwayLabel.setTranslateX(blueBackground.getWidth()/2 - 15);
        this.getChildren().add(clearwayLabel);


        TODAarrow = new Arrow(0, 20, toda*scalingFactor - 10, 20, 12);
        ASDAarrow = new Arrow(0, 20, asda*scalingFactor - 10, 20, 12 );
        TORAarrow = new Arrow(0, 20, tora*scalingFactor - 10, 20, 12);
        LDAarrow = new Arrow(displacedThreshold*scalingFactor, 20, tora*scalingFactor - 10, 20, 12);

        addArrowLabels(toda, asda, tora, lda);

        double baseWidth = 15;
        double height = 15;
        double tipX = toda * scalingFactor + 5;
        double tipY = 0;
        double baseLeftX = tipX - baseWidth / 2;
        double baseRightX = tipX + baseWidth / 2;
        double baseY = tipY + height;

        arrowhead1 = createArrowhead(toda, scalingFactor, tipY, baseY, height, baseWidth, -160);
        arrowhead2 = createArrowhead(asda, scalingFactor, tipY, baseY, height, baseWidth, -130);
        arrowhead3 = createArrowhead(tora, scalingFactor, tipY, baseY, height, baseWidth, -100);
        arrowhead4 = createArrowhead(tora, scalingFactor, tipY, baseY, height, baseWidth, -70);

        this.getChildren().addAll(arrowhead1, arrowhead2, arrowhead3, arrowhead4);

        this.getChildren().addAll(TODAarrow, ASDAarrow, TORAarrow, LDAarrow);
        TODAarrow.setTranslateY(-160);
        ASDAarrow.setTranslateY(-130);
        TORAarrow.setTranslateY(-100);
        LDAarrow.setTranslateY(-70);

        TODAarrow.setTranslateX((TODAarrow.getWidth() - runway.getWidth())/2 + 3);
        ASDAarrow.setTranslateX((ASDAarrow.getWidth() - runway.getWidth())/2 + 3);
        TORAarrow.setTranslateX((TORAarrow.getWidth() - runway.getWidth())/2 + 3);
        LDAarrow.setTranslateX((LDAarrow.getWidth() - runway.getWidth())/2 + displacedThreshold*scalingFactor + 3);

        todaLine1 = new Line(100, 100, 100 ,198);
        todaLine1.setStroke(Color.WHITE);
        todaLine1.setTranslateY(-111);
        todaLine1.setTranslateX(-(toda*scalingFactor)/2 + 1 - (runway.getWidth()- toda*scalingFactor)/2 );

        todaLine2 = new Line(100, 100, 100 ,197);
        todaLine2.setStroke(Color.WHITE);
        todaLine2.setTranslateY(-111);
        todaLine2.setTranslateX((toda*scalingFactor)/2 + (-runway.getWidth() + toda*scalingFactor)/2 );

        asdaLine1 = new Line(100, 100, 100 ,167);
        asdaLine1.setStroke(Color.WHITE);
        asdaLine1.setTranslateY(-96);
        asdaLine1.setTranslateX(-(asda*scalingFactor)/2 + 1 - (runway.getWidth()- asda*scalingFactor)/2 );

        asdaLine2 = new Line(100, 100, 100 ,167);
        asdaLine2.setStroke(Color.WHITE);
        asdaLine2.setTranslateY(-96);
        asdaLine2.setTranslateX((asda*scalingFactor)/2 + (-runway.getWidth() + asda*scalingFactor)/2 );

        toraLine1 = new Line(100, 100, 100 ,136);
        toraLine1.setStroke(Color.WHITE);
        toraLine1.setTranslateY(-80);
        toraLine1.setTranslateX(-(tora*scalingFactor)/2 + 1 - (runway.getWidth()- tora*scalingFactor)/2 );

        toraLine2 = new Line(100, 100, 100 ,136);
        toraLine2.setStroke(Color.WHITE);
        toraLine2.setTranslateY(-80);
        toraLine2.setTranslateX((tora*scalingFactor)/2 + (-runway.getWidth() + tora*scalingFactor)/2 );

        ldaLine1 = new Line(100, 100, 100 ,105);
        ldaLine1.setStroke(Color.WHITE);
        ldaLine1.setTranslateY(-65);
        ldaLine1.setTranslateX(-(lda*scalingFactor)/2 + 1 - (runway.getWidth()- lda*scalingFactor)/2 + displacedThreshold*scalingFactor);

        ldaLine2 = new Line(100, 100, 100 ,105);
        ldaLine2.setStroke(Color.WHITE);
        ldaLine2.setTranslateY(-65);
        ldaLine2.setTranslateX((tora*scalingFactor)/2 + (-runway.getWidth() + tora*scalingFactor)/2 );

        this.getChildren().addAll(todaLine1, todaLine2, asdaLine1, asdaLine2, toraLine1, toraLine2, ldaLine1, ldaLine2);



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
        RDNVBox.setTranslateX(200 + displacedThreshold*scalingFactor);

        Label RunwayDirectionLetter = new Label(runwayName.substring(2,3));
        RDVLBox = new VBox(RunwayDirectionLetter);
        RunwayDirectionLetter.setTextFill(Color.WHITE);
        RunwayDirectionLetter.setRotate(90);
        RunwayDirectionLetter.setFont(Font.font("Arial", FontWeight.BLACK, 30));
        RDVLBox.setTranslateY(246 - runway.getHeight()/2 - RunwayDirectionLetter.getWidth()/2);
        RDVLBox.setTranslateX(180+ displacedThreshold*scalingFactor);




        this.getChildren().addAll(DTLVBox, DTTVBox, RDNVBox, RDVLBox);
        System.out.println(tora + " " + asda + " " + tora + " " + lda + " " + stopway + " " + clearway + " " + displacedThreshold);
    }

    public void addObstacle(Double height, Double width, Double length, Double lThreshold, Double rThreshold, Double cThreshold) {
        scalingFactor2 = runway.getHeight()/50;

        if (this.getChildren().contains(obstacleVBox)) {
            this.getChildren().remove(obstacleVBox);
        }
        Rectangle obstacle = new Rectangle(length*scalingFactor2, width*scalingFactor2);
        obstacle.setFill(Color.RED);
        obstacleVBox = new VBox();
        obstacleVBox.getChildren().add(obstacle);
        obstacleVBox.setAlignment(Pos.CENTER_LEFT);
        obstacleVBox.setPadding(new Insets(-cThreshold*scalingFactor,0,0,lThreshold*scalingFactor + 100));
        this.getChildren().add(obstacleVBox);
    }
    public Polygon createArrowhead(double distance, double scalingFactor, double tipY, double baseY, double height, double width, double translateY) {
        double tipX = distance * scalingFactor + 5; // Tip of the arrowhead at the end of the line
        double baseLeftX = tipX - width / 2; // Left base X coordinate
        double baseRightX = tipX + width / 2; // Right base X coordinate

        Polygon arrowhead = new Polygon();
        arrowhead.getPoints().addAll(new Double[]{
                tipX, tipY, // Tip
                baseLeftX, baseY, // Base left
                baseRightX, baseY // Base right
        });

        arrowhead.setFill(Color.WHITE);
        arrowhead.setRotate(90); // Rotate the arrowhead to point downwards
        arrowhead.setTranslateX((distance * scalingFactor) / 2 + (-runway.getWidth() + distance * scalingFactor) / 2 - 8);
        arrowhead.setTranslateY(translateY);

        return arrowhead;
    }

    public void coverClearway() {

        Rectangle runwayCover = new Rectangle(100, runway.getHeight());
        runwayCover.setFill(Color.web("#262626"));
        runwayCover.setTranslateX(300);
        this.getChildren().add(runwayCover);



        this.getChildren().remove(rightRunwayStripeVBox);
        rightRunwayStripeVBox = new VBox(6);
        rightRunwayStripeVBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < 10; i++) {
            Rectangle whiteStripe = new Rectangle(40, 6);
            whiteStripe.setFill(Color.WHITE);
            rightRunwayStripeVBox.getChildren().add(whiteStripe);
        }
        rightRunwayStripeVBox.setPadding(new Insets(0, 0, 0, runway.getWidth()*5/6 + 50));
        this.getChildren().add(rightRunwayStripeVBox);
    }

    public void addArrowLabels(double TODA, double ASDA, double TORA, double LDA) {
        TODALabel = new Label("TODA: " + TODA + "m");
        TODALabel.setFont(Font.font("Arial", FontWeight.BLACK, 12));
        TODALabel.setTranslateY(-168);
        TODALabel.setTranslateX(-150);
        TODALabel.setTextFill(Color.WHITE);

        ASDALabel = new Label("ASDA: " + ASDA + "m");
        ASDALabel.setFont(Font.font("Arial", FontWeight.BLACK, 12));
        ASDALabel.setTranslateY(-138);
        ASDALabel.setTranslateX(-130);
        ASDALabel.setTextFill(Color.WHITE);

        TORALabel = new Label("TORA: " + TORA + "m");
        TORALabel.setFont(Font.font("Arial", FontWeight.BLACK, 12));
        TORALabel.setTranslateY(-108);
        TORALabel.setTranslateX(-110);
        TORALabel.setTextFill(Color.WHITE);

        LDALabel = new Label("LDA: " + LDA + "m");
        LDALabel.setFont(Font.font("Arial", FontWeight.BLACK, 12));
        LDALabel.setTranslateY(-78);
        LDALabel.setTranslateX(-90);
        LDALabel.setTextFill(Color.WHITE);

        this.getChildren().addAll(TODALabel,ASDALabel, TORALabel, LDALabel);
    }

    public void moveRunwayDetails(double displacementThreshold) {

        this.getChildren().remove(runwayCenterLineStripeHBox);
        int numberOfCenterLineStripes = 9;
        runwayCenterLineStripeHBox = new HBox(20);
        runwayCenterLineStripeHBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < numberOfCenterLineStripes; i++) {
            Rectangle whiteStripe = new Rectangle(22, 5);
            whiteStripe.setFill(Color.WHITE);
            runwayCenterLineStripeHBox.getChildren().add(whiteStripe);
        }
        runwayCenterLineStripeHBox.setTranslateX(displacementThreshold*scalingFactor);
        this.getChildren().add(runwayCenterLineStripeHBox);

        this.getChildren().remove(leftRunwayStripeVBox);
        int numberOfStripes = 10;
        leftRunwayStripeVBox = new VBox(6);
        leftRunwayStripeVBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < numberOfStripes; i++) {
            Rectangle whiteStripe = new Rectangle(40, 6);
            whiteStripe.setFill(Color.WHITE);
            leftRunwayStripeVBox.getChildren().add(whiteStripe);
        }
        leftRunwayStripeVBox.setTranslateX(-runway.getWidth()/2 +(displacementThreshold*scalingFactor) + 45);
        this.getChildren().add(leftRunwayStripeVBox);
    }

    private void handleZoom(ScrollEvent event, StackPane pane) {
        double zoomFactor = 1.05;
        double deltaY = event.getDeltaY();

        if (deltaY > 0) {
            pane.setScaleX(pane.getScaleX() * zoomFactor);
            pane.setScaleY(pane.getScaleY() * zoomFactor);
        } else if (deltaY < 0) {
            pane.setScaleX(pane.getScaleX() / zoomFactor);
            pane.setScaleY(pane.getScaleY() / zoomFactor);
        }
    }

}