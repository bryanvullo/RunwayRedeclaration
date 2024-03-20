package uk.ac.soton.comp2211.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import uk.ac.soton.comp2211.model.obstacles.Obstacle;

public class Tool {
    private Calculation revisedCalculation;
    
    private Calculation previousCalculation;
    
    private Runway runway;
    
    //runway values
    public DoubleProperty clearway;
    public DoubleProperty stopway;
    public DoubleProperty displacedThreshold;
    
    //original values
    public DoubleProperty tora;
    public DoubleProperty toda;
    public DoubleProperty asda;
    public DoubleProperty lda;
    
    //revised values
    public DoubleProperty revisedTora;
    public DoubleProperty revisedToda;
    public DoubleProperty revisedAsda;
    public DoubleProperty revisedlLda;
    
    //previous values
    public DoubleProperty previousTora;
    public DoubleProperty previousToda;
    public DoubleProperty previousAsda;
    public DoubleProperty previousLda;
    
    public Tool() {
        clearway = new SimpleDoubleProperty(0.0);
        stopway = new SimpleDoubleProperty(0.0);
        displacedThreshold = new SimpleDoubleProperty(0.0);
        
        tora = new SimpleDoubleProperty(0.0);
        toda = new SimpleDoubleProperty(0.0);
        asda = new SimpleDoubleProperty(0.0);
        lda = new SimpleDoubleProperty(0.0);
        
        revisedTora = new SimpleDoubleProperty(0.0);
        revisedToda = new SimpleDoubleProperty(0.0);
        revisedAsda = new SimpleDoubleProperty(0.0);
        revisedlLda = new SimpleDoubleProperty(0.0);
        
        previousTora = new SimpleDoubleProperty(0.0);
        previousToda = new SimpleDoubleProperty(0.0);
        previousAsda = new SimpleDoubleProperty(0.0);
        previousLda = new SimpleDoubleProperty(0.0);
    }
    
    public void recalculate(Obstacle obstacle, String type) {
        previousCalculation = revisedCalculation;
        previousTora.set(revisedTora.get());
        previousToda.set(revisedToda.get());
        previousAsda.set(revisedAsda.get());
        previousLda.set(revisedlLda.get());
        
        revisedCalculation = new Calculation(runway, obstacle);
        if (type.equals("TOTLT")) {
            revisedCalculation.calculateTakeOffTowardsLandingTowards();
        } else {
            revisedCalculation.calculateTakeOffAwayLandingOver();
        }
        
        revisedTora.set(revisedCalculation.getTora());
        revisedToda.set(revisedCalculation.getToda());
        revisedAsda.set(revisedCalculation.getAsda());
        revisedlLda.set(revisedCalculation.getLda());
    }
    
    public void setRunway(Runway runway) {
        this.runway = runway;
        
        clearway.set(runway.getClearway());
        stopway.set(runway.getStopway());
        displacedThreshold.set(runway.getDisplacedThreshold());
        
        tora.set(runway.getTora());
        toda.set(runway.getToda());
        asda.set(runway.getAsda());
        lda.set(runway.getLda());
        
        revisedTora.set(0.0);
        revisedToda.set(0.0);
        revisedAsda.set(0.0);
        revisedlLda.set(0.0);
        
        previousTora.set(0.0);
        previousToda.set(0.0);
        previousAsda.set(0.0);
        previousLda.set(0.0);
    }
    
    public Runway getRunway() {
        return runway;
    }
}
