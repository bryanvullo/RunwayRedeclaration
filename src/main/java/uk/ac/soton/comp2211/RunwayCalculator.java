package uk.ac.soton.comp2211;

public class RunwayCalculator {

    public static double recalculateLengthAfterObstacle(Runway runway, Obstacle obstacle, boolean useFeet) {

        double obstacleHeight = obstacle.heightInMeters;
        double lengthReduction = obstacleHeight * 10;
        double recalculatedLength = runway.lengthInMeters - lengthReduction;

        if (useFeet) {
            return UnitConverter.metersToFeet(recalculatedLength);
        } else {
            return recalculatedLength;
        }
    }
}