package uk.ac.soton.comp2211.model.Obstacles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.model.Calculation;
import uk.ac.soton.comp2211.model.Runway;

import java.util.Optional;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;

/**
 * This class represents and models obstacles in a more advanced manner, providing their properties and additional information.
 * Assume all values are in meters. This is the default unit for the system, however the units can be changed as per the UnitConverter class.
 */
public class AdvancedObstacle extends Obstacle {
    private String obstacleName;
    private int width, length; //height and width of obstacle. Note: height is inherited from Obstacle
    private int distanceRightThreshold, distanceLeftThreshold, distanceFromCentre; //distance from the threshold
    private Optional <Properties> obstacleProperties; //the properties of an obstacle outside dimensions that may be relevant
    private static final Logger logger = LogManager.getLogger(AdvancedObstacle.class);

    /**
     * Constructor for the AdvancedObstacle class
     * @param obstacleName the name of the obstacle
     * @param height the height of the obstacle
     * @param width the width of the obstacle
     * @param length the length of the obstacle
     * @param distanceRightThreshold the distance from the right threshold
     * @param distanceLeftThreshold the distance from the left threshold
     * @param distanceFromCentre the distance from the center-line
     * @param obstacleProperties the additional properties of the obstacle that may provide additional information
     */
    public AdvancedObstacle(String obstacleName, int height, int width, int length, int distanceRightThreshold, int distanceLeftThreshold, int distanceFromCentre, Optional<Properties> obstacleProperties) {
        super(validatePositive(validateNotUnrealistic(height, "Height"), "Height"), Math.min(distanceRightThreshold, distanceLeftThreshold)); //This is assuming the correct calculations for distance and correct values for obstacles are already correct and calculated correctly
        this.obstacleName = obstacleName;
        this.width = validatePositive(validateNotUnrealistic(width, "Width"), "Width");
        this.length = validatePositive(validateNotUnrealistic(length, "Length"), "Length");
        this.distanceRightThreshold = distanceRightThreshold;
        this.distanceLeftThreshold = distanceLeftThreshold;
        this.distanceFromCentre = distanceFromCentre;
        this.obstacleProperties = obstacleProperties;
    }

    /* This method calculates the impact of an obstacle on runway parameters (TORA, TODA, ASDA, LDA) based on its position and dimensions.
       This is particularly useful for flight planning and safety assessments.*/
    public Map<String, Double> calculateRunwayImpact(Runway runway) {
        Calculation calculation = new Calculation(runway, this);
        calculation.calculateTakeOffAwayLandingOver();
        calculation.calculateTakeOffTowardsLandingTowards();

        Map<String, Double> impact = new HashMap<>();
        impact.put("Adjusted TORA", (double) calculation.getTora());
        impact.put("Adjusted TODA", (double) calculation.getToda());
        impact.put("Adjusted ASDA", (double) calculation.getAsda());
        impact.put("Adjusted LDA", (double) calculation.getLda());

        return impact;
    }

    // This method generates a map containing the obstacle's name and its position relative to runway thresholds for visualisation purposes. This supports GUI representations of the obstacle on a runway.
    public Map<String, Object> generateVisualisationData() {
        Map<String, Object> visualisationData = new HashMap<>();
        visualisationData.put("Obstacle Name", getObstacleName());
        visualisationData.put("Position", Map.of("Right Threshold", getDistanceRightThreshold(),
                "Left Threshold", getDistanceLeftThreshold(),
                "Distance From Centre", getDistanceFromCentre()));
        return visualisationData;
    }

    /* This method exports the obstacle's key properties (name, height, width, length) as a Properties object.
       This Useful for data sharing, reporting, or external analysis; which are key requirements for the system.*/
    public Properties exportData() {
        Properties data = new Properties();
        data.setProperty("Name", getObstacleName());
        data.setProperty("Height", String.valueOf(getHeight()));
        data.setProperty("Width", String.valueOf(getWidth()));
        data.setProperty("Length", String.valueOf(getLength()));
        return data;
    }

    /* This method provides a textual description of the obstacle suitable for accessibility purposes, which enhances the system's usability with assistive technologies.
       Again, this is one of the key requirements*/
    public String getDescriptionForAccessibility() {
        return String.format("Obstacle named %s: height %d meters, width %d meters, length %d meters, near thresholds and centre line.",
                getObstacleName(), getHeight(), getWidth(), getLength());
    }

    /**
     * Returns the obstacle name
     * @return the obstacle name
     */
    public String toString() {
        return obstacleName;
    }
    /**
     * Returns the name of the obstacle.
     * @return the name of the obstacle.
     */
    public String getObstacleName() {
        return obstacleName;
    }
    /**
     * Sets the name of the obstacle.
     * @param obstacleName the name of the obstacle.
     */
    public void setObstacleName(String obstacleName) {
        this.obstacleName = obstacleName;
    }
    /**
     * Returns the width of the obstacle.
     * @return the obstacle width.
     */
    public int getWidth() {
        return width;
    }
    /**
     * Sets the width of the obstacle.
     * @param width the obstacle width.
     */
    public void setWidth(int width) {
        this.width = width;
    }
    /**
     * Returns the length of the obstacle.
     * @return the length of the obstacle.
     */
    public int getLength() {
        return length;
    }
    /**
     * Sets the length of the obstacle.
     * @param length the obstacle length
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Returns the distance from the right threshold.
     * @return the distance from the right threshold.
     */
    public int getDistanceRightThreshold() {
        return distanceRightThreshold;
    }
    /**
     * Sets the distance from the right threshold.
     * @param distanceRightThreshold the distance from the right threshold.
     */
    public void setDistanceRightThreshold(int distanceRightThreshold) {
        this.distanceRightThreshold = distanceRightThreshold;
    }

    /**
     * Returns the distance from the left threshold.
     * @return the distance from the left threshold.
     */
    public int getDistanceLeftThreshold() {
        return distanceLeftThreshold;
    }
    /**
     * Sets the distance from the left threshold.
     * @param distanceLeftThreshold the distance from the left threshold.
     */
    public void setDistanceLeftThreshold(int distanceLeftThreshold) {
        this.distanceLeftThreshold = distanceLeftThreshold;
    }

    /**
     * Returns the distance from the centre line.
     * @return the distance from the centre line.
     */
    public int getDistanceFromCentre() {
        return distanceFromCentre;
    }
    /**
     * Sets the distance from the centre line.
     * @param distanceFromCentre the distance from the centre line.
     */
    public void setDistanceFromCentre(int distanceFromCentre) {
        this.distanceFromCentre = distanceFromCentre;
    }

    /**
     * Returns the additional properties of the obstacle.
     * @return the additional properties of the obstacle.
     */
    public Optional<Properties> getObstacleProperties() {
        return obstacleProperties;
    }
    /**
     * Sets the additional properties of the obstacle.
     * @param obstacleProperties the additional properties of the obstacle.
     */
    public void setObstacleProperties(Optional<Properties> obstacleProperties) {
        this.obstacleProperties = obstacleProperties;
    }

    // This is a utility method for validating that a value is positive - for physical dimensions, i.e. Height, Length, and Width
    private static int validatePositive(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be a negative value.");
        }
        return value; //Return the value if it passes the check
    }
    // This utility method validates that a dimension value is not unrealistically large
    private static int validateNotUnrealistic(int value, String fieldName) {
        if (value > 100000) {
            throw new IllegalArgumentException(fieldName + " is unrealistically large and cannot be accepted.");
        }
        return value;
    }
}
