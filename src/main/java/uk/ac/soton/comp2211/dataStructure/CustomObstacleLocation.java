package uk.ac.soton.comp2211.dataStructure;

public class CustomObstacleLocation {
    
    private String name;
    private double height;
    private double width;
    private double length;
    private double distanceFromLeftThreshold;
    private double distanceFromRightThreshold;
    private double distanceFromCentre;
    
    public CustomObstacleLocation(String name, double height, double width, double length, double distanceFromLeftThreshold,
        double distanceFromRightThreshold, double distanceFromCentre) {
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
    
    public double getHeight() {
        return height;
    }
    
    public double getWidth() {
        return width;
    }
    
    public double getLength() {
        return length;
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
