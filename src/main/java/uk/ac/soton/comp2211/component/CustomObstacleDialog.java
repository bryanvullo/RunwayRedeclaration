package uk.ac.soton.comp2211.component;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import uk.ac.soton.comp2211.dataStructure.CustomObstacleLocation;

public class CustomObstacleDialog extends Dialog<CustomObstacleLocation> {
    
    private TextField name;
    private TextField height;
    private TextField width;
    private TextField length;
    private TextField distanceFromLeftThreshold;
    private TextField distanceFromRightThreshold;
    private TextField distanceFromCentre;
    
    public CustomObstacleDialog() {
        super();
        this.setTitle("Obstacle Location");
        this.setHeaderText("Enter the location of the obstacle");
        buildUI();
    }
    
    private void buildUI() {
        // Create a grid pane to hold the text fields
        var grid = new GridPane();
        
        // Create labels for the text fields
        var nameLabel = new Label("Name: ");
        var heightLabel = new Label("Height: ");
        var widthLabel = new Label("Width: ");
        var lengthLabel = new Label("Length: ");
        var distanceFromLeftThresholdLabel = new Label("Distance from left threshold: ");
        var distanceFromRightThresholdLabel = new Label("Distance from right threshold: ");
        var distanceFromCentreLabel = new Label("Distance from centre: ");
        
        // Create the text fields
        name = new TextField();
        height = new TextField();
        width = new TextField();
        length = new TextField();
        distanceFromLeftThreshold = new TextField();
        distanceFromRightThreshold = new TextField();
        distanceFromCentre = new TextField();
        
        // Add the labels and text fields to the grid
        grid.add(nameLabel, 0, 0);
        grid.add(name, 1, 0);
        grid.add(heightLabel, 0, 1);
        grid.add(height, 1, 1);
        grid.add(widthLabel, 0, 2);
        grid.add(width, 1, 2);
        grid.add(lengthLabel, 0, 3);
        grid.add(length, 1, 3);
        grid.add(distanceFromLeftThresholdLabel, 0, 4);
        grid.add(distanceFromLeftThreshold, 1, 4);
        grid.add(distanceFromRightThresholdLabel, 0, 5);
        grid.add(distanceFromRightThreshold, 1, 5);
        grid.add(distanceFromCentreLabel, 0, 6);
        grid.add(distanceFromCentre, 1, 6);
        
        // Set the content of the dialog to the grid
        getDialogPane().setContent(grid);
        
        // Add OK and Cancel buttons to the dialog
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        // Set the action for the OK button
        var okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
        okButton.setOnAction(e -> {
            if (!validateDialog()) {
                e.consume();
            }
        });
    }
    
    private boolean validateDialog() {
        // Check that all the text fields have been filled in
        if (name.getText().isEmpty() ||
            height.getText().isEmpty() ||
            width.getText().isEmpty() ||
            length.getText().isEmpty() ||
            distanceFromLeftThreshold.getText().isEmpty() ||
            distanceFromRightThreshold.getText().isEmpty() ||
            distanceFromCentre.getText().isEmpty()) {
            return false;
        }
        return true;
    }
    
    public String getName() {
        return name.getText();
    }
    
    public String getHeightValue() {
        return height.getText();
    }
    
    
    public String getWidthValue() {
        return width.getText();
    }
    
    public String getLength() {
        return length.getText();
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
