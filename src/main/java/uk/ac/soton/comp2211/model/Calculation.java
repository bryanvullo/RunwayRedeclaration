package uk.ac.soton.comp2211.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.model.obstacles.Obstacle;

/**
 * This is the Calculation Class. Where each calculation, including the figures used are stored.
 * Where methods to perform the calculations are defined.
 */
public class Calculation {
    
    private static final Logger logger = LogManager.getLogger(Calculation.class);
    
    /**
     * The runway for this calculation
     */
    private Runway runway;
    
    /**
     * The obstacle for this calculation
     */
    private Obstacle obstacle;
    
    /**
     * Take-Off Run Available
     * the length of the runway available for take‐off.
     * This is the total distance from the point where an aircraft can commence its take‐off
     * to the point where the runway can no longer support the weight of the aircraft
     * under normal conditions
     */
    private int tora;
    
    /**
     * Take-Off Distance Available
     * the length of the runway plus any Clearway (area beyond the runway that is considered
     * free from obstructions). This is the total distance an aircraft can safely utilise
     * for its take‐off and initial ascent.
     */
    private int toda;
    
    /**
     * Accelerate-Stop Distance Available
     * the length of the runway plus any Stopway (area that is not part of the TORA, but that can
     * be used to safely stop an aircraft in an emergency).
     * This is the total distance available to the aircraft in case of an aborted take‐off.
     */
    private int asda;
    
    /**
     * Landing Distance Available
     * the length of the runway available for landing. The start of
     * this is called the threshold and is typically the same as the start of the TORA.
     * A threshold may be displaced for operational reasons or because of a temporary obstacle,
     * in which case LDA and TORA can differ.
     */
    private int lda;
    
    /**
     * The Take-Off Climb Surface (TOCS)
     */
    private int tocs;
    
    /**
     * The Approach Landing Surface (ALS)
     */
    private int als;
    
    /**
     * The Runway End Safety Area (RESA)
     * An area symmetrical about the extended runway centreline and adjacent to the end of the
     * strip primarily intended to reduce the risk of damage to an aircraft
     * undershooting or overrunning the runway
     */
    private final int resaMin = 240;
    
    /**
     * The strip end
     */
    private final int stripEnd = 60;
    
    /**
     * Constructor for the Calculation class
     * @param runway the runway for this calculation
     * @param obstacle the obstacle for this calculation
     */
    public Calculation(Runway runway, Obstacle obstacle) {
        this.runway = runway;
        //the new obstacle we are calculating for
        this.obstacle = obstacle;
        
        //initialise the calculations
        tora = runway.getTora();
        toda = runway.getToda();
        asda = runway.getAsda();
        lda = runway.getLda();
    }
    
    /**
     * Method to get the Take-Off Runway Length
     * @return the Take-Off Runway Length
     */
    public int getTora() {
        return tora;
    }
    
    /**
     * Method to get the Take-Off Distance Available
     * @return the Take-Off Distance Available
     */
    public int getToda() {
        return toda;
    }
    
    /**
     * Method to get the Accelerate-Stop Distance Available
     * @return the Accelerate-Stop Distance Available
     */
    public int getAsda() {
        return asda;
    }
    
    /**
     * Method to get the Landing Distance Available
     * @return the Landing Distance Available
     */
    public int getLda() {
        return lda;
    }
    
    /**
     * Method to calculate the Take-Off Away and Landing Over
     * Calculates the TORA, TODA and ASDA for a take-off away from the obstacle
     * and the LDA for landing over the obstacle
     */
    public void calculateTakeOffAwayLandingOver() {
        logger.info("Calculating for Landing Over and Take-Off Away");
        
        calcLandingOver();
        calcTOAway();
    }
    
    /**
     * Method to calculate the Take-Off Towards and Landing Towards
     * Calculates the TORA, TODA and ASDA for a take-off towards the obstacle
     * and the LDA for landing towards the obstacle
     */
    public void calculateTakeOffTowardsLandingTowards() {
        logger.info("Calculating for Landing Towards and Take-Off Towards");
        
        calcLandingTowards();
        calcTOTowards();
    }
    
    /**
     * Method to calculate the Landing Towards
     * Calculates the LDA for a landing towards the obstacle
     */
    public void calcLandingTowards() {
        logger.info("Calculating LDA for Landing Towards");
        
        lda = obstacle.getDistanceFromThreshold() - resaMin - stripEnd;
    }
    
    /**
     * Method to calculate the Landing Over
     * Calculates the LDA for a landing over the obstacle
     */
    public void calcLandingOver() {
        logger.info("Calculating LDA for Landing Over");
        
        // approach landing surface
        // slope calculation 1 in 50 slope from the highest point of obstacle or 240m (RESA min)
        als = Math.max(resaMin, 50 * obstacle.getHeight());
        
        lda = lda - obstacle.getDistanceFromThreshold() - stripEnd - als;
    }
    
    /**
     * Method to calculate the Take-Off Towards
     * Calculates the TORA, TODA and ASDA for a take-off towards the obstacle
     */
    public void calcTOTowards() {
        logger.info("Calculating TORA, TODA and ASDA for Take-Off Towards");
        
        //take off climb surface
        // slope calculation 1 in 50 slope from the highest point of obstacle or 240m (RESA min)
        tocs = Math.max(resaMin, 50 * obstacle.getHeight());
        
        tora = obstacle.getDistanceFromThreshold() - tocs - stripEnd;
        tora = runway.getDisplacedThreshold() != null ? tora + runway.getDisplacedThreshold() : tora;
        toda = tora;
        asda = tora;
    }
    
    /**
     * Method to calculate the Take-Off Away
     * Calculates the TORA, TODA and ASDA for a take-off away from the obstacle
     */
    public void calcTOAway() {
        logger.info("Calculating TORA, TODA and ASDA for Take-Off Away");
        
        int blastProtection = resaMin + stripEnd;
        
        tora = tora - obstacle.getDistanceFromThreshold() - blastProtection;
        
        tora = runway.getDisplacedThreshold() != null ? tora - runway.getDisplacedThreshold() : tora;
        
        toda = runway.getClearway() != null ? tora + runway.getClearway() : tora;
        asda = runway.getStopway() != null ? tora + runway.getStopway() : tora;
    }
    
}
