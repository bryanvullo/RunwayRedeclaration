package uk.ac.soton.comp2211.component;

import javafx.beans.property.Property;
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
  private Label DisplacementThreshold;
  private Label directionLabel;
  private Label RunwayDirectionNumber;
  private Label RunwayDirectionLetter;
  private Polygon arrowhead1;
  private Polygon arrowhead2;
  private Polygon arrowhead3;
  private Polygon arrowhead4;
  private Polygon directionArrowHead;
  private Line todaLine1;
  private Line todaLine2;
  private Line asdaLine1;
  private Line asdaLine2;
  private Line toraLine1;
  private Line toraLine2;
  private Line ldaLine1;
  private Line ldaLine2;
  private Label recalculateLabel;
  private VBox DTLVBox;
  private double scalingFactor;
  private double scalingFactor2;
  private Rectangle clearwayRect;
  private Label clearwayLabel;
  private Rectangle clip;
  private Label DisplacementThresholdText;
  private Boolean isRotated;
  private Arrow directionArrow;
  private Boolean isLeft;
  private double oldtoda;
  private double oldasda;
  private double oldtora;
  private double oldlda;
  private double oldstopway;
  private double oldclearway;
  private double lDisplacement;
  private double rDisplacement;
  private double displacementInUse;
  private double flip;
  private Boolean isTakeOffAway;
  private String runwayName;
  private double displacedThreshold;
  private double newtoda;
  private double newasda;
  private double newtora;
  private double newlda;
  private double newDisplacementThreshold;
  private Rectangle stopwayRect;
  public double initialX;
  public double initialY;
  private Label stopwayLabel;
  private Rectangle RESARect;
  private double obstacleWidth;
  private double lThreshold;
  private Label RESALabel;

  public Property dragProperty;


  public TopDownRunway() {
    logger.info("Creating Top Down Runway View");
    build();
    this.setMaxWidth(900);
    this.setMaxHeight(400);
    this.isRotated = false;
    this.isLeft = true;
    this.flip = 1;
    this.isTakeOffAway = true;
    this.lDisplacement = 0;

//        // Handle mouse press events
//        this.setOnMousePressed(event -> {
//            initialX = event.getSceneX() - this.getTranslateX();
//            initialY = event.getSceneY() - this.getTranslateY();
//        });
//
//        // Handle mouse drag events
//        this.setOnMouseDragged(event -> {
//            this.setTranslateX(event.getSceneX() - initialX);
//            this.setTranslateY(event.getSceneY() - initialY);
//        });

//        dragProperty = this.onMouseDraggedProperty();
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
    Rectangle purpleBackground = new Rectangle(800, 350);
    purpleBackground.setFill(Color.web("#7f3d9e"));

    // Code for blue background
    blueBackground = new Rectangle(800, 200);
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
    leftRunwayStripeVBox.setPadding(new Insets(0, runway.getWidth() * 5 / 6 + 50, 0, 0));

    // This code adds 10 right white stripes to the runway in a VBOX
    rightRunwayStripeVBox = new VBox(6);
    rightRunwayStripeVBox.setAlignment(Pos.CENTER);
    for (int i = 0; i < numberOfStripes; i++) {
      Rectangle whiteStripe = new Rectangle(40, 6);
      whiteStripe.setFill(Color.WHITE);
      rightRunwayStripeVBox.getChildren().add(whiteStripe);
    }
    rightRunwayStripeVBox.setPadding(new Insets(0, 0, 0, runway.getWidth() * 5 / 6 + 50));

    // This code adds all previous static elements to the build
    this.getChildren().addAll(greenBackground, purpleBackground, blueBackground, runway, runwayCenterLineStripeHBox, leftRunwayStripeVBox, rightRunwayStripeVBox);


  }

  public void updateRunway(double toda, double tora, double asda, double lda, double clearway, double stopway, double displacedThreshold, String runwayName) {
    if (runwayName == null) {
      logger.info("No runway selected, cannot update runway view.");
      return; // Early return if there's no runway to update
    }

    this.runwayName = runwayName;
    this.displacedThreshold = displacedThreshold;
    this.oldtoda = toda;
    this.oldasda = asda;
    this.oldtora = tora;
    this.oldlda = lda;
    this.oldstopway = stopway;
    this.oldclearway = clearway;
    scalingFactor = runway.getWidth() / tora;
    if (this.getChildren().contains(TODAarrow)) {
      this.getChildren().removeAll(TODAarrow, ASDAarrow, TORAarrow, LDAarrow, clearwayRect, clearwayLabel, stopwayRect, stopwayLabel, RESARect, RESALabel);
      this.getChildren().removeAll(todaLine1, todaLine2, asdaLine1, asdaLine2, toraLine1, toraLine2, ldaLine1, ldaLine2);
      this.getChildren().removeAll(DTLVBox, DisplacementThresholdText, RunwayDirectionNumber, RunwayDirectionLetter);
      this.getChildren().removeAll(arrowhead1, arrowhead2, arrowhead3, arrowhead4);
      this.getChildren().removeAll(TODALabel, ASDALabel, TORALabel, LDALabel);
      this.getChildren().removeAll(directionArrow, directionLabel, directionArrowHead);
    }
    if (this.getChildren().contains(RESARect)) {
      this.getChildren().removeAll(RESARect, RESALabel);
    }

    moveRunwayDetails(displacedThreshold);

    stopwayRect = new Rectangle(stopway*scalingFactor, runway.getHeight());
    stopwayRect.setFill(Color.LIMEGREEN);
    stopwayRect.setTranslateX(runway.getWidth()/2 + stopwayRect.getWidth()/2);
    this.getChildren().add(stopwayRect);

    clearwayRect = new Rectangle(clearway*scalingFactor, 150);
    clearwayRect.setFill(Color.web("#564f3d"));
    clearwayRect.setTranslateX(runway.getWidth()/2 + clearwayRect.getWidth()/2);
    this.getChildren().add(clearwayRect);
    coverClearway();


    if (clearway == 0) {
      clearwayLabel = new Label("No Clearway");
    } else {
      clearwayLabel = new Label("Clearway");
    }

    if (stopway == 0) {
      stopwayLabel = new Label("No Stopway");
    } else {
      stopwayLabel = new Label("Stopway");
    }

    stopwayLabel.setRotate(90);
    stopwayLabel.setFont(Font.font("Arial", FontWeight.BLACK, 14));
    stopwayLabel.setTextFill(Color.WHITE);
    stopwayLabel.setTranslateX(blueBackground.getWidth() / 2 - 45);
    this.getChildren().add(stopwayLabel);

    clearwayLabel.setRotate(90);
    clearwayLabel.setFont(Font.font("Arial", FontWeight.BLACK, 25));
    clearwayLabel.setTranslateX(blueBackground.getWidth() / 2 - 15);
    clearwayLabel.setTextFill(Color.WHITE);
    this.getChildren().add(clearwayLabel);


    TODAarrow = new Arrow(0, 20, toda * scalingFactor - 10, 20, 12);
    ASDAarrow = new Arrow(0, 20, asda * scalingFactor - 10, 20, 12);
    TORAarrow = new Arrow(0, 20, tora * scalingFactor - 10, 20, 12);
    LDAarrow = new Arrow(displacedThreshold * scalingFactor, 20, tora * scalingFactor - 10, 20, 12);

    addArrowLabels(toda, asda, tora, lda);

    double baseWidth = 15;
    double height = 15;
    double tipX = toda * scalingFactor + 5;
    double tipY = 0;
    double baseLeftX = tipX - baseWidth / 2;
    double baseRightX = tipX + baseWidth / 2;
    double baseY = tipY + height;

    arrowhead1 = createArrowhead(toda, scalingFactor, tipY, baseY, baseWidth, -160);
    arrowhead2 = createArrowhead(asda, scalingFactor, tipY, baseY, baseWidth, -130);
    arrowhead3 = createArrowhead(tora, scalingFactor, tipY, baseY, baseWidth, -100);
    arrowhead4 = createArrowhead(tora, scalingFactor, tipY, baseY, baseWidth, -70);

    this.getChildren().addAll(arrowhead1, arrowhead2, arrowhead3, arrowhead4);

    this.getChildren().addAll(TODAarrow, ASDAarrow, TORAarrow, LDAarrow);
    TODAarrow.setTranslateY(-160);
    ASDAarrow.setTranslateY(-130);
    TORAarrow.setTranslateY(-100);
    LDAarrow.setTranslateY(-70);

    TODAarrow.setTranslateX((TODAarrow.getWidth() - runway.getWidth()) / 2 + 3 + lDisplacement * scalingFactor);
    ASDAarrow.setTranslateX((ASDAarrow.getWidth() - runway.getWidth()) / 2 + 3 + lDisplacement * scalingFactor);
    TORAarrow.setTranslateX((TORAarrow.getWidth() - runway.getWidth()) / 2 + 3 + lDisplacement * scalingFactor);
    LDAarrow.setTranslateX((LDAarrow.getWidth() - runway.getWidth()) / 2 + displacedThreshold * scalingFactor + 3 + lDisplacement * scalingFactor);

    todaLine1 = new Line(100, 100, 100, 198);
    todaLine1.setStroke(Color.WHITE);
    todaLine1.setTranslateY(-111);
    todaLine1.setTranslateX(-(toda * scalingFactor) / 2 + 1 - (runway.getWidth() - toda * scalingFactor) / 2);

    todaLine2 = new Line(100, 100, 100, 197);
    todaLine2.setStroke(Color.WHITE);
    todaLine2.setTranslateY(-111);
    todaLine2.setTranslateX((toda * scalingFactor) / 2 + (-runway.getWidth() + toda * scalingFactor) / 2);

    asdaLine1 = new Line(100, 100, 100, 167);
    asdaLine1.setStroke(Color.WHITE);
    asdaLine1.setTranslateY(-96);
    asdaLine1.setTranslateX(-(asda * scalingFactor) / 2 + 1 - (runway.getWidth() - asda * scalingFactor) / 2);

    asdaLine2 = new Line(100, 100, 100, 167);
    asdaLine2.setStroke(Color.WHITE);
    asdaLine2.setTranslateY(-96);
    asdaLine2.setTranslateX((asda * scalingFactor) / 2 + (-runway.getWidth() + asda * scalingFactor) / 2);

    toraLine1 = new Line(100, 100, 100, 136);
    toraLine1.setStroke(Color.WHITE);
    toraLine1.setTranslateY(-80);
    toraLine1.setTranslateX(-(tora * scalingFactor) / 2 + 1 - (runway.getWidth() - tora * scalingFactor) / 2);

    toraLine2 = new Line(100, 100, 100, 136);
    toraLine2.setStroke(Color.WHITE);
    toraLine2.setTranslateY(-80);
    toraLine2.setTranslateX((tora * scalingFactor) / 2 + (-runway.getWidth() + tora * scalingFactor) / 2);

    ldaLine1 = new Line(100, 100, 100, 105);
    ldaLine1.setStroke(Color.WHITE);
    ldaLine1.setTranslateY(-65);
    ldaLine1.setTranslateX(-(lda * scalingFactor) / 2 + 1 - (runway.getWidth() - lda * scalingFactor) / 2 + displacedThreshold * scalingFactor);

    ldaLine2 = new Line(100, 100, 100, 105);
    ldaLine2.setStroke(Color.WHITE);
    ldaLine2.setTranslateY(-65);
    ldaLine2.setTranslateX((tora * scalingFactor) / 2 + (-runway.getWidth() + tora * scalingFactor) / 2);

    this.getChildren().addAll(todaLine1, todaLine2, asdaLine1, asdaLine2, toraLine1, toraLine2, ldaLine1, ldaLine2);

    Rectangle DisplacementThresholdLine = new Rectangle(10, runway.getHeight());
    DTLVBox = new VBox(DisplacementThresholdLine);
    DisplacementThresholdLine.setFill(Color.YELLOW);
    DTLVBox.setTranslateY(200 - runway.getHeight() / 2);
    DTLVBox.setTranslateX(displacedThreshold * scalingFactor + 100);

    DisplacementThresholdText = new Label("Displacement Threshold");
    DisplacementThresholdText.setRotate(90);
    DisplacementThresholdText.setFont(Font.font("Arial", FontWeight.BLACK, 10));
    DisplacementThresholdText.setTranslateX(-runway.getWidth() / 2 + (displacedThreshold * scalingFactor) + 5);

    RunwayDirectionNumber = new Label(extractNumbers(extractLastThreeLetters(runwayName)));
    RunwayDirectionNumber.setTextFill(Color.WHITE);
    RunwayDirectionNumber.setRotate(90);
    RunwayDirectionNumber.setFont(Font.font("Arial", FontWeight.BLACK, 30));
    RunwayDirectionNumber.setTranslateX(150 + displacedThreshold*scalingFactor - runway.getWidth()/2);

    RunwayDirectionLetter = new Label(extractLetters(extractLastThreeLetters(runwayName)));
    RunwayDirectionLetter.setTextFill(Color.WHITE);
    RunwayDirectionLetter.setRotate(90);
    RunwayDirectionLetter.setFont(Font.font("Arial", FontWeight.BLACK, 30));
    RunwayDirectionLetter.setTranslateX(120 + displacedThreshold*scalingFactor - runway.getWidth()/2);


    directionArrow = new Arrow(0, 20, 100, 20, 12);


    this.getChildren().addAll(DTLVBox, DisplacementThresholdText, RunwayDirectionNumber, RunwayDirectionLetter);


// Assuming runwayName is a string like "09R/27L"
    if (runwayName.endsWith("R)") && !isRotated) {
      flipRunway();
      isRotated = true;
    } else if (runwayName.endsWith("L)") && isRotated) {
      flipRunway();
      isRotated = false;
    }

    if (runwayName.contains("R")) {
      addRightRunwayDirection();
    } else if (runwayName.contains("L")) {
      addLeftRunwayDirection();
    }

    setLabels();

    addLeftRunwayDirection();
    if (this.getChildren().contains(obstacleVBox)) {
      obstacleVBox.toFront();
    }
    stopwayRect.toFront();
    stopwayLabel.toFront();
  }

  public void flipRunway() {
    this.setScaleX(-flip);
    DisplacementThresholdText.setScaleX(-flip);
    RunwayDirectionLetter.setScaleX(-flip);
    RunwayDirectionNumber.setScaleX(-flip);
    if (this.getChildren().contains(obstacleVBox)) {
      obstacleVBox.toFront();
    }
    this.flip = -flip;
  }


  public void addObstacle(Double height, Double width, Double length, Double lThreshold, Double rThreshold, Double cThreshold) {
    scalingFactor2 = runway.getHeight() / 50;
    this.obstacleWidth = length * scalingFactor2;
    this.lThreshold = lThreshold * scalingFactor;
    if (this.getChildren().contains(obstacleVBox)) {
      this.getChildren().remove(obstacleVBox);
    }


    Rectangle obstacle = new Rectangle(length * scalingFactor2, width * scalingFactor2);
    obstacle.setFill(Color.RED);
    obstacleVBox = new VBox();
    obstacleVBox.getChildren().add(obstacle);
    obstacleVBox.setAlignment(Pos.CENTER_LEFT);
    obstacleVBox.setPadding(new Insets(-cThreshold * scalingFactor, 0, 0, lThreshold * scalingFactor + 100));
    this.getChildren().add(obstacleVBox);

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
    rightRunwayStripeVBox.setPadding(new Insets(0, 0, 0, runway.getWidth() * 5 / 6 + 50));
    this.getChildren().add(rightRunwayStripeVBox);
  }

  public void addArrowLabels(double TODA, double ASDA, double TORA, double LDA) {
    this.getChildren().removeAll(TODALabel, ASDALabel, TORALabel, LDALabel);

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

    this.getChildren().addAll(TODALabel, ASDALabel, TORALabel, LDALabel);
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
    runwayCenterLineStripeHBox.setTranslateX(displacementThreshold * scalingFactor);
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
    leftRunwayStripeVBox.setTranslateX(-runway.getWidth() / 2 + (displacementThreshold * scalingFactor) + 45);
    this.getChildren().add(leftRunwayStripeVBox);
  }

  public void handleZoom(ScrollEvent event, StackPane pane) {
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


  public void addLeftRunwayDirection() {
    if (this.getChildren().contains(directionArrow)) {
      this.getChildren().removeAll(directionArrow, directionLabel, directionArrowHead);
    }
    directionArrow = new Arrow(0, 20, 100, 20, 12);
    directionArrow.setTranslateY(125);
    directionArrow.setTranslateX(-runway.getWidth() / 2 + 50);
    directionArrowHead = new Polygon();
    directionArrowHead.getPoints().addAll(100.0, 20.0, // Tip
        80.0, 30.0, // Base left
        80.0, 10.0 // Base right
    );

    directionArrowHead.setFill(Color.WHITE);
    directionArrowHead.setTranslateX(-runway.getWidth() / 2 + 100);
    directionArrowHead.setTranslateY(125);

    directionLabel = new Label("Take Off Away, Landing Over");
    directionLabel.setFont(Font.font("Arial", FontWeight.BLACK, 20));
    directionLabel.setTranslateY(125 + 20);
    directionLabel.setTranslateX(-runway.getWidth() / 2 + 135);
    directionLabel.setTextFill(Color.WHITE);
    directionLabel.setScaleX(this.getScaleX());
    isTakeOffAway = true;
    this.getChildren().addAll(directionArrow, directionArrowHead, directionLabel);
  }

  public void addRightRunwayDirection() {

    if (this.getChildren().contains(directionArrow)) {
      this.getChildren().removeAll(directionArrow, directionLabel, directionArrowHead);
      System.out.println(true);
    }
    directionArrow = new Arrow(0, 20, 100, 20, 12);
    directionArrow.setTranslateY(125);
    directionArrow.setTranslateX(runway.getWidth() / 2 + -200);
    directionArrowHead = new Polygon();
    directionArrowHead.getPoints().addAll(100.0, 20.0, // Tip
        80.0, 30.0, // Base left
        80.0, 10.0 // Base right
    );

    directionArrowHead.setFill(Color.WHITE);
    directionArrowHead.setTranslateX(runway.getWidth() / 2 - 250);
    directionArrowHead.setRotate(180);
    directionArrowHead.setTranslateY(125);

    directionLabel = new Label("Take Off Towards, Landing Away");
    directionLabel.setFont(Font.font("Arial", FontWeight.BLACK, 20));
    directionLabel.setTranslateY(125 + 20);
    directionLabel.setTranslateX(runway.getWidth() / 2 - 135);
    directionLabel.setTextFill(Color.WHITE);
    directionLabel.setScaleX(this.getScaleX());
    isTakeOffAway = false;
    directionLabel.setScaleX(this.getScaleX());
    this.getChildren().addAll(directionArrow, directionArrowHead, directionLabel);
  }

  public void updateRunwayWithoutScale(double toda, double tora, double asda, double lda, double clearway, double stopway, double displacedThreshold, String runwayName) {

    this.newtoda = toda;
    this.newasda = asda;
    this.newtora = tora;
    this.newlda = lda;
    this.newDisplacementThreshold = displacedThreshold;

    if (this.getChildren().contains(TODAarrow)) {
      this.getChildren().removeAll(TODAarrow, ASDAarrow, TORAarrow, LDAarrow, clearwayRect, clearwayLabel, stopwayRect, stopwayLabel);
      this.getChildren().removeAll(todaLine1, todaLine2, asdaLine1, asdaLine2, toraLine1, toraLine2, ldaLine1, ldaLine2);
      this.getChildren().removeAll(DTLVBox, DisplacementThresholdText, RunwayDirectionNumber, RunwayDirectionLetter);
      this.getChildren().removeAll(arrowhead1, arrowhead2, arrowhead3, arrowhead4);
      this.getChildren().removeAll(TODALabel, ASDALabel, TORALabel, LDALabel);
      this.getChildren().removeAll(RESARect, RESALabel, stopwayRect, stopwayLabel);
    }


    double toraPush = oldtora - newtora;
    double asdaPush = oldasda - newasda;
    double todaPush = oldtoda - newtoda;
    double ldaPush = oldlda - newlda;


    moveRunwayDetails(displacedThreshold);
    
    stopwayRect = new Rectangle(stopway*scalingFactor, runway.getHeight());
    stopwayRect.setFill(Color.LIMEGREEN);
    stopwayRect.setTranslateX(runway.getWidth()/2 - stopwayRect.getWidth()/2);
    this.getChildren().add(stopwayRect);

    clearwayRect = new Rectangle(clearway*scalingFactor, 150);
    clearwayRect.setFill(Color.web("#564f3d"));
    clearwayRect.setTranslateX(runway.getWidth()/2 + clearwayRect.getWidth()/2);
    this.getChildren().add(clearwayRect);
    coverClearway();


    if (clearway == 0) {
      clearwayLabel = new Label("No Clearway");
    } else {
      clearwayLabel = new Label("Clearway");
    }

    if (stopway == 0) {
      stopwayLabel = new Label("No Stopway");
    } else {
      stopwayLabel = new Label("Stopway");
    }

    stopwayLabel.setRotate(90);
    stopwayLabel.setFont(Font.font("Arial", FontWeight.BLACK, 14));
    stopwayLabel.setTextFill(Color.WHITE);
    stopwayLabel.setTranslateX(blueBackground.getWidth() / 2 - 45);
    clearwayLabel.setTextFill(Color.WHITE);
    this.getChildren().add(stopwayLabel);

    clearwayLabel.setRotate(90);
    clearwayLabel.setFont(Font.font("Arial", FontWeight.BLACK, 25));
    clearwayLabel.setTranslateX(blueBackground.getWidth() / 2 - 15);
    this.getChildren().add(clearwayLabel);


    TODAarrow = new Arrow(0, 20, newtoda * scalingFactor - 10, 20, 12);
    ASDAarrow = new Arrow(0, 20, newasda * scalingFactor - 10, 20, 12);
    TORAarrow = new Arrow(0, 20, newtora * scalingFactor - 10, 20, 12);
    LDAarrow = new Arrow(0, 20, newlda * scalingFactor - 10, 20, 12);

    addArrowLabels(toda, asda, tora, lda);

    double baseWidth = 15;
    double height = 15;
    double tipY = 0;
    double baseY = tipY + height;

    arrowhead1 = createArrowhead(newtoda + todaPush, scalingFactor, tipY, baseY, baseWidth, -160);
    arrowhead2 = createArrowhead(newasda + asdaPush, scalingFactor, tipY, baseY, baseWidth, -130);
    arrowhead3 = createArrowhead(newtora + toraPush, scalingFactor, tipY, baseY, baseWidth, -100);
    arrowhead4 = createArrowhead(newtora + toraPush, scalingFactor, tipY, baseY, baseWidth, -70);


    this.getChildren().addAll(TODAarrow, ASDAarrow, TORAarrow, LDAarrow, arrowhead1, arrowhead2, arrowhead3, arrowhead4);
    TODAarrow.setTranslateY(-160);
    ASDAarrow.setTranslateY(-130);
    TORAarrow.setTranslateY(-100);
    LDAarrow.setTranslateY(-70);

    TODAarrow.setTranslateX((TODAarrow.getWidth() + 2 * (todaPush * scalingFactor) - runway.getWidth()) / 2 + 3);
    ASDAarrow.setTranslateX((ASDAarrow.getWidth() + 2 * (asdaPush * scalingFactor) - runway.getWidth()) / 2 + 3);
    TORAarrow.setTranslateX((TORAarrow.getWidth() + 2 * (toraPush * scalingFactor) - runway.getWidth()) / 2 + 3);
    LDAarrow.setTranslateX((runway.getWidth() - LDAarrow.getWidth()) / 2 - 8);

    todaLine1 = new Line(100, 100, 100, 197);
    todaLine1.setStroke(Color.WHITE);
    todaLine1.setTranslateY(-111);
    todaLine1.setTranslateX(-(newtoda * scalingFactor) / 2 + 1 - (runway.getWidth() - newtoda * scalingFactor) / 2 + (todaPush * scalingFactor));

    todaLine2 = new Line(100, 100, 100, 197);
    todaLine2.setStroke(Color.WHITE);
    todaLine2.setTranslateY(-111);
    todaLine2.setTranslateX((newtoda * scalingFactor) / 2 + (-runway.getWidth() + newtoda * scalingFactor) / 2 + todaPush * scalingFactor);

    asdaLine1 = new Line(100, 100, 100, 167);
    asdaLine1.setStroke(Color.WHITE);
    asdaLine1.setTranslateY(-96);
    asdaLine1.setTranslateX(-(newasda * scalingFactor) / 2 + 1 - (runway.getWidth() - newasda * scalingFactor) / 2 + asdaPush * scalingFactor);

    asdaLine2 = new Line(100, 100, 100, 167);
    asdaLine2.setStroke(Color.WHITE);
    asdaLine2.setTranslateY(-96);
    asdaLine2.setTranslateX((newasda * scalingFactor) / 2 + (-runway.getWidth() + newasda * scalingFactor) / 2 + asdaPush * scalingFactor);

    toraLine1 = new Line(100, 100, 100, 136);
    toraLine1.setStroke(Color.WHITE);
    toraLine1.setTranslateY(-80);
    toraLine1.setTranslateX(-(newtora * scalingFactor) / 2 + 1 - (runway.getWidth() - newtora * scalingFactor) / 2 + toraPush * scalingFactor);

    toraLine2 = new Line(100, 100, 100, 136);
    toraLine2.setStroke(Color.WHITE);
    toraLine2.setTranslateY(-80);
    toraLine2.setTranslateX((newtora * scalingFactor) / 2 + (-runway.getWidth() + newtora * scalingFactor) / 2 + toraPush * scalingFactor);

    ldaLine1 = new Line(100, 100, 100, 105);
    ldaLine1.setStroke(Color.WHITE);
    ldaLine1.setTranslateY(-65);
    ldaLine1.setTranslateX(-runway.getWidth() / 2 + (runway.getWidth() - LDAarrow.getWidth()) - 10);

    ldaLine2 = new Line(100, 100, 100, 105);
    ldaLine2.setStroke(Color.WHITE);
    ldaLine2.setTranslateY(-65);
    ldaLine2.setTranslateX((newtora * scalingFactor) / 2 + (-runway.getWidth() + newtora * scalingFactor) / 2 + toraPush * scalingFactor);

    this.getChildren().addAll(todaLine1, todaLine2, asdaLine1, asdaLine2, toraLine1, toraLine2, ldaLine1, ldaLine2);


    Rectangle DisplacementThresholdLine = new Rectangle(10, runway.getHeight());
    DTLVBox = new VBox(DisplacementThresholdLine);
    DisplacementThresholdLine.setFill(Color.YELLOW);
    DTLVBox.setTranslateY(200 - runway.getHeight() / 2);
    DTLVBox.setTranslateX(newDisplacementThreshold * scalingFactor + 100);

    DisplacementThresholdText = new Label("Displacement Threshold");
    DisplacementThresholdText.setRotate(90);
    DisplacementThresholdText.setFont(Font.font("Arial", FontWeight.BLACK, 10));
    DisplacementThresholdText.setTranslateX(-runway.getWidth() / 2 + (newDisplacementThreshold * scalingFactor) + 5);

    RunwayDirectionNumber = new Label(extractNumbers(extractLastThreeLetters(runwayName)));
    RunwayDirectionNumber.setTextFill(Color.WHITE);
    RunwayDirectionNumber.setRotate(90);
    RunwayDirectionNumber.setFont(Font.font("Arial", FontWeight.BLACK, 30));
    RunwayDirectionNumber.setTranslateX(150 + newDisplacementThreshold*scalingFactor - runway.getWidth()/2);

    RunwayDirectionLetter = new Label(extractLetters(extractLastThreeLetters(runwayName)));
    RunwayDirectionLetter.setTextFill(Color.WHITE);
    RunwayDirectionLetter.setRotate(90);
    RunwayDirectionLetter.setFont(Font.font("Arial", FontWeight.BLACK, 30));
    RunwayDirectionLetter.setTranslateX(120 + newDisplacementThreshold*scalingFactor - runway.getWidth()/2);


    directionArrow = new Arrow(0, 20, 100, 20, 12);

    RESARect = new Rectangle(runway.getWidth() - LDAarrow.getWidth() - obstacleWidth - 10 - lThreshold, runway.getHeight());
    RESARect.setFill(Color.GREY);
    RESARect.setTranslateX(-runway.getWidth() / 2 + RESARect.getWidth() / 2 + obstacleWidth + lThreshold);
    RESARect.setOpacity(0.7);
    this.getChildren().add(RESARect);

    RESALabel = new Label("RESA");
    RESALabel.setFont(Font.font("Arial", FontWeight.BLACK, 25));
    RESALabel.setTextFill(Color.WHITE);
    RESALabel.setTranslateX(-runway.getWidth() / 2 + obstacleWidth + RESARect.getWidth() / 2 + lThreshold);
    RESALabel.setTranslateY(runway.getHeight() / 2 + 10);
    RESALabel.setTextFill(Color.WHITE);
    RESALabel.toFront();
    this.getChildren().add(RESALabel);

    this.getChildren().addAll(DTLVBox, DisplacementThresholdText, RunwayDirectionNumber, RunwayDirectionLetter);

// Assuming runwayName is a string like "09R/27L"
    if (runwayName.endsWith("R)") && !isRotated) {
      flipRunway();
      isRotated = true;
    } else if (runwayName.endsWith("L)") && isRotated) {
      flipRunway();
      isRotated = false;
    }

    setLabels();
    this.RESALabel.setScaleX(this.getScaleX());
    obstacleVBox.toFront();
    RESARect.toFront();
    

  }

  public boolean getIsRotated() {
    return isRotated;
  }

  public void setLabels() {
    clearwayLabel.setScaleX(this.getScaleX());
    TORALabel.setScaleX(this.getScaleX());
    TODALabel.setScaleX(this.getScaleX());
    ASDALabel.setScaleX(this.getScaleX());
    LDALabel.setScaleX(this.getScaleX());
    RunwayDirectionNumber.setScaleX(this.getScaleX());
    RunwayDirectionLetter.setScaleX(this.getScaleX());
    stopwayLabel.setScaleX(this.getScaleX());
    DisplacementThresholdText.setScaleX(this.getScaleX());
    directionLabel.setScaleX(this.getScaleX());
    if (this.getChildren().contains(RESALabel)) {
      RESALabel.setScaleX(this.getScaleX());
    }
  }


  public double calculateRunwayRotation() {
    if (runwayName == null || runwayName.length() < 2) {
      throw new IllegalArgumentException("Invalid runway designation");
    }

    // Extract the first two characters and parse them as integer
    String numericPart = runwayName.substring(0, 2);
    int runwayNumber = Integer.parseInt(numericPart);

    // Each runway number corresponds to a compass heading multiplied by 10
    double headingDegrees = runwayNumber * 10;

    return headingDegrees;
  }

    public static String extractLastThreeLetters(String runwayName) {
        if (runwayName != null) {
            int startIndex = runwayName.indexOf('(');
            int endIndex = runwayName.indexOf(')');

            if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
                System.out.println(runwayName.substring(startIndex + 1, endIndex));
                return runwayName.substring(startIndex + 1, endIndex);
            }
        }
        return ""; 
    }

    public static String extractNumbers(String input) {
        if (input == null || input.length() < 2) {
            return ""; // Return empty if the input is null or less than 2 characters
        }

        // Extract and return the first two characters of the string
        return input.substring(0, 2);
    }

    public static String extractLetters(String input) {
        if (input == null) {
            return ""; // Return empty if the input is null
        }

        StringBuilder filteredOutput = new StringBuilder();
        for (char c : input.toCharArray()) {
            // Check if the character is one of the specific letters used in runway naming
            if (c == 'L' || c == 'R' || c == 'C') {
                filteredOutput.append(c);
            }
        }
        return filteredOutput.toString();
    }

  public double getLabelScale() {
    return directionLabel.getScaleX();
  }

  public void setLabelsToHalfYScale() {
    clearwayLabel.setScaleY(0.5);
    TORALabel.setScaleY(0.5);
    TODALabel.setScaleY(0.5);
    ASDALabel.setScaleY(0.5);
    LDALabel.setScaleY(0.5);
    RunwayDirectionNumber.setScaleY(0.5);
    RunwayDirectionLetter.setScaleY(0.5);
    stopwayLabel.setScaleY(0.5);
    DisplacementThresholdText.setScaleY(0.5);
    directionLabel.setScaleY(0.5);
    if (this.getChildren().contains(RESALabel)) {
      RESALabel.setScaleY(0.5);
    }
  }

  public void resetToInitialState() {
    logger.info("Resetting runway view to initial state");

    // Clear all existing children that are added dynamically during updates
    this.getChildren().clear();

    // Rebuild the runway to its default setup without any specific runway data
    build();

    // Optionally, you can reset any specific properties or fields
    this.runwayName = null;
    this.displacedThreshold = 0;
    this.oldtoda = 0;
    this.oldasda = 0;
    this.oldtora = 0;
    this.oldlda = 0;
    this.oldstopway = 0;
    this.oldclearway = 0;
    this.flip = 1;
    this.isRotated = false;
    this.isLeft = true;
    this.isTakeOffAway = true;
    this.lDisplacement = 0;

    // Reset any other necessary components or visual elements
    // For example, resetting background colors, removing arrows, labels, etc.
  }


  public void setLabelsToNormalY() {
    clearwayLabel.setScaleY(1);
    TORALabel.setScaleY(1);
    TODALabel.setScaleY(1);
    ASDALabel.setScaleY(1);
    LDALabel.setScaleY(1);
    RunwayDirectionNumber.setScaleY(1);
    RunwayDirectionLetter.setScaleY(1);
    stopwayLabel.setScaleY(1);
    DisplacementThresholdText.setScaleY(1);
    directionLabel.setScaleY(1);
    if (this.getChildren().contains(RESALabel)) {
      RESALabel.setScaleY(1);
    }
  }
}
