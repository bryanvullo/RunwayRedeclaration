package uk.ac.soton.comp2211.component;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SideRunway extends HBox {
    private static final Logger logger = LogManager.getLogger(SideRunway.class);

    public SideRunway() {
        logger.info("Creating Side Runway View");
        build();
    }
    private void build() {
        logger.info("Building Side Runway View");

        //TODO add Runway View here

        var runwayView = new HBox();
        runwayView.setAlignment(Pos.CENTER);
        runwayView.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        VBox.setVgrow(runwayView, Priority.ALWAYS);
        getChildren().add(runwayView);

        StackPane SideRunwayPane = new StackPane();
        // Create a grey line stretching across the runwayView
        Line runway = new Line(0, runway.getHeight() / 2, runway.getWidth(), runway.getHeight() / 2);
        runway.setStroke(Color.GRAY);
        runway.setStrokeWidth(2); // Adjust the width as needed
        runway.setFill(Color.GRAY);
        SideRunwayPane.getChildren().addAll(runway);

        runwayView.getChildren().add(SideRunwayPane);
    }

}
