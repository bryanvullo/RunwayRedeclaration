package uk.ac.soton.comp2211.control;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ContactUsController {

  @FXML
  JFXButton closeButton;

  @FXML
  public void initialize() {
    closeButton.setOnAction(e -> closeStage());
  }

  public void closeStage() {
    Stage stage = (Stage) closeButton.getScene().getWindow();
    stage.close();
  }
}
