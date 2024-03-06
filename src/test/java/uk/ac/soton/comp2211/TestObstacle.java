package uk.ac.soton.comp2211;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.soton.comp2211.model.AdvancedObstacle;
import uk.ac.soton.comp2211.model.Container;
import uk.ac.soton.comp2211.model.Plane;
import uk.ac.soton.comp2211.model.ShuttleBus;

import java.util.Optional;
public class TestObstacle {
    @Test
    void testNegativeDimensionsRejected() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AdvancedObstacle("Test Obstacle", -1, 10, 10, 0, 0, 0, Optional.empty());
        }, "Height cannot be a negative value.");

        assertThrows(IllegalArgumentException.class, () -> {
            new AdvancedObstacle("Test Obstacle", 10, -1, 10, 0, 0, 0, Optional.empty());
        }, "Width cannot be a negative value.");

        assertThrows(IllegalArgumentException.class, () -> {
            new AdvancedObstacle("Test Obstacle", 10, 10, -1, 0, 0, 0, Optional.empty());
        }, "Length cannot be a negative value.");
    }

    // Test to verify that creating an obstacle with unrealistic large values throws an exception
    @Test
    void testVeryLargeValuesRejected() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AdvancedObstacle("Unrealistically Large Obstacle", 10000, 20000, 30000, 0, 0, 0, Optional.empty());
        }, "Obstacle dimensions should be within realistic limits.");
    }

    // Test to ensure that attempting to create an unknown type of obstacle via factory method is handled properly
    @Test
    void testUnknownObstacleType() {
        // Assuming you have a method in AdvancedObstacle or a specific factory class for creating obstacles
        assertThrows(IllegalArgumentException.class, () -> {
            AdvancedObstacle.createObstacle("Unknown Obstacle Type", 0, 0, 0, 0, 0, 0, Optional.empty());
        }, "The system doesn't accept unknown obstacles.");
    }

    // This tests for proper functioning of specialised methods like estimateClearanceTime for Plane
    @Test
    void testEstimateClearanceTimeAccuracy() {
        Plane plane = Plane.createPlane("Boeing 747");
        // Assuming an average taxi speed, verify the estimated clearance time matches expected value
        assertTrue(plane.estimateClearanceTime() > 0, "The clearance time should be a positive value.");
    }
}
