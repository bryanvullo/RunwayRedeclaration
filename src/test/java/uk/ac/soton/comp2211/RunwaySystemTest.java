package uk.ac.soton.comp2211;

import uk.ac.soton.comp2211.model.UnitConverter;

public class RunwaySystemTest {
    public static void main(String[] args) {
        testMetersToFeetConversion();
        testFeetToMetersConversion();
        testRecalculateLengthAfterObstacle();
    }

    public static void testMetersToFeetConversion() {
        double meters = 1000;
        double expectedFeet = 3280.84;
        double feet = UnitConverter.metersToFeet(meters);
        checkEquals(expectedFeet, feet, "Meters to feet conversion failed");
    }

    public static void testFeetToMetersConversion() {
        double feet = 3280.84;
        double expectedMeters = 1000;
        double meters = UnitConverter.feetToMeters(feet);
        checkEquals(expectedMeters, meters, "Feet to meters conversion failed");
    }

    public static void checkEquals(double expected, double actual, String message) {
        double delta = 0.1;
        if (Math.abs(expected - actual) > delta) {
            System.out.println(message + " Expected: " + expected + ", Actual: " + actual);
        } else {
            System.out.println("Test passed: " + message);
        }
    }
}