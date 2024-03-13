package uk.ac.soton.comp2211.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import uk.ac.soton.comp2211.model.obstacles.Obstacle;

public class Tool {
    private Calculation revisedCalculation;
    
    private Calculation previousCalculation;
    
    private Runway runway;
    
    private Runway secondarRunway;
    
    //original values
    public IntegerProperty tora;
    public IntegerProperty toda;
    public IntegerProperty asda;
    public IntegerProperty lda;
    
    //revised values
    public IntegerProperty revisedTora;
    public IntegerProperty revisedToda;
    public IntegerProperty revisedAsda;
    public IntegerProperty revisedlLda;
    
    //previous values
    public IntegerProperty previousTora;
    public IntegerProperty previousToda;
    public IntegerProperty previousAsda;
    public IntegerProperty previousLda;
    
    public Tool() {
        tora = new SimpleIntegerProperty(0);
        toda = new SimpleIntegerProperty(0);
        asda = new SimpleIntegerProperty(0);
        lda = new SimpleIntegerProperty(0);
        
        revisedTora = new SimpleIntegerProperty(0);
        revisedToda = new SimpleIntegerProperty(0);
        revisedAsda = new SimpleIntegerProperty(0);
        revisedlLda = new SimpleIntegerProperty(0);
        
        previousTora = new SimpleIntegerProperty(0);
        previousToda = new SimpleIntegerProperty(0);
        previousAsda = new SimpleIntegerProperty(0);
        previousLda = new SimpleIntegerProperty(0);
    }
    
    public void recalculate(Obstacle obstacle) {
        previousCalculation = revisedCalculation;
        previousTora.set(revisedTora.get());
        previousToda.set(revisedToda.get());
        previousAsda.set(revisedAsda.get());
        previousLda.set(revisedlLda.get());
        
        revisedCalculation = new Calculation(runway, obstacle);
        revisedCalculation.calculateTakeOffAwayLandingOver();
        
        revisedTora.set(revisedCalculation.getTora());
        revisedToda.set(revisedCalculation.getToda());
        revisedAsda.set(revisedCalculation.getAsda());
        revisedlLda.set(revisedCalculation.getLda());
    }
    
    public void setRunway(Runway runway) {
        this.runway = runway;
        
        tora.set(runway.getTora());
        toda.set(runway.getToda());
        asda.set(runway.getAsda());
        lda.set(runway.getLda());
        
        revisedTora.set(0);
        revisedToda.set(0);
        revisedAsda.set(0);
        revisedlLda.set(0);
        
        previousTora.set(0);
        previousToda.set(0);
        previousAsda.set(0);
        previousLda.set(0);
    }
    
    public Runway getRunway() {
        return runway;
    }
}
