package uk.ac.soton.comp2211.model.obstacles;

import java.util.Optional;
import java.util.Properties;

/**
 * This class represents a Shuttle Bus as a specific type of AdvancedObstacle -  a predefined obstacle.
 * This class defines a shuttle bus with predefined dimensions, suitable for airport scenarios where
 * shuttle buses might be present on or near runways. Factory method 'createShuttleBus' is used to instantiate a shuttle bus obstacle with these fixed dimensions.
 */

public class ShuttleBus extends AdvancedObstacle {

    private ShuttleBus(String obstacleName, Double height, Double width, Double length, Double distanceRightThreshold, Double distanceLeftThreshold, Double distanceFromCentre, Optional<Properties> obstacleProperties) {
        super(obstacleName, height, width, length, distanceRightThreshold, distanceLeftThreshold, distanceFromCentre, obstacleProperties);
    }

    public static ShuttleBus createShuttleBus() {
        return new ShuttleBus("Passenger Shuttle Bus", 3.0, 3.0, 14.0, 0.0, 0.0, 0.0, Optional.empty());
    }
}
