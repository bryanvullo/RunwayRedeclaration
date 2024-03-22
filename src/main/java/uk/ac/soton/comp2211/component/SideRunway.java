package uk.ac.soton.comp2211.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SideRunway extends HBox {
    private static final Logger logger = LogManager.getLogger(SideRunway.class);
    private StackPane SideRunwayPane;
    private Rectangle runway;
    private Rectangle obstacle;
    private VBox viewVBox;
    private VBox obstacleContainer;
    private VBox landingDirectionVBox;

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

        SideRunwayPane = new StackPane();
        obstacleContainer = new VBox();
        SideRunwayPane.getChildren().add(obstacleContainer);
        SideRunwayPane.setAlignment(Pos.CENTER_LEFT);
        runway = new Rectangle(0, 0, 730, 10);
        runway.setFill(Color.GRAY);
        viewVBox = new VBox();

        HBox LDAHBox = new HBox();

        landingDirectionVBox = new VBox();
        landingDirectionVBox.setSpacing(10);
        landingDirectionVBox.setAlignment(Pos.CENTER_LEFT);
        Arrow landingDirectionarrow = new Arrow(100, 20, 300, 20);
        Label landingDirectionLabel = new Label("Landing Direction");
        landingDirectionVBox.getChildren().addAll(landingDirectionLabel, landingDirectionarrow);
        landingDirectionVBox.setPadding(new Insets(0, 0, 100, 0));

        Arrow LDAarrow = new Arrow(100, 20, 500, 20);
        Label LDAarrowLabel = new Label("Landing Distance Available");
        VBox LDAVBox = new VBox();
        LDAVBox.getChildren().addAll(LDAarrow, LDAarrowLabel);
        LDAVBox.setAlignment(Pos.CENTER_RIGHT);

        SideRunwayPane.getChildren().addAll(runway);
        viewVBox.getChildren().addAll(landingDirectionVBox,SideRunwayPane, LDAVBox);
        viewVBox.setAlignment(Pos.CENTER_LEFT);
        viewVBox.setSpacing(10);

        runwayView.getChildren().add(viewVBox);
    }

    public void addObstacle(Double height, Double width, Double length) {
        SideRunwayPane.getChildren().remove(obstacleContainer);
        obstacle = new Rectangle(length * 1.5, height * 1.5);
        obstacle.setFill(Color.RED);
        obstacle.setY(runway.getY());
        obstacleContainer = new VBox();
        obstacleContainer.getChildren().add(obstacle);
        obstacleContainer.setPadding(new Insets(0, 0,   runway.getHeight() + obstacle.getHeight(), 0));
        SideRunwayPane.getChildren().add(obstacleContainer);
    }

}
