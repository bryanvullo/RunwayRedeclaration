package uk.ac.soton.comp2211.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import uk.ac.soton.comp2211.Utility.DBUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class TestController implements Initializable {

  @FXML
  Button logoutButton;
  @FXML
  Label welcomeLabel;
  @FXML
  Label acessLevelLabel;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    logoutButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        DBUtils.changeScene(actionEvent, "/fxml/login-page.fxml", "Login", null, null);
      }
    });
  }

  public void setUserInformation(String username, String acess_Level) {
    welcomeLabel.setText("Welcome " + username);
    acessLevelLabel.setText("Your Access Level is: " + acess_Level);
  }
}
