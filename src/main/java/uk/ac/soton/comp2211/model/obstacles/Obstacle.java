package uk.ac.soton.comp2211.model.obstacles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class to represents and models an Obstacle.
 */
public class Obstacle {
    
    private static final Logger logger = LogManager.getLogger(Obstacle.class);
    
    /**
     * The height of the obstacle
     */
    private Double height;
    
    /**
     * The distance from the threshold
     */
    private Double distanceFromThreshold;
    
    
    /**
     * Constructor for the Obstacle class
     * @param height the height of the obstacle
     * @param distanceFromThreshold the distance from the threshold
     */
    public Obstacle(Double height, Double distanceFromThreshold) {
        this.height = height;
        this.distanceFromThreshold = distanceFromThreshold;
    }
    
    /**
     * Constructor for the Obstacle class
     */
    public Obstacle() {
    }
    
    /**
     * Sets the height of the obstacle
     * @param height the height of the obstacle
     */
    public void setHeight(Double height) {
        this.height = height;
    }
    
    /**
     * Returns the height of the obstacle
     * @return the height of the obstacle
     */
    public Double getHeight() {
        return height;
    }
    
    /**
     * Returns the distance from the threshold
     * @return the distance from the threshold
     */
    public Double getDistanceFromThreshold() {
        return distanceFromThreshold;
    }
}
