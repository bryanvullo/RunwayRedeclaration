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
  Button button_Loggin;
  @FXML
  RadioButton rBox_ICalc;
  @FXML
  RadioButton rBox_ATC;
  @FXML
  TextField textFieldUserName;
  @FXML
  PasswordField textFieldPassword;

  @FXML
  private Label testConnection1;

  @FXML
  private RadioButton rBox_RTech;

  private final Logger logger = Logger.getLogger(LoginController.class.getName());

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    ToggleGroup toggleGroup = new ToggleGroup();
    rBox_ICalc.setToggleGroup(toggleGroup);
    rBox_ATC.setToggleGroup(toggleGroup);
    rBox_RTech.setToggleGroup(toggleGroup);

    rBox_ICalc.setSelected(true);

    buttonSign_Up.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        String access_level = ((RadioButton) toggleGroup.getSelectedToggle()).getText();

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
        logger.info("Loaded mainPage.fxml file");
        DBUtils.changeScene(actionEvent, "/fxml/mainPage.fxml", "Login", null, null);
      }
    });
  }
}
