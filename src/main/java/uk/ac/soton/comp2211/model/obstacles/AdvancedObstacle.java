package uk.ac.soton.comp2211.model.obstacles;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.model.Calculation;
import uk.ac.soton.comp2211.model.Runway;

import java.util.Optional;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;
import uk.ac.soton.comp2211.model.Calculation;
import uk.ac.soton.comp2211.model.Runway;

/**
 * This class represents and models obstacles in a more advanced manner, providing their properties and additional information.
 * Assume all values are in meters. This is the default unit for the system, however the units can be changed as per the UnitConverter class.
 */
public class AdvancedObstacle extends Obstacle {
    private String obstacleName;
    private Double width, length; //height and width of obstacle. Note: height is inherited from Obstacle
    private DoubleProperty distanceRightThreshold = new SimpleDoubleProperty(), distanceLeftThreshold = new SimpleDoubleProperty(), distanceFromCentre = new SimpleDoubleProperty(); //distance from the threshold
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
     */
    public AdvancedObstacle(String obstacleName, Double height, Double width, Double length, Double distanceRightThreshold,
        Double distanceLeftThreshold, Double distanceFromCentre) throws IllegalArgumentException {
        super(height, 0.0);
        this.obstacleName = obstacleName;
        this.width = length;
        this.length = length;
        this.distanceRightThreshold.setValue(distanceRightThreshold);
        this.distanceLeftThreshold.setValue(distanceLeftThreshold);
        this.distanceFromCentre.setValue(distanceFromCentre);
        
        //validate the distance values
        validateNotUnrealistic(height, "Height");
        validatePositive(height, "Height");
        validateNotUnrealistic(width, "Width");
        validatePositive(width, "Width");
        validateNotUnrealistic(length, "Length");
        validatePositive(length, "Length");
    }



    public AdvancedObstacle() {
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
        return String.format("Obstacle named %s: height %f meters, width %f meters, length %f meters, near thresholds and centre line.",
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
    public Double getWidth() {
        return width;
    }
    /**
     * Sets the width of the obstacle.
     * @param width the obstacle width.
     */
    public void setWidth(Double width) throws IllegalArgumentException {
        validatePositive(width, "Width");
        validateNotUnrealistic(width, "Width");
        this.width = width;
    }
    /**
     * Returns the length of the obstacle.
     * @return the length of the obstacle.
     */
    public Double getLength() {
        return length;
    }
    /**
     * Sets the length of the obstacle.
     * @param length the obstacle length
     */
    public void setLength(Double length) throws  IllegalArgumentException {
        validatePositive(length, "Length");
        validateNotUnrealistic(length, "Length");
        this.length = length;
    }
    
    @Override
    public void setHeight(Double height) throws IllegalArgumentException {
        validatePositive(height, "Height");
        validateNotUnrealistic(height, "Height");
        super.setHeight(height);
    }

    /**
     * Returns the distance from the right threshold.
     * @return the distance from the right threshold.
     */
    public double getDistanceRightThreshold() {
        return distanceRightThreshold.getValue();
    }
    /**
     * Sets the distance from the right threshold.
     * @param distanceRightThreshold the distance from the right threshold.
     */
    public void setDistanceRightThreshold(double distanceRightThreshold) {
        this.distanceRightThreshold.setValue(distanceRightThreshold);
    }

    /**
     * Returns the distance from the left threshold.
     * @return the distance from the left threshold.
     */
    public double getDistanceLeftThreshold() {
        return distanceLeftThreshold.getValue();
    }
    /**
     * Sets the distance from the left threshold.
     * @param distanceLeftThreshold the distance from the left threshold.
     */
    public void setDistanceLeftThreshold(double distanceLeftThreshold) {
        this.distanceLeftThreshold.setValue(distanceLeftThreshold);
    }

    /**
     * Returns the distance from the centre line.
     * @return the distance from the centre line.
     */
    public double getDistanceFromCentre() {
        return distanceFromCentre.getValue();
    }
    /**
     * Sets the distance from the centre line.
     * @param distanceFromCentre the distance from the centre line.
     */
    public void setDistanceFromCentre(double distanceFromCentre) {
        this.distanceFromCentre.setValue(distanceFromCentre);
    }

    public DoubleProperty distanceRightThresholdProperty() {
        return distanceRightThreshold;
    }
    public DoubleProperty distanceLeftThresholdProperty() {
        return distanceLeftThreshold;
    }
    public DoubleProperty distanceFromCentreProperty() {
      return distanceFromCentre;
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
    private static void validatePositive(Double value, String fieldName) throws IllegalArgumentException {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be a negative value.");
        }
    }
    // This utility method validates that a dimension value is not unrealistically large
    private static void validateNotUnrealistic(Double value, String fieldName) throws IllegalArgumentException{
        if (value > 99999) {
            throw new IllegalArgumentException(fieldName + " is unrealistically large. Please enter max. of 5 digits.");
        }
    }
    public static double parseNumericInput(String input, String fieldName) throws IllegalArgumentException {
        try {
            double value = Double.parseDouble(input);
            validateNotUnrealistic(value, fieldName);
            validatePositive(value, fieldName);
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a numeric value.");
        }
    }
}
