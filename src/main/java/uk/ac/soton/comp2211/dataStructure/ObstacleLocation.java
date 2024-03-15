package uk.ac.soton.comp2211.dataStructure;

public class ObstacleLocation {

    private double distanceFromLeftThreshold;
    private double distanceFromRightThreshold;
    private double distanceFromCentre;
    
    public ObstacleLocation(double distanceFromLeftThreshold, double distanceFromRightThreshold,
        double distanceFromCentre) {
        this.distanceFromLeftThreshold = distanceFromLeftThreshold;
        this.distanceFromRightThreshold = distanceFromRightThreshold;
        this.distanceFromCentre = distanceFromCentre;
    }
    
    public double getDistanceFromLeftThreshold() {
        return distanceFromLeftThreshold;
    }
    
    public double getDistanceFromRightThreshold() {
        return distanceFromRightThreshold;
    }
    
    public double getDistanceFromCentre() {
        return distanceFromCentre;
    }
}
