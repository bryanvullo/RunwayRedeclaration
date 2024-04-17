package uk.ac.soton.comp2211.component;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TopDownRunway extends HBox {
    private static final Logger logger = LogManager.getLogger(TopDownRunway.class);
    private Arrow TODAarrow;
    private Arrow ASDAarrow;
    private Arrow TORAarrow;
    private Arrow LDAarrow;
    private Rectangle runway;
    private VBox TODAVBox;
    private VBox ASDAVBox;
    private VBox TORAVBox;
    private VBox LDAVBox;
    private StackPane topDownRunwayPane;
    private VBox viewVBox;
    private Rectangle obstacle;

    private Rectangle runwayStopway;
    private Rectangle runwayStopway2;
    private VBox obstacleVBox;
    private HBox runwayWithStopway;
    private VBox arrowsVBox;

    public TopDownRunway() {
        logger.info("Creating Top Down Runway View");
        build();
    }
    private void build() {
        logger.info("Building Top Down Runway View");

        //TODO add Runway View here

        var runwayView = new HBox();
        runwayView.setAlignment(Pos.CENTER);
        runwayView.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        VBox.setVgrow(runwayView, Priority.ALWAYS);
        getChildren().add(runwayView);

        topDownRunwayPane = new StackPane();
        viewVBox = new VBox();
        runway = new Rectangle(0, 0, 600, 150);
        runway.setFill(Color.GRAY);
        topDownRunwayPane.setAlignment(Pos.CENTER_LEFT);
        topDownRunwayPane.getChildren().addAll(runway);

        Label TODALabel = new Label("TODA");
        Label ASDALabel = new Label("ASDA");
        Label TORALabel = new Label("TORA");
        Label LDALabel = new Label("LDA");
        Label landingDirectionLabel = new Label("Landing Direction");

        TODAarrow = new Arrow(0, 20, runway.getWidth() + 100, 20);
        ASDAarrow = new Arrow(0, 20, runway.getWidth() + 50, 20);
        TORAarrow = new Arrow(0, 20, runway.getWidth() - 100, 20);
        LDAarrow = new Arrow(40, 20, runway.getWidth() - 120, 20);
        Arrow landingDirectionarrow = new Arrow(100, 20, 300, 20);

        TODAVBox = new VBox();
        TODAVBox.setSpacing(1);
        TODAVBox.setAlignment(Pos.CENTER_LEFT);
        TODAVBox.getChildren().addAll(TODALabel, TODAarrow);

        ASDAVBox = new VBox();
        ASDAVBox.setSpacing(5);
        ASDAVBox.setAlignment(Pos.CENTER_LEFT);
        ASDAVBox.getChildren().addAll(ASDALabel, ASDAarrow);

        TORAVBox = new VBox();
        TORAVBox.setSpacing(5);
        TORAVBox.setAlignment(Pos.CENTER_LEFT);
        TORAVBox.getChildren().addAll(TORALabel, TORAarrow);

        LDAVBox = new VBox();
        LDAVBox.setSpacing(5);
        LDAVBox.setAlignment(Pos.CENTER_LEFT);
        LDAVBox.getChildren().addAll(LDALabel, LDAarrow);

        HBox landingDirectionVBox = new HBox();
        landingDirectionVBox.setSpacing(10);
        landingDirectionVBox.setAlignment(Pos.CENTER_LEFT);
        landingDirectionVBox.getChildren().addAll(landingDirectionLabel, landingDirectionarrow);

        arrowsVBox = new VBox();
        arrowsVBox.setSpacing(10);
        arrowsVBox.setAlignment(Pos.CENTER_LEFT);

        var dashHBox = new HBox();
        double dashWidth = 40;
        double gapWidth = 10;
        double padding = 15;
        double currentX = padding + 20;
        double centerY = runway.getY() + runway.getHeight() / 2;

        while (currentX < runway.getWidth()) {
            Line dashedLine = new Line(currentX, centerY, currentX + dashWidth, centerY);
            dashedLine.setStroke(Color.WHITE);
            dashedLine.setStrokeWidth(2); // Adjust the width as needed
            dashedLine.getStrokeDashArray().addAll(dashWidth, gapWidth);
            dashHBox.getChildren().add(dashedLine);
            currentX += dashWidth + gapWidth + padding;
        }
        dashHBox.setAlignment(Pos.CENTER_LEFT);
        dashHBox.setSpacing(padding);
        dashHBox.setPadding(new Insets(0, 0, 0, 30));
        topDownRunwayPane.getChildren().add(dashHBox);

        runwayStopway = new Rectangle(0, 0, 50, 150);
        runwayStopway.setFill(Color.TRANSPARENT); // Set fill color to transparent
        runwayStopway.setStroke(Color.BLACK); // Set stroke color to black

        runwayStopway2 = new Rectangle(0, 0, 50, 150);
        runwayStopway2.setFill(Color.TRANSPARENT); // Set fill color to transparent
        runwayStopway2.setStroke(Color.BLACK); // Set stroke color to black

        runwayWithStopway = new HBox();
        runwayWithStopway.setAlignment(Pos.CENTER);
        runwayWithStopway.getChildren().addAll(runwayStopway, topDownRunwayPane, runwayStopway2);

        arrowsVBox.getChildren().addAll(TODAVBox, ASDAVBox, TORAVBox, LDAVBox);
        arrowsVBox.setPadding(new Insets(0, 0, 0, runwayStopway.getWidth()));

        viewVBox.getChildren().addAll(arrowsVBox,runwayWithStopway, landingDirectionVBox);
        viewVBox.setAlignment(Pos.CENTER_LEFT);
        viewVBox.setSpacing(10);
        viewVBox.setPadding(new Insets(0, 0, 0, 30));
        runwayView.getChildren().add(viewVBox);
    }

    public void updateRunway(double toda, double tora, double asda, double lda, double clearway, double stopway, double displacedThreshold) {
        TODAVBox.getChildren().removeAll(TODAarrow);
        ASDAVBox.getChildren().remove(ASDAarrow);
        TORAVBox.getChildren().remove(TORAarrow);
        LDAVBox.getChildren().remove(LDAarrow);
        arrowsVBox.getChildren().removeAll(TODAVBox, TORAVBox, ASDAVBox, LDAVBox);
        runwayWithStopway.getChildren().removeAll(runwayStopway, runwayStopway2);

        System.out.println(stopway);
        double scalingFactor = runway.getWidth()/tora;

        TODAarrow = new Arrow(0, 20, toda*scalingFactor, 20);
        ASDAarrow = new Arrow(0, 20, asda*scalingFactor, 20);
        TORAarrow = new Arrow(0, 20, tora*scalingFactor, 20);
        LDAarrow = new Arrow(100, 20, lda*scalingFactor, 20);


        TODAVBox.getChildren().add(TODAarrow);
        ASDAVBox.getChildren().add(ASDAarrow);
        TORAVBox.getChildren().add(TORAarrow);
        LDAVBox.getChildren().add(LDAarrow);
        LDAVBox.setPadding(new Insets(0, 0, 0, displacedThreshold*scalingFactor));
        arrowsVBox.getChildren().addAll(TODAVBox, TORAVBox, ASDAVBox, LDAVBox);

        runwayStopway = new Rectangle(0, 0, stopway*scalingFactor, 150);
        runwayStopway.setFill(Color.TRANSPARENT); // Set fill color to transparent
        runwayStopway.setStroke(Color.BLACK); // Set stroke color to black

        runwayStopway2 = new Rectangle(0, 0, stopway*scalingFactor, 150);
        runwayStopway2.setFill(Color.TRANSPARENT); // Set fill color to transparent
        runwayStopway2.setStroke(Color.BLACK); // Set stroke color to black

        runwayWithStopway.getChildren().addAll(runwayStopway, runwayStopway2);
        arrowsVBox.setPadding(new Insets(0, 0, 0, runwayStopway.getWidth()));

    }

    public void addObstacle(Double height, Double width, Double length, Double lThreshold, Double rThreshold, Double cThreshold) {
        System.out.println("the values inputted are: " + height + " " + width + " " + length);
        topDownRunwayPane.getChildren().remove(obstacleVBox);
        obstacle = new Rectangle(length * 1.5, width*1.5);
        obstacle.setFill(Color.RED);
        obstacleVBox = new VBox();
        obstacleVBox.getChildren().add(obstacle);
        obstacleVBox.setAlignment(Pos.CENTER_LEFT);
        obstacleVBox.setPadding(new Insets(-cThreshold,0,0,lThreshold));
        topDownRunwayPane.getChildren().add(obstacleVBox);
    }

    public Rectangle getRunway() {
        return runway;
    }

}