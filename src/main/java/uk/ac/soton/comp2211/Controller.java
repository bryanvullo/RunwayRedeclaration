package uk.ac.soton.comp2211;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import uk.ac.soton.comp2211.Utility.DBUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

  @FXML
  private Button buttonLogin;
  @FXML
  private Button buttonSignUp;
  @FXML
  private TextField textField_UserName;
  @FXML
  private TextField textField_Password;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    buttonLogin.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        DBUtils.changeScene(actionEvent, "/fxml/TopDownView.fxml", "Sign Up", null, null);
        //DBUtils.logInUser(actionEvent, textField_UserName.getText(), textField_Password.getText());
      }
    });

    buttonSignUp.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        DBUtils.changeScene(actionEvent, "/fxml/signUp.fxml", "Sign Up", null, null);
      }
    });
  }
}