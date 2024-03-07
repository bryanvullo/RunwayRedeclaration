package uk.ac.soton.comp2211.model;

import java.util.Optional;
import java.util.Properties;

/**
 * Represents a specific type of AdvancedObstacle for multiple predefined aircraft models.
 */
public class Plane extends AdvancedObstacle {
    /**
     * This private constructor prevents direct instantiation, using a factory method instead.
     */
    private Plane(String obstacleName, int height, int width, int length, int distanceRightThreshold, int distanceLeftThreshold, int distanceFromCentre, Optional<Properties> obstacleProperties) {
        super(obstacleName, height, width, length, distanceRightThreshold, distanceLeftThreshold, distanceFromCentre, obstacleProperties);
    }
    /**
     * This is a factory method for creating Plane instances based on the model type.
     * @param model The model name of the plane.
     * @return A Plane instance with predefined dimensions for the specified model.
     */
    public static Plane createPlane(String model) {
        switch (model) {
            case "Boeing 747":
                return new Plane("Boeing 747", 19, 64, 71, 0, 0, 0, Optional.empty());
            case "Airbus A380":
                return new Plane("Airbus A380", 24, 80, 73, 0, 0, 0, Optional.empty());
            default:
                throw new IllegalArgumentException("Unknown plane model: " + model);
        }
    }
    /**
     * This method estimates the time required for the plane to clear the runway based on typical taxi speed.
     * This method assumes a standard taxi speed and calculates the time needed to vacate the runway.
     * This is because a plane is a moving obstacle and its clearance time is important for runway scheduling; this is functionality that could help in planning runway usage or in simulations involving aircraft movements.
     * @return Estimated time in minutes for the plane to clear the runway.
     */
    public double estimateClearanceTime() {
        final double taxiSpeedInMetersPerMinute = 100.0;
        int clearanceDistance = getLength();
        return clearanceDistance / taxiSpeedInMetersPerMinute;
    }
}
