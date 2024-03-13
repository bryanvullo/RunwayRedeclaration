package uk.ac.soton.comp2211.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import uk.ac.soton.comp2211.model.obstacles.Obstacle;

public class Tool {
    private Calculation revisedCalculation;
    
    private Calculation previousCalculation;
    
    private Runway runway;
    
    private Runway secondarRunway;
    
    public IntegerProperty tora;
    public IntegerProperty toda;
    public IntegerProperty asda;
    public IntegerProperty lda;
    
    public Tool() {
        tora = new SimpleIntegerProperty(0);
        toda = new SimpleIntegerProperty(0);
        asda = new SimpleIntegerProperty(0);
        lda = new SimpleIntegerProperty(0);
    }
    
    public void recalculate(Obstacle obstacle) {
        previousCalculation = revisedCalculation;
        
        revisedCalculation = new Calculation(runway, obstacle);
        revisedCalculation.calculateTakeOffAwayLandingOver();
        
        tora.set(revisedCalculation.getTora());
        toda.set(revisedCalculation.getToda());
        asda.set(revisedCalculation.getAsda());
        lda.set(revisedCalculation.getLda());
    }
    
    public void setRunway(Runway runway) {
        this.runway = runway;
        
        tora.set(runway.getTora());
        toda.set(runway.getToda());
        asda.set(runway.getAsda());
        lda.set(runway.getLda());
    }
}
