package uk.ac.soton.comp2211.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import uk.ac.soton.comp2211.Utility.DBUtils;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SignUpController implements Initializable {

  @FXML
  Button buttonSign_Up;
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

  private final Logger logger = Logger.getLogger(LoginController.class.getName());

  private final String[] accessLevels = {"Viewer", "Editor"};

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {


    alvlChoiceBox.getItems().addAll(accessLevels);
    buttonSign_Up.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        String access_level = alvlChoiceBox.getValue();

        if (!textFieldUserName.getText().trim().isEmpty() && !textFieldPassword.getText().trim().isEmpty()) {
          DBUtils.signUpUser(actionEvent, textFieldUserName.getText().trim(), textFieldPassword.getText().trim(), access_level);
        } else {
          System.out.println("Please fill in all the fields");
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setContentText("Please fill in all the fields to sign up!");
          alert.show();
        }
      }
    });

    if (DBUtils.isDbConnected()) {
      testConnection1.setText("Database Status: Connected");
    } else {
      testConnection1.setText("Database Status: DisConnected");
    }

    button_Loggin.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        logger.info("Loaded login-page.fxml file");
        DBUtils.changeScene(actionEvent, "/fxml/login-page.fxml", "Login", null, null);
      }
    });
  }
}
