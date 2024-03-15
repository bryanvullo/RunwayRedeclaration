package uk.ac.soton.comp2211;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.soton.comp2211.model.obstacles.AdvancedObstacle;
import uk.ac.soton.comp2211.model.obstacles.Plane;

import java.util.Optional;
public class TestObstacle {

    // Test to ensure that negative dimensions are rejected
    @Test
    void testNegativeDimensionsRejected() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AdvancedObstacle("Test Obstacle", -1.0, 10.0, 10.0, 0.0, 0.0, 0.0, Optional.empty());
        }, "Height cannot be a negative value.");

        assertThrows(IllegalArgumentException.class, () -> {
            new AdvancedObstacle("Test Obstacle", 10.0, -1.0, 10.0, 0.0, 0.0, 0.0, Optional.empty());
        }, "Width cannot be a negative value.");

        assertThrows(IllegalArgumentException.class, () -> {
            new AdvancedObstacle("Test Obstacle", 10.0, 10.0, -1.0, 0.0, 0.0, 0.0, Optional.empty());
        }, "Length cannot be a negative value.");
    }

    /**
     * Test to verify that creating an obstacle with unrealistic large values throws an exception
     * Testing for height, length, width, and then all 3 together
     */
    @Test
    void testUnrealisticDimensionsRejected() {
        assertThrows(IllegalArgumentException.class, () -> {
            // Attempt to create an AdvancedObstacle with unrealistic height, width and length values
            new AdvancedObstacle("Unrealistically Large Obstacle", 200000.0, 100001.0, 100002.0, 50.0, 50.0, 50.0, Optional.empty());
            }, "Obstacle dimensions should be within realistic limits.");

            // Test for height
            assertThrows(IllegalArgumentException.class, () -> {
                new AdvancedObstacle("Unrealistically Large Obstacle", 200001.0, 50.0, 50.0, 100.0, 0.0, 0.0, Optional.empty());
            }, "Height is unrealistically large and should not be accepted.");

            // Test for width
            assertThrows(IllegalArgumentException.class, () -> {
                new AdvancedObstacle("Unrealistically Large Obstacle", 20.0, 100001.0, 50.0, 20.0, 50.0, 0.0, Optional.empty());
            }, "Width is unrealistically large and should not be accepted.");

            // Test for length
            assertThrows(IllegalArgumentException.class, () -> {
                new AdvancedObstacle("Unrealistically Large Obstacle", 20.0, 50.0, 100002.0, 500.0, 0.0, 110.0, Optional.empty());
            }, "Length is unrealistically large and should not be accepted.");
    }

    // This tests for proper functioning of specialised methods like estimateClearanceTime for Plane
    @Test
    void testEstimateClearanceTimeAccuracy() {
        Plane plane = Plane.createPlane("Boeing 747");
        assertTrue(plane.estimateClearanceTime() > 0, "The clearance time should be a positive value.");
    }
}
