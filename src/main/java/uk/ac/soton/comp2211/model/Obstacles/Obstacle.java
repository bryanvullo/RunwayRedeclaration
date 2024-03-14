package uk.ac.soton.comp2211.model.Obstacles;

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
    private final int height;
    
    /**
     * The distance from the threshold
     */
    private int distanceFromThreshold;
    
    
    /**
     * Constructor for the Obstacle class
     * @param height the height of the obstacle
     * @param distanceFromThreshold the distance from the threshold
     */
    public Obstacle(int height, int distanceFromThreshold) {
        this.height = height;
        this.distanceFromThreshold = distanceFromThreshold;
    }
    
    /**
     * Returns the height of the obstacle
     * @return the height of the obstacle
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Returns the distance from the threshold
     * @return the distance from the threshold
     */
    public int getDistanceFromThreshold() {
        return distanceFromThreshold;
    }
}
