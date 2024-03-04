package uk.ac.soton.comp2211;

public class Main {
    public static void main(String[] args) {
        Runway runway = new Runway("Runway 1", 4000);
        Obstacle obstacle = new Obstacle("Obstacle 1", 5);

        double recalculatedLengthMeters = RunwayCalculator.recalculateLengthAfterObstacle(runway, obstacle, false);
        double recalculatedLengthFeet = RunwayCalculator.recalculateLengthAfterObstacle(runway, obstacle, true);

        System.out.println("Recalculated Runway Length: " + recalculatedLengthMeters + " meters");
        System.out.println("Recalculated Runway Length: " + recalculatedLengthFeet + " feet");
    }
}
