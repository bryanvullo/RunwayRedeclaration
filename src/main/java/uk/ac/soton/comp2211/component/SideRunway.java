package uk.ac.soton.comp2211.component;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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

        StackPane topDownRunwayPane = new StackPane();
        Rectangle runway = new Rectangle(0, 0, 600, 10);
        runway.setFill(Color.GRAY);
        topDownRunwayPane.getChildren().addAll(runway);
        runwayView.getChildren().add(topDownRunwayPane);
    }

}
