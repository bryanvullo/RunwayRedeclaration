package uk.ac.soton.comp2211.control;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uk.ac.soton.comp2211.UI.AppWindow;
import uk.ac.soton.comp2211.model.Database;
import uk.ac.soton.comp2211.model.User;
import uk.ac.soton.comp2211.Utility.DBUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
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
  private User currentUser;

  private final Logger logger = Logger.getLogger(LoginController.class.getName());

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    if (DBUtils.isDbConnected()) {
      testConnection.setText("Database Status: Connected");
    } else {
      testConnection.setText("Database Status: DisConnected");
    }
  }


  public void toggleVisibility() {
    if (textField_Password.isVisible()) {
      textField_Password.setVisible(false);
      textField_Password.setManaged(false);
      textField_Password.setDisable(true);
      textField_Password.setOpacity(0);
      textField_Password.setMouseTransparent(true);
      textField_Password.setPromptText("Password");
      textField_Password.setText(textField_Password.getText());
    } else {
      textField_Password.setVisible(true);
      textField_Password.setManaged(true);
      textField_Password.setDisable(false);
      textField_Password.setOpacity(1);
      textField_Password.setMouseTransparent(false);
      textField_Password.setPromptText("");
      textField_Password.setText(textField_Password.getText());
    }
  }

  public void openSignUp(ActionEvent actionEvent) {
    DBUtils.closeStage((Stage) textField_UserName.getScene().getWindow());
    DBUtils.changeScene(actionEvent, "/fxml/sign-up.fxml", "Sign Up", null, null);
  }

  public void performLogin(ActionEvent actionEvent) throws IOException {
    if (textField_UserName != null && textField_Password != null) {
      String username = textField_UserName.getText().trim();
      String password = textField_Password.getText().trim();

      // Check username for special characters and spaces
      if (!username.matches("[a-zA-Z0-9]+")) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Username should only contain letters and numbers, no special characters or spaces allowed", ButtonType.OK);
        alert.showAndWait();
        return;
      }

      // Check password length
      if (password.length() < 6) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Password should be at least 6 characters long", ButtonType.OK);
        alert.showAndWait();
        return;
      }

      // Check password for spaces
      if (password.contains(" ")) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Password should not contain spaces", ButtonType.OK);
        alert.showAndWait();
        return;
      }

      if (Database.userExists(username)) {
        logger.info("User exists");
        if (Database.checkPassword(username, password)) {
          logger.info("Password is correct");
          currentUser = Database.getUser(username); // will implement this
          logger.log(Level.INFO, "User {0} logged in successfully", currentUser.getUsername());
          Notification.showNotification("User: " + username + " logged in successfully");
          DBUtils.closeStage((Stage) textField_UserName.getScene().getWindow());
          DBUtils.changeSceneToMainScene(actionEvent, new AppWindow(new Stage(), 1000, 800));
        } else {
          showAlert("Password is incorrect");
        }
      } else {
        showAlert("User does not exist");
      }
    }
  }

  public void showAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
    alert.showAndWait();
  }

  public User getUsername() {
    return currentUser;
  }
}