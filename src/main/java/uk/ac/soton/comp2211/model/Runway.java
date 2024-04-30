package uk.ac.soton.comp2211.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.model.obstacles.Obstacle;

import java.util.ArrayList;
import java.util.List;

/**
 * This class to represents and models a Runway.
 */
public class Runway {
    private static final Logger logger = LogManager.getLogger(Runway.class);
    
    /**
     * The name of the runway
     */
    private String name;
    
    /**
     * The clearway of the runway
     * An area beyond the end of the TORA, which may be used during an aircraft’s initial
     * climb to a specified height.
     */
    private Double clearway;
    
    
    /**
     * The stopway of the runway
     * An area beyond the end of the TORA, which may be used in the case of an abandoned
     * take-off so that an aircraft can be safely brought to a stop.
     */
    private Double stopway;
    
    /**
     * The displaced threshold of the runway
     */
    private Double displacedThreshold;
    
    /**
     * The obstacles on the runway
     */
    private List<Obstacle> obstacles;
    
    /**
     * The Take-Off Run Available (TORA) for this Runway
     */
    private Double tora;
    
    /**
     * The Take-Off Distance Available (TODA) for this Runway
     */
    private Double toda;
    
    /**
     * The Accelerate-Stop Distance Available (ASDA) for this Runway
     */
    private Double asda;
    
    /**
     * The Landing Distance Available (LDA) for this Runway
     */
    private Double lda;
    
    /**
     * Constructor for the Runway class
     * @param name the name of the runway
     */
    public Runway(String name, Double tora, Double toda, Double asda, Double lda) {
        this.name = name;
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
        
        this.clearway = null;
        this.stopway = null;
        this.displacedThreshold = null;
        this.obstacles = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns the name of the runway
     * @return the name of the runway
     */
    public String getRunwayName() {
        return name;
    }
    
    /**
     * Returns the clearway of the runway
     * @return the clearway of the runway
     */
    public Double getClearway() {
        return clearway;
    }
    
    /**
     * Sets the clearway of the runway
     * @param clearway the clearway of the runway
     */
    public void setClearway(Double clearway) {
        this.clearway = clearway;
    }
    
    /**
     * Returns the stopway of the runway
     * @return the stopway of the runway
     */
    public Double getStopway() {
        return stopway;
    }
    
    /**
     * Sets the stopway of the runway
     * @param stopway the stopway of the runway
     */
    public void setStopway(Double stopway) {
        this.stopway = stopway;
    }
    
    /**
     * Returns the displaced threshold of the runway
     * @return the displaced threshold of the runway
     */
    public Double getDisplacedThreshold() {
        return displacedThreshold;
    }
    
    /**
     * Sets the displaced threshold of the runway
     * @param displacedThreshold the displaced threshold of the runway
     */
    public void setDisplacedThreshold(Double displacedThreshold) {
        this.displacedThreshold = displacedThreshold;
    }
    
    /**
     * Returns the obstacles on the runway
     * @return the obstacles on the runway
     */
    public List<Obstacle> getObstacles() {
        return obstacles;
    }
    
    /**
     * Adds an obstacle to the runway
     * @param obstacle the obstacle to add
     */
    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }
    
    /**
     * Returns the Take-Off Run Available (TORA) for this Runway
     * @return the Take-Off Run Available (TORA) for this Runway
     */
    public Double getTora() {
        return tora;
    }
    
    /**
     * Sets the Take-Off Run Available (TORA) for this Runway
     * @param tora the Take-Off Run Available (TORA) for this Runway
     */
    public void setTora(Double tora) {
        this.tora = tora;
    }
    
    /**
     * Returns the Take-Off Distance Available (TODA) for this Runway
     * @return the Take-Off Distance Available (TODA) for this Runway
     */
    public Double getToda() {
        return toda;
    }
    
    /**
     * Sets the Take-Off Distance Available (TODA) for this Runway
     * @param toda the Take-Off Distance Available (TODA) for this Runway
     */
    public void setToda(Double toda) {
        this.toda = toda;
    }
    
    /**
     * Returns the Accelerate-Stop Distance Available (ASDA) for this Runway
     * @return the Accelerate-Stop Distance Available (ASDA) for this Runway
     */
    public Double getAsda() {
        return asda;
    }
    
    /**
     * Sets the Accelerate-Stop Distance Available (ASDA) for this Runway
     * @param asda the Accelerate-Stop Distance Available (ASDA) for this Runway
     */
    public void setAsda(Double asda) {
        this.asda = asda;
    }
    
    /**
     * Returns the Landing Distance Available (LDA) for this Runway
     * @return the Landing Distance Available (LDA) for this Runway
     */
    public Double getLda() {
        return lda;
    }
    
    /**
     * Sets the Landing Distance Available (LDA) for this Runway
     * @param lda the Landing Distance Available (LDA) for this Runway
     */
    public void setLda(Double lda) {
        this.lda = lda;
    }
}
