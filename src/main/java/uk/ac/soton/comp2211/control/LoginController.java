package uk.ac.soton.comp2211.control;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import uk.ac.soton.comp2211.Utility.DBUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class LoginController implements Initializable {

  @FXML
  private JFXButton buttonLogin;
  @FXML
  private Hyperlink buttonSignUp;
  @FXML
  private TextField textField_UserName;
  @FXML
  private PasswordField textField_Password;
  @FXML
  private Label testConnection;

  private final Logger logger = Logger.getLogger(LoginController.class.getName());

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    buttonLogin.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        try {
          DBUtils.logInUser(actionEvent, textField_UserName.getText().trim(), textField_Password.getText().trim());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    });

    if (DBUtils.isDbConnected()) {
      testConnection.setText("Database Status: Connected");
    } else {
      testConnection.setText("Database Status: DisConnected");
    }

    buttonSignUp.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        logger.info("Loaded signUp.fxml file");
        DBUtils.changeScene(actionEvent, "/fxml/sign-up.fxml", "Sign Up", null, null);
      }
    });
  }
}