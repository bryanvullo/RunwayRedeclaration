package uk.ac.soton.comp2211.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RunwayViewBox extends VBox {
    
    private static final Logger logger = LogManager.getLogger(RunwayViewBox.class);
    
    public RunwayViewBox() {
        logger.info("Creating Runway View");
        build();
    }

    private void build() {
        logger.info("Building Runway View");
        
        setAlignment(Pos.CENTER);
        setSpacing(10);
        setPadding(new Insets(10));
        
        var viewSelectionBox = new HBox();
        viewSelectionBox.setAlignment(Pos.CENTER);
        viewSelectionBox.setSpacing(10);
        getChildren().add(viewSelectionBox);
        
        var topdownButton = new Button("Top-Down View");
        var simultaneousButton = new Button("Simultaneous View");
        var sideButton = new Button("Side View");
        
        viewSelectionBox.getChildren().addAll(topdownButton, simultaneousButton, sideButton);
        
        //TODO add Runway View here

        var runwayView = new HBox();
        runwayView.setAlignment(Pos.CENTER);
        runwayView.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        VBox.setVgrow(runwayView, Priority.ALWAYS);
        getChildren().add(runwayView);

        var topDownRunwayPane = new SideRunway();
        runwayView.getChildren().add(topDownRunwayPane);

        //End of TEMP code

        var runwayViewTools = new HBox();
        runwayViewTools.setAlignment(Pos.CENTER);
        runwayViewTools.setSpacing(10);
        getChildren().add(runwayViewTools);
        
        var zoomInButton = new Button("+");
        var zoomOutButton = new Button("-");
        var rotateButton = new Button("Rotate");
        var alignButton = new Button("Align to Compass");
        var panButton = new Button("Pan");
        var resetButton = new Button("Reset");
        
        runwayViewTools.getChildren().addAll(
            zoomInButton, zoomOutButton, rotateButton, alignButton, panButton, resetButton);
        
    }
    
}
