package uk.ac.soton.comp2211.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import uk.ac.soton.comp2211.Utility.DBUtils;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class LoginController implements Initializable {

  @FXML
  private Button buttonLogin;
  @FXML
  private Button buttonSignUp;
  @FXML
  private TextField textField_UserName;
  @FXML
  private TextField textField_Password;
  @FXML
  private Label testConnection;

  private final Logger logger = Logger.getLogger(LoginController.class.getName());

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    buttonLogin.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        DBUtils.logInUser(actionEvent, textField_UserName.getText(), textField_Password.getText());
      }
    });

    if (DBUtils.isDbConnected()) {
      testConnection.setText("Connected");
    } else {
      testConnection.setText("DisConnected");
    }

    buttonSignUp.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        logger.info("Loaded signUp.fxml file");
        DBUtils.changeScene(actionEvent, "/fxml/signUp.fxml", "Sign Up", null, null);
      }
    });
  }
}