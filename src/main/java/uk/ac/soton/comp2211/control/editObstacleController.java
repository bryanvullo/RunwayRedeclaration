package uk.ac.soton.comp2211.control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uk.ac.soton.comp2211.component.SystemMessageBox;
import uk.ac.soton.comp2211.model.obstacles.AdvancedObstacle;
import uk.ac.soton.comp2211.scene.MainScene;

public class editObstacleController {
  @FXML
  private TextField nameField, heightField, widthField, lengthField, leftThresholdField, rightThresholdField, centreDistanceField;
  @FXML
  private JFXListView editListView;
  @FXML
  private JFXButton editButton;
  @FXML
  private JFXButton deleteButton;
  @FXML
  private JFXButton closeButton, cancelButton, saveChangesButton, addObstacleButton, confirmButton;
  @FXML
  private JFXButton reloadButton;
  @FXML
  private Label nameLabel, heightLabel, widthLabel, lengthLabel, leftThresholdLabel, rightThresholdLabel, centreDistanceLabel;


  public void initialize() {
    nameField.setVisible(false);
    heightField.setVisible(false);
    widthField.setVisible(false);
    lengthField.setVisible(false);
    leftThresholdField.setVisible(false);
    rightThresholdField.setVisible(false);
    centreDistanceField.setVisible(false);
    saveChangesButton.setVisible(false);
    confirmButton.setVisible(false);
    cancelButton.setVisible(false);


    editButton.setOnAction(e -> startEdit());
    closeButton.setOnAction(e -> closeStage());
    deleteButton.setOnAction(e -> deleteObstacle());
    cancelButton.setOnAction(e -> cancelAddition());
    confirmButton.setOnAction(e -> confirmAddition());
    reloadButton.setOnAction(e -> reloadList());
    addObstacleButton.setOnAction(e -> initiateAddObstacle());


    editListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        updateFields((AdvancedObstacle) newVal);
      }
    });

    editButton.setOnAction(e -> toggleEdit(true));
    saveChangesButton.setOnAction(e -> {
      saveChanges();
      toggleEdit(false);
    });

    editListView.getSelectionModel().selectFirst();
    populateList();
  }

  private void saveChanges() {
    AdvancedObstacle selectedObstacle = (AdvancedObstacle) editListView.getSelectionModel().getSelectedItem();
    if (selectedObstacle != null) {
      selectedObstacle.setObstacleName(nameField.getText());
      selectedObstacle.setHeight(Double.parseDouble(heightField.getText()));
      selectedObstacle.setWidth(Double.parseDouble(widthField.getText()));
      selectedObstacle.setLength(Double.parseDouble(lengthField.getText()));
      selectedObstacle.setDistanceLeftThreshold(Double.parseDouble(leftThresholdField.getText()));
      selectedObstacle.setDistanceRightThreshold(Double.parseDouble(rightThresholdField.getText()));
      selectedObstacle.setDistanceFromCentre(Double.parseDouble(centreDistanceField.getText()));

      SystemMessageBox.addMessage("Obstacle" + selectedObstacle.getObstacleName() +"updated successfully.");

      // Update list to reflect changes
      editListView.refresh();
    }
  }

  private void initiateAddObstacle() {
    clearFields(); // Clear all fields to allow fresh input
    toggleAddObstacle(true); // Set up UI for adding a new obstacle
  }

  private void clearFields() {
    nameField.clear();
    heightField.clear();
    widthField.clear();
    lengthField.clear();
    leftThresholdField.clear();
    rightThresholdField.clear();
    centreDistanceField.clear();
  }

  private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setContentText(content);
    alert.showAndWait();
  }


  private void updateFields(AdvancedObstacle obstacle) {
    nameLabel.setText(obstacle.getObstacleName());
    heightLabel.setText(String.valueOf(obstacle.getHeight()));
    widthLabel.setText(String.valueOf(obstacle.getWidth()));
    lengthLabel.setText(String.valueOf(obstacle.getLength()));
    leftThresholdLabel.setText(String.valueOf(obstacle.getDistanceLeftThreshold()));
    rightThresholdLabel.setText(String.valueOf(obstacle.getDistanceRightThreshold()));
    centreDistanceLabel.setText(String.valueOf(obstacle.getDistanceFromCentre()));

    // Set text fields for editing but don't show them
    nameField.setText(obstacle.getObstacleName());
    heightField.setText(String.valueOf(obstacle.getHeight()));
    widthField.setText(String.valueOf(obstacle.getWidth()));
    lengthField.setText(String.valueOf(obstacle.getLength()));
    leftThresholdField.setText(String.valueOf(obstacle.getDistanceLeftThreshold()));
    rightThresholdField.setText(String.valueOf(obstacle.getDistanceRightThreshold()));
    centreDistanceField.setText(String.valueOf(obstacle.getDistanceFromCentre()));
  }


  private void toggleAddObstacle(boolean isAdding) {
    // Toggle visibility of fields and labels
    nameField.setVisible(isAdding);
    heightField.setVisible(isAdding);
    widthField.setVisible(isAdding);
    lengthField.setVisible(isAdding);
    leftThresholdField.setVisible(isAdding);
    rightThresholdField.setVisible(isAdding);
    centreDistanceField.setVisible(isAdding);
    saveChangesButton.setVisible(isAdding);

    nameLabel.setVisible(!isAdding);
    heightLabel.setVisible(!isAdding);
    widthLabel.setVisible(!isAdding);
    lengthLabel.setVisible(!isAdding);
    leftThresholdLabel.setVisible(!isAdding);
    rightThresholdLabel.setVisible(!isAdding);
    centreDistanceLabel.setVisible(!isAdding);

    // Control button visibility
    editButton.setVisible(!isAdding);
    deleteButton.setVisible(!isAdding);
    reloadButton.setVisible(!isAdding);
    addObstacleButton.setVisible(!isAdding);

    // Show confirm and cancel only when adding
    confirmButton.setVisible(isAdding);
    cancelButton.setVisible(isAdding);
  }

  @FXML
  private void startEdit() {
    if (editListView.getSelectionModel().getSelectedItem() != null) {
      toggleEdit(true);
    }
  }


  private void confirmAddition() {
    try {
      if (validateInputs()) {
        double height = Double.parseDouble(heightField.getText());
        double width = Double.parseDouble(widthField.getText());
        double length = Double.parseDouble(lengthField.getText());
        double leftThreshold = Double.parseDouble(leftThresholdField.getText());
        double rightThreshold = Double.parseDouble(rightThresholdField.getText());
        double centreDistance = Double.parseDouble(centreDistanceField.getText());
        String name = nameField.getText().trim();

        AdvancedObstacle newObstacle = new AdvancedObstacle(name, height, width, length, rightThreshold, leftThreshold, centreDistance);
        MainScene.getObstaclesBox().getObstacleChooser().getItems().add(newObstacle);
        editListView.getItems().add(newObstacle); // Update UI list
        reloadList(); // Optionally refresh list view

        toggleAddObstacle(false); // Reset UI
      }
    } catch (NumberFormatException e) {
      showAlert("Input Error", "Please enter valid numbers for height, width, length, and distances.");
    }
  }

  private boolean validateInputs() {
    if (nameField.getText().isEmpty() ||
        heightField.getText().isEmpty() ||
        widthField.getText().isEmpty() ||
        lengthField.getText().isEmpty() ||
        leftThresholdField.getText().isEmpty() ||
        rightThresholdField.getText().isEmpty() ||
        centreDistanceField.getText().isEmpty()) {
      showAlert("Input Error", "All fields must be filled.");
      return false;
    }
    return true;
  }

  private void cancelAddition() {
    clearFields();
    toggleAddObstacle(false); // Hide input fields and show regular buttons
  }

  private void toggleEdit(boolean isEditing) {
    // Toggle visibility of text fields and labels
    nameField.setVisible(isEditing);
    heightField.setVisible(isEditing);
    widthField.setVisible(isEditing);
    lengthField.setVisible(isEditing);
    leftThresholdField.setVisible(isEditing);
    rightThresholdField.setVisible(isEditing);
    centreDistanceField.setVisible(isEditing);

    nameLabel.setVisible(!isEditing);
    heightLabel.setVisible(!isEditing);
    widthLabel.setVisible(!isEditing);
    lengthLabel.setVisible(!isEditing);
    leftThresholdLabel.setVisible(!isEditing);
    rightThresholdLabel.setVisible(!isEditing);
    centreDistanceLabel.setVisible(!isEditing);

    // Control button visibility
    editButton.setVisible(!isEditing);
    deleteButton.setVisible(!isEditing);
    reloadButton.setVisible(!isEditing);
    addObstacleButton.setVisible(!isEditing);
    cancelButton.setVisible(isEditing);
    saveChangesButton.setVisible(isEditing);
  }


  private void deleteObstacle() {
    AdvancedObstacle selectedObstacle = (AdvancedObstacle) editListView.getSelectionModel().getSelectedItem();
    if (selectedObstacle != null) {
      MainScene.getObstaclesBox().getObstacleChooser().getItems().remove(selectedObstacle);
      MainScene.getObstaclesBox().getObstacleChooser().getSelectionModel().selectFirst();
      reloadList();
    }
  }

  public void reloadList() {
    editListView.refresh();
    editListView.getSelectionModel().selectFirst();
  }

  public void populateList() {
    editListView.getItems().addAll(MainScene.getObstaclesBox().getObstacleChooser().getItems());
    editListView.getSelectionModel().selectFirst();
  }


  public void closeStage() {
    Stage stage = (Stage) closeButton.getScene().getWindow();
    stage.close();
  }

}