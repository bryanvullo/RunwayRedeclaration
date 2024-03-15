package uk.ac.soton.comp2211.model.obstacles;

import java.util.Optional;
import java.util.Properties;

/**
 * Represents a Container as a specific type of AdvancedObstacle - a predefined obstacle.
 * Containers, commonly found in airport cargo areas, can sometimes be obstacles on runways.
 * This class provides a predefined size for a standard shipping container.
 * The 'createContainer' factory method generates a container obstacle, assuming dimensions typical for a large shipping container.
 */

public class Container extends AdvancedObstacle {

    private Container(String obstacleName, Double height, Double width, Double length, Double distanceRightThreshold, Double distanceLeftThreshold, Double distanceFromCentre, Optional<Properties> obstacleProperties) {
        super(obstacleName, height, width, length, distanceRightThreshold, distanceLeftThreshold, distanceFromCentre, obstacleProperties);
    }

    public static Container createContainer() {
        return new Container("Cargo Container", 3.0, 2.0, 12.0, 0.0, 0.0, 0.0, Optional.empty());
    }
}
