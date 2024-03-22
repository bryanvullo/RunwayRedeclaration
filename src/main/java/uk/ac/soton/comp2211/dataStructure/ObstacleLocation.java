package uk.ac.soton.comp2211.dataStructure;

public class ObstacleLocation {

    private String distanceFromLeftThreshold;
    private String distanceFromRightThreshold;
    private String distanceFromCentre;
    
    public ObstacleLocation(String distanceFromLeftThreshold, String distanceFromRightThreshold,
        String distanceFromCentre) {
        this.distanceFromLeftThreshold = distanceFromLeftThreshold;
        this.distanceFromRightThreshold = distanceFromRightThreshold;
        this.distanceFromCentre = distanceFromCentre;
    }
    
    public String getDistanceFromLeftThreshold() {
        return distanceFromLeftThreshold;
    }
    
    public String getDistanceFromRightThreshold() {
        return distanceFromRightThreshold;
    }
    
    public String getDistanceFromCentre() {
        return distanceFromCentre;
    }
}
