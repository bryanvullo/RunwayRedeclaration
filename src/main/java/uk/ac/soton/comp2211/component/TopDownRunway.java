package uk.ac.soton.comp2211.component;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TopDownRunway extends HBox {
    private static final Logger logger = LogManager.getLogger(TopDownRunway.class);

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

        StackPane topDownRunwayPane = new StackPane();
        Rectangle runway = new Rectangle(0, 0, 600, 150);
        runway.setFill(Color.GRAY);
        Rectangle runwayInnerOutline = new Rectangle(0, 0, 590, 110);
        runwayInnerOutline.setStroke(Color.WHITE);
        runwayInnerOutline.setStrokeWidth(2);
        runwayInnerOutline.setFill(Color.GRAY);
        runwayInnerOutline.setX(runway.getX() - 40);
        topDownRunwayPane.getChildren().addAll(runway, runwayInnerOutline);

        var dashHBox = new HBox();
        double dashWidth = 40;
        double gapWidth = 10;
        double padding = 15;
        double currentX = padding;
        double centerY = runway.getY() + runway.getHeight() / 2;

        while (currentX < runway.getWidth()) {
            Line dashedLine = new Line(currentX, centerY, currentX + dashWidth, centerY);
            dashedLine.setStroke(Color.WHITE);
            dashedLine.setStrokeWidth(2); // Adjust the width as needed
            dashedLine.getStrokeDashArray().addAll(dashWidth, gapWidth);
            dashHBox.getChildren().add(dashedLine);
            currentX += dashWidth + gapWidth + padding;
        }
        dashHBox.setAlignment(Pos.CENTER);
        dashHBox.setSpacing(padding);
        topDownRunwayPane.getChildren().add(dashHBox);
        runwayView.getChildren().add(topDownRunwayPane);
    }
}
