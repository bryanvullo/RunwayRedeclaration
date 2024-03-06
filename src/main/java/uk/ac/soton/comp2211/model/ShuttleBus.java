package uk.ac.soton.comp2211.model;

import java.util.Optional;
import java.util.Properties;

/**
 * This class represents a Shuttle Bus as a specific type of AdvancedObstacle -  a predefined obstacle.
 * This class defines a shuttle bus with predefined dimensions, suitable for airport scenarios where
 * shuttle buses might be present on or near runways. Factory method 'createShuttleBus' is used to instantiate a shuttle bus obstacle with these fixed dimensions.
 */

public class ShuttleBus extends AdvancedObstacle {

    private ShuttleBus(String obstacleName, int height, int width, int length, int distanceRightThreshold, int distanceLeftThreshold, int distanceFromCentre, Optional<Properties> obstacleProperties) {
        super(obstacleName, height, width, length, distanceRightThreshold, distanceLeftThreshold, distanceFromCentre, obstacleProperties);
    }

    public static ShuttleBus createShuttleBus() {
        return new ShuttleBus("Passenger Shuttle Bus", 3, 3, 14, 0, 0, 0, Optional.empty());
    }
}
