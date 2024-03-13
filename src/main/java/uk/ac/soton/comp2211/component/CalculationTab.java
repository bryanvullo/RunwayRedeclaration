package uk.ac.soton.comp2211.component;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.model.Calculation;
import uk.ac.soton.comp2211.model.Runway;

public class CalculationTab extends VBox {
    
    private static final Logger logger = LogManager.getLogger(CalculationTab.class);
    
    private DoubleProperty orignalTora;
    private DoubleProperty originalToda;
    private DoubleProperty originalAsda;
    private DoubleProperty originalLda;
    
    private DoubleProperty recalculatedTora;
    private DoubleProperty recalculatedToda;
    private DoubleProperty recalculatedAsda;
    private DoubleProperty recalculatedLda;
    
    private Button recalculateButton;
    
    public CalculationTab() {
        logger.info("Creating Calculation Tab");
        
        orignalTora = new SimpleDoubleProperty(0.0);
        originalToda = new SimpleDoubleProperty(0.0);
        originalAsda = new SimpleDoubleProperty(0.0);
        originalLda = new SimpleDoubleProperty(0.0);
        recalculatedTora = new SimpleDoubleProperty(0.0);
        recalculatedToda = new SimpleDoubleProperty(0.0);
        recalculatedAsda = new SimpleDoubleProperty(0.0);
        recalculatedLda = new SimpleDoubleProperty(0.0);
        
        build();
    }
    
    private void build() {
        logger.info("Building Calculation Tab");
        setSpacing(10);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(20));
        setBorder(new Border((new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT))));
        
        var title = new Text("Calculation Tab");
        title.getStyleClass().add("componentTitle");
        getChildren().add(title);
        
        var box = new HBox();
        box.setAlignment(Pos.CENTER);
        getChildren().add(box);
        box.setSpacing(50);
        
        var grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(10,10,10,10));
        box.getChildren().add(grid);
//        HBox.setHgrow(grid, Priority.ALWAYS);
        
        var toraLabel = new Text("TORA");
        GridPane.setConstraints(toraLabel, 1, 0);
        var todaLabel = new Text("TODA");
        GridPane.setConstraints(todaLabel, 2, 0);
        var asdaLabel = new Text("ASDA");
        GridPane.setConstraints(asdaLabel, 3, 0);
        var ldaLabel = new Text("LDA");
        GridPane.setConstraints(ldaLabel, 4, 0);
        
        var originalLabel = new Text("Original");
        GridPane.setConstraints(originalLabel, 0, 1);
        var recalculatedLabel = new Text("Recalculated");
        GridPane.setConstraints(recalculatedLabel, 0, 2);
        
        var originalToraText = new Text();
        originalToraText.textProperty().bind(orignalTora.asString());
        GridPane.setConstraints(originalToraText, 1, 1);
        
        var originalTodaText = new Text();
        originalTodaText.textProperty().bind(originalToda.asString());
        GridPane.setConstraints(originalTodaText, 2, 1);
        
        var originalAsdaText = new Text();
        originalAsdaText.textProperty().bind(originalAsda.asString());
        GridPane.setConstraints(originalAsdaText, 3, 1);
        
        var originalLdaText = new Text();
        originalLdaText.textProperty().bind(originalLda.asString());
        GridPane.setConstraints(originalLdaText, 4, 1);
        
        var recalculatedToraText = new Text();
        recalculatedToraText.textProperty().bind(recalculatedTora.asString());
        GridPane.setConstraints(recalculatedToraText, 1, 2);
        
        var recalculatedTodaText = new Text();
        recalculatedTodaText.textProperty().bind(recalculatedToda.asString());
        GridPane.setConstraints(recalculatedTodaText, 2, 2);
        
        var recalculatedAsdaText = new Text();
        recalculatedAsdaText.textProperty().bind(recalculatedAsda.asString());
        GridPane.setConstraints(recalculatedAsdaText, 3, 2);
        
        var recalculatedLdaText = new Text();
        recalculatedLdaText.textProperty().bind(recalculatedLda.asString());
        GridPane.setConstraints(recalculatedLdaText, 4, 2);
        
        grid.getChildren().addAll(toraLabel, todaLabel, asdaLabel, ldaLabel, originalLabel, recalculatedLabel,
            originalToraText, originalTodaText, originalAsdaText, originalLdaText, recalculatedToraText, recalculatedTodaText,
            recalculatedAsdaText, recalculatedLdaText);
        
        recalculateButton = new Button("Recalculate");
        box.getChildren().add(recalculateButton);
    }
    
    public void updateRunway(Runway runway) {
        logger.info("Updating Calculation Tab with Runway");
        
        orignalTora.setValue(runway.getTora());
        originalToda.setValue(runway.getToda());
        originalAsda.setValue(runway.getAsda());
        originalLda.setValue(runway.getLda());
    }
    
    public void updateCalculation(Calculation calculation) {
        logger.info("Updating Calculation Tab with Calculation");
        
        recalculatedTora.setValue(calculation.getTora());
        recalculatedToda.setValue(calculation.getToda());
        recalculatedAsda.setValue(calculation.getAsda());
        recalculatedLda.setValue(calculation.getLda());
    }
    
    public Button recalculateButton() {
        return recalculateButton;
    }
    
}
