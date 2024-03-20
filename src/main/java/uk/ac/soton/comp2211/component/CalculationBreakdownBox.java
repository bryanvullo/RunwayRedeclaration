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

public class CalculationBreakdownBox extends VBox {
    
    private static final Logger logger = LogManager.getLogger(CalculationBreakdownBox.class);
    
    private StringProperty toraBreakdown;
    private StringProperty todaBreakdown;
    private StringProperty asdaBreakdown;
    private StringProperty ldaBreakdown;
    
    public CalculationBreakdownBox() {
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
    
    public void reset(Calculation calculation) {
        logger.info("Resetting Calculation Breakdown Box");
        
        toraBreakdown.set("Not calculated");
        todaBreakdown.set("Not calculated");
        asdaBreakdown.set("Not calculated");
        ldaBreakdown.set("Not calculated");
    }
    
    public String getToraBreakdown() {
        return toraBreakdown.get();
    }
    
    public StringProperty toraBreakdownProperty() {
        return toraBreakdown;
    }
    
    public String getTodaBreakdown() {
        return todaBreakdown.get();
    }
    
    public StringProperty todaBreakdownProperty() {
        return todaBreakdown;
    }
    
    public String getAsdaBreakdown() {
        return asdaBreakdown.get();
    }
    
    public StringProperty asdaBreakdownProperty() {
        return asdaBreakdown;
    }
    
    public String getLdaBreakdown() {
        return ldaBreakdown.get();
    }
    
    public StringProperty ldaBreakdownProperty() {
        return ldaBreakdown;
    }
}
