package uk.ac.soton.comp2211.component;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * Arrow component that extends Path for graphical representation of directional arrows.
 * @author kn
 */
public class Arrow extends Path {
    private static final double defaultArrowHeadSize = 12;
    private double startX;
    private double endX;

    /**
     * Constructs an Arrow object with specified start and end points and a custom arrow head size.
     * @param startX The starting x-coordinate of the arrow.
     * @param startY The starting y-coordinate of the arrow.
     * @param endX The ending x-coordinate of the arrow.
     * @param endY The ending y-coordinate of the arrow.
     * @param arrowHeadSize The size of the arrow head.
     */
    public Arrow(double startX, double startY, double endX, double endY, double arrowHeadSize) {
        super();
        this.startX = startX;
        this.endX = endX;
        strokeProperty().bind(fillProperty());
        setFill(Color.WHITE);
        setStrokeWidth(5);

        // Main line of the arrow
        getElements().add(new MoveTo(startX, startY));
        getElements().add(new LineTo(endX, endY));


    }

    /**
     * Constructs an Arrow object with specified start and end points using the default arrow head size.
     * @param startX The starting x-coordinate of the arrow.
     * @param startY The starting y-coordinate of the arrow.
     * @param endX The ending x-coordinate of the arrow.
     * @param endY The ending y-coordinate of the arrow.
     */


    /**
     * Returns the width of the arrow, which is the absolute difference between the start and end x-coordinates.
     * @return The horizontal width of the arrow.
     */
    public double getWidth() {
        return Math.abs(endX - startX);
    }

}
