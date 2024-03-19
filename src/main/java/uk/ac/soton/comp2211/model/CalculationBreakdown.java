package uk.ac.soton.comp2211.model;

public class CalculationBreakdown {
    
    private String toraBreakdown;
    private String todaBreakdown;
    private String asdaBreakdown;
    private String ldaBreakdown;
    private String tocsBreakdown;
    private String alsBreakdown;
    private String blastProctectionBreakdown;
    
    
    public CalculationBreakdown(String calculationType, Calculation calculation) {
        var obstacle = calculation.getObstacle();
        var runway = calculation.getRunway();
        
        switch (calculationType) {
            case "TOALO": // Take-Off Away and Landing Over
                // Calculate the breakdown of the TORA, TODA, ASDA, LDA, ALS, and Blast Protection
                blastProctectionBreakdown = " = RESA + Strip End \n"
                    + " = 240 + 60 \n"
                    + " = 300";
                if (runway.getDisplacedThreshold() != null) {
                    toraBreakdown = " = Original TORA - Distance from the threshold - Displaced Threshold - Blast Protection \n"
                        + String.format(" = %f - %f - %f - 300 \n", runway.getTora(), obstacle.getDistanceFromThreshold(), runway.getDisplacedThreshold())
                        + " = " + calculation.getTora();
                } else {
                    toraBreakdown = " = Original TORA - Distance from the threshold - Blast Protection \n"
                        + String.format(" = %f - %f - 300 \n", runway.getTora(), obstacle.getDistanceFromThreshold())
                        + " = " + calculation.getTora();
                }
                if (runway.getClearway() != null) {
                    todaBreakdown = " = TORA + Clearway \n"
                        + String.format(" = %f + %f \n", calculation.getTora(), runway.getClearway())
                        + " = " + calculation.getToda();
                } else {
                    todaBreakdown = " = TORA \n = " + calculation.getTora();
                }
                if (runway.getStopway() != null) {
                    asdaBreakdown = " = TORA + Stopway \n"
                        + String.format(" = %f + %f \n", calculation.getTora(), runway.getStopway())
                        + " = " + calculation.getAsda();
                } else {
                    asdaBreakdown = " = TORA \n = " + calculation.getTora();
                }
                alsBreakdown = String.format(" = max(RESA, 50 x %f) \n", obstacle.getHeight())
                    + String.format(" = max(240, %f) \n", 50 * obstacle.getHeight())
                    + String.format(" = %f", Math.max(240, 50 * obstacle.getHeight()));
                ldaBreakdown = " = Original LDA - Distance from the threshold - Strip End - ALS \n"
                    + String.format(" = %f - %f - 60 - %f \n", runway.getLda(), obstacle.getDistanceFromThreshold(), Math.max(240, 50 * obstacle.getHeight()))
                    + " = " + calculation.getLda();
                break;
            case "TOTLT": // Take-Off Towards and Landing Towards
                // Calculate the breakdown of the TORA, TODA, ASDA, LDA, and TOCS
                tocsBreakdown = String.format(" = max(RESA, 50 x %f) \n", obstacle.getHeight())
                    + String.format(" = max(240, %f) \n", 50 * obstacle.getHeight())
                    + String.format(" = %f", Math.max(240, 50 * obstacle.getHeight()));
                
                if (runway.getDisplacedThreshold() != null) {
                    toraBreakdown = " = Distance from the threshold + Displaced Threshold - TOCS - Strip End \n"
                        + String.format(" = %f + %f - %f - 60 \n", obstacle.getDistanceFromThreshold(), runway.getDisplacedThreshold(), Math.max(240, 50 * obstacle.getHeight()))
                        + " = " + calculation.getTora();
                } else {
                    toraBreakdown = " = Distance from the threshold - TOCS - Strip End \n"
                        + String.format(" = %f - %f - 60 \n", obstacle.getDistanceFromThreshold(), Math.max(240, 50 * obstacle.getHeight()))
                        + " = " + calculation.getTora();
                }
                todaBreakdown = " = TORA \n = " + calculation.getTora();
                asdaBreakdown = " = TORA \n = " + calculation.getTora();
                ldaBreakdown = " = Distance from the threshold - RESA - Strip End \n"
                    + String.format(" = %f - 240 - 60 \n", obstacle.getDistanceFromThreshold())
                    + " = " + calculation.getLda();
                break;
        }
        
        
    }
    

}
