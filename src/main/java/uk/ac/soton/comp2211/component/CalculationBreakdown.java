package uk.ac.soton.comp2211.component;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
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
        
        toraBreakdown = new SimpleStringProperty("Not calculated");
        todaBreakdown = new SimpleStringProperty("Not calculated");
        asdaBreakdown = new SimpleStringProperty("Not calculated");
        ldaBreakdown = new SimpleStringProperty("Not calculated");
        
        build();
    }
    
    private void build() {
        logger.info("Building Calculation Breakdown");
        setAlignment(Pos.TOP_CENTER);
        setSpacing(10);
        setPadding(new Insets(20));
        
        var title = new Text("Calculation Breakdown");
        title.getStyleClass().add("componentTitle");
        getChildren().add(title);
        
        var toraBox = new HBox();
        var toraLabel = new Text("TORA: ");
        toraLabel.getStyleClass().add("subtitle");
        var toraText = new Text();
        toraText.textProperty().bind(toraBreakdown);
        toraBox.getChildren().addAll(toraLabel, toraText);
        
        var todaBox = new HBox();
        var todaLabel = new Text("TODA: ");
        todaLabel.getStyleClass().add("subtitle");
        var todaText = new Text();
        todaText.textProperty().bind(todaBreakdown);
        todaBox.getChildren().addAll(todaLabel, todaText);
        
        var asdaBox = new HBox();
        var asdaLabel = new Text("ASDA: ");
        asdaLabel.getStyleClass().add("subtitle");
        var asdaText = new Text();
        asdaText.textProperty().bind(asdaBreakdown);
        asdaBox.getChildren().addAll(asdaLabel, asdaText);
        
        var ldaBox = new HBox();
        var ldaLabel = new Text("LDA: ");
        ldaLabel.getStyleClass().add("subtitle");
        var ldaText = new Text();
        ldaText.textProperty().bind(ldaBreakdown);
        ldaBox.getChildren().addAll(ldaLabel, ldaText);
        
        getChildren().addAll(toraBox, todaBox, asdaBox, ldaBox);
    }
    
    public void update(Calculation calculation) {
        logger.info("Updating Calculation Breakdown Box");
        
        //TODO: update the text properties with the new values
    }
}
