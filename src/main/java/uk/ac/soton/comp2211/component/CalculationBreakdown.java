package uk.ac.soton.comp2211.component;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.model.Calculation;

public class CalculationBreakdown extends VBox {
    
    private static final Logger logger = LogManager.getLogger(CalculationBreakdown.class);
    
    private StringProperty toraBreakdown;
    private StringProperty todaBreakdown;
    private StringProperty asdaBreakdown;
    private StringProperty ldaBreakdown;
    
    public CalculationBreakdown() {
        logger.info("Creating Calculation Breakdown");
        
        toraBreakdown = new SimpleStringProperty();
        todaBreakdown = new SimpleStringProperty();
        asdaBreakdown = new SimpleStringProperty();
        ldaBreakdown = new SimpleStringProperty();
        
        toraBreakdown.setValue("Not calculated");
        todaBreakdown.setValue("Not calculated");
        asdaBreakdown.setValue("Not calculated");
        ldaBreakdown.setValue("Not calculated");
        
        build();
    }
    
    private void build() {
        logger.info("Building Calculation Breakdown");
        
        var title = new Text("Calculation Breakdown");
        getChildren().add(title);
        
        var toraBox = new VBox();
        var toraLabel = new Text("TORA: ");
        var toraText = new Text();
        toraText.textProperty().bind(toraBreakdown);
        toraBox.getChildren().addAll(toraLabel, toraText);
        
        var todaBox = new VBox();
        var todaLabel = new Text("TODA: ");
        var todaText = new Text();
        todaBox.getChildren().addAll(todaLabel, todaText);
        
        var asdaBox = new VBox();
        var asdaLabel = new Text("ASDA: ");
        var asdaText = new Text();
        asdaBox.getChildren().addAll(asdaLabel, asdaText);
        
        var ldaBox = new VBox();
        var ldaLabel = new Text("LDA: ");
        var ldaText = new Text();
        ldaBox.getChildren().addAll(ldaLabel, ldaText);
        
        getChildren().addAll(toraBox, todaBox, asdaBox, ldaBox);
    }
    
    public void update(Calculation calculation) {
        logger.info("Updating Calculation Breakdown Box");
        
        //TODO: update the text properties with the new values
    }
}
