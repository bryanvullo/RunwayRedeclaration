package uk.ac.soton.comp2211.dataStructure;

public class CustomObstacleLocation {
    
    private String name;
    private String height;
    private String width;
    private String length;
    private String distanceFromLeftThreshold;
    private String distanceFromRightThreshold;
    private String distanceFromCentre;
    
    public CustomObstacleLocation(String name, String height, String width, String length, String distanceFromLeftThreshold,
        String distanceFromRightThreshold, String distanceFromCentre) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.length = length;
        this.distanceFromLeftThreshold = distanceFromLeftThreshold;
        this.distanceFromRightThreshold = distanceFromRightThreshold;
        this.distanceFromCentre = distanceFromCentre;
    }
    
    public String getName() {
        return name;
    }
    
    public String getHeight() {
        return height;
    }
    
    public String getWidth() {
        return width;
    }
    
    public String getLength() {
        return length;
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
