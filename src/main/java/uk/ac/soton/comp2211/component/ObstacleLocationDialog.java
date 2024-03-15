package uk.ac.soton.comp2211.component;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import uk.ac.soton.comp2211.dataStructure.ObstacleLocation;

public class ObstacleLocationDialog extends Dialog<ObstacleLocation> {
    private TextField distanceFromLeftThreshold;
    private TextField distanceFromRightThreshold;
    private TextField distanceFromCentre;
    
    public ObstacleLocationDialog () {
        super();
        this.setTitle("Obstacle Location");
        this.setHeaderText("Enter the location of the obstacle");
        buildUI();
    }
    
    private void buildUI() {
        GridPane grid = new GridPane();
        
        var distanceFromLeftThresholdLabel = new Label("Distance from left threshold: ");
        var distanceFromRightThresholdLabel = new Label("Distance from right threshold: ");
        var distanceFromCentreLabel = new Label("Distance from centre: ");
        
        distanceFromLeftThreshold = new TextField();
        distanceFromRightThreshold = new TextField();
        distanceFromCentre = new TextField();
        
        grid.add(distanceFromLeftThresholdLabel, 0, 0);
        grid.add(distanceFromLeftThreshold, 1, 0);
        grid.add(distanceFromRightThresholdLabel, 0, 1);
        grid.add(distanceFromRightThreshold, 1, 1);
        grid.add(distanceFromCentreLabel, 0, 2);
        grid.add(distanceFromCentre, 1, 2);
        
        getDialogPane().setContent(grid);
        
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        var okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
        okButton.setOnAction(e -> {
            if (!validateDialog()) {
                e.consume();
            }
        });
        
    }
    
    private boolean validateDialog() {
        if (distanceFromLeftThreshold.getText().isEmpty() ||
            distanceFromRightThreshold.getText().isEmpty() ||
            distanceFromCentre.getText().isEmpty()) {
            return false;
        }
        return true;
    }
    
    public String getDistanceFromLeftThreshold() {
        return distanceFromLeftThreshold.getText();
    }
    
    public String getDistanceFromRightThreshold() {
        return distanceFromRightThreshold.getText();
    }
    
    public String getDistanceFromCentre() {
        return distanceFromCentre.getText();
    }
    
}
