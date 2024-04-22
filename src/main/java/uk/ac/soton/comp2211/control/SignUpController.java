package uk.ac.soton.comp2211.control;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uk.ac.soton.comp2211.utility.DBUtils;
import uk.ac.soton.comp2211.model.Database;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SignUpController implements Initializable {

  @FXML
  JFXButton buttonSign_Up;
  @FXML
  Hyperlink button_Loggin;
  @FXML
  TextField textFieldUserName;
  @FXML
  PasswordField textFieldPassword;

  @FXML
  private ChoiceBox<String> alvlChoiceBox;

  @FXML
  private Label testConnection1;

  private final Logger logger = Logger.getLogger(SignUpController.class.getName());

  private final String[] accessLevels = {"ADMIN", "VIEWER", "EDITOR"};

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    alvlChoiceBox.getItems().addAll(accessLevels);

    if (DBUtils.isDbConnected()) {
      testConnection1.setText("Database Status: Connected");
    } else {
      testConnection1.setText("Database Status: DisConnected");
    }
  }

  public void openLoginScene(ActionEvent actionEvent) {
    DBUtils.closeStage((Stage) textFieldUserName.getScene().getWindow());
    DBUtils.changeScene(actionEvent, "/fxml/login-page.fxml", "Login", null, null);
  }

  public void signUpUser(ActionEvent actionEvent) throws IOException {
    String username = textFieldUserName.getText().trim();
    String password = textFieldPassword.getText().trim();
    String access_level = alvlChoiceBox.getValue();

    if (!username.isEmpty() && !password.isEmpty() && access_level != null) {

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
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("User already exists");
        alert.show();
      } else {
        Database.insertUser(username, password, access_level);
        this.logger.info("User registered successfully");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(username + " User registered successfully");
        alert.show();
        openLoginScene(actionEvent);
      }
    } else {
      System.out.println("Please fill in all the fields");
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText("Please fill in all the fields to sign up!");
      alert.show();
    }
  }
}
