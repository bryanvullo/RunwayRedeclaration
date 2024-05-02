package uk.ac.soton.comp2211.component;

import javafx.scene.shape.Polygon;

public class Hexagon extends Polygon {

    public Hexagon(double centerX, double centerY, double radius) {
        super();
        createHexagon(centerX, centerY, radius);
    }

    private void createHexagon(double centerX, double centerY, double radius) {
        this.getPoints().clear();

        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI / 6 * (i + 0.5);
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            this.getPoints().addAll(x, y);
        }
    }
}
