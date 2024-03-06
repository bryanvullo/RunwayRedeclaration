package uk.ac.soton.comp2211.control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class MainController implements Initializable {
    
    @FXML
    private Button recalculateButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button editButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button addButton;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recalculateButton.setOnAction(this::recalculate);
        saveButton.setOnAction(this::save);
        editButton.setOnAction(this::editObstacle);
        removeButton.setOnAction(this::removeObstacle);
        addButton.setOnAction(this::addObstacle);
    }
    
    public void recalculate(ActionEvent e) {
        System.out.println("Recalculating...");
    }
    
    public void save(ActionEvent e) {
        System.out.println("Saving");
    }
    
    public void editObstacle(ActionEvent e) {
        System.out.println("Editing obstacle");
    }
    
    public void addObstacle(ActionEvent e) {
        System.out.println("Adding obstacle");
    }
    
    public void removeObstacle(ActionEvent e) {
        System.out.println("Removing obstacle");
    }
}
