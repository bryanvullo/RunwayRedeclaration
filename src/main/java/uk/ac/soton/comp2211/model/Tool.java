package uk.ac.soton.comp2211.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import uk.ac.soton.comp2211.model.Obstacles.Obstacle;

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
        //TODO implement function to be able to change the runway
        runway = new Runway("Heathrow 09L", 3902, 3902, 3902, 3595);
        runway.setDisplacedThreshold(306);
        
        tora = new SimpleIntegerProperty(runway.getTora());
        toda = new SimpleIntegerProperty(runway.getToda());
        asda = new SimpleIntegerProperty(runway.getAsda());
        lda = new SimpleIntegerProperty(runway.getLda());
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
}
