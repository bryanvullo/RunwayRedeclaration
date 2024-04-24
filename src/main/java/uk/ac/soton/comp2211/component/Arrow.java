package uk.ac.soton.comp2211.component;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 *
 * @author kn
 */
public class Arrow extends Path{
    private static final double defaultArrowHeadSize = 20;

    public Arrow(double startX, double startY, double endX, double endY, double arrowHeadSize){
        super();
        strokeProperty().bind(fillProperty());
        setFill(Color.WHITE);
        setStrokeWidth(5);

        //Line
        getElements().add(new MoveTo(startX, startY));
        getElements().add(new LineTo(endX, endY));

    }

    public Arrow(double startX, double startY, double endX, double endY){
        this(startX, startY, endX, endY, defaultArrowHeadSize);
    }
}