package uk.ac.soton.comp2211.component;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
        var toraLabel = new Text("TORA ");
        toraLabel.getStyleClass().add("subtitle");
        var toraText = new Text();
        toraText.getStyleClass().add("tinytext");
        toraText.textProperty().bind(toraBreakdown);
        toraBox.getChildren().addAll(toraLabel, toraText);
        
        var todaBox = new HBox();
        var todaLabel = new Text("TODA ");
        todaLabel.getStyleClass().add("subtitle");
        var todaText = new Text();
        todaText.getStyleClass().add("tinytext");
        todaText.textProperty().bind(todaBreakdown);
        todaBox.getChildren().addAll(todaLabel, todaText);
        
        var asdaBox = new HBox();
        var asdaLabel = new Text("ASDA ");
        asdaLabel.getStyleClass().add("subtitle");
        var asdaText = new Text();
        asdaText.getStyleClass().add("tinytext");
        asdaText.textProperty().bind(asdaBreakdown);
        asdaBox.getChildren().addAll(asdaLabel, asdaText);
        
        var ldaBox = new HBox();
        var ldaLabel = new Text("LDA ");
        ldaLabel.getStyleClass().add("subtitle");
        var ldaText = new Text();
        ldaText.getStyleClass().add("tinytext");
        ldaText.textProperty().bind(ldaBreakdown);
        ldaBox.getChildren().addAll(ldaLabel, ldaText);
        
        var breakdownBox = new VBox(toraBox, todaBox, asdaBox, ldaBox);
        
        var scroll = new ScrollPane();
        scroll.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
        scroll.setContent(breakdownBox);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPrefWidth(this.getPrefWidth());
        
        getChildren().addAll(scroll);
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
