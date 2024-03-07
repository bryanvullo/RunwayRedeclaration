package uk.ac.soton.comp2211.Utility;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import uk.ac.soton.comp2211.model.Database;


import java.io.IOException;
import java.util.logging.Logger;

public class DBUtils {

  static Database database = null;
  static MongoClient mongoClient = null;

  private static final Logger logger = Logger.getLogger(Database.class.getName());

  public static void changeScene(ActionEvent actionEvent, String fxmlFile, String title, String username, String acess_level) {
    Parent root = null;
    if (username != null && acess_level != null) {
      try {
        FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
        root = loader.load();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      try {
        FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
        root = loader.load();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    stage.setTitle(title);
    stage.setScene(new Scene(root));
    stage.show();
  }

  public static void signUpUser(ActionEvent actionEvent, String username, String password, String acess_level) {
    try {
      mongoClient = MongoClients.create(Database.getConnectionString());
      database = new Database(mongoClient);
      logger.info("Connected to the database");
    } catch (Exception e) {
      logger.warning("Failed to connect to the database");
    }


    if (!username.isEmpty() && !password.isEmpty()) {
      database.insertUser(username, password, acess_level);
      logger.info("User registered successfully");
      changeScene(actionEvent, "/fxml/mainPage.fxml", "Login", null, null);
    } else {
      logger.warning("Username or password is empty");
    }
  }

  public static void logInUser(ActionEvent actionEvent, String username, String password) {
    try {
      mongoClient = MongoClients.create(Database.getConnectionString());
      database = new Database(mongoClient);
      logger.info("Connected to the database");
    } catch (Exception e) {
      logger.warning("Failed to connect to the database");
    }
    if (username != null && password != null) {
      if (database.userExists(username)) {
        logger.info("User exists");
        if (database.checkPassword(username, password)) {
          DBUtils.changeScene(actionEvent, "/fxml/TopDownView.fxml", "TopDown View", username, database.getAccessLevel(username).toString());
          logger.info("Password is correct");
        } else {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setContentText("Invalid username or password! If you forgot username or password, please sign up again!");
          alert.show();
          logger.warning("Password is incorrect");
        }
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Invalid username or password! If you forgot username or password, please sign up again!");
        alert.show();
        logger.warning("User does not exist");
      }
    } else {
      logger.warning("Text fields are not initialized");
    }
  }

  public static boolean isDbConnected() {
    try {
      mongoClient = MongoClients.create(Database.getConnectionString());
      database = new Database(mongoClient);
      logger.info("Connected to the database");
      return true;
    } catch (Exception e) {
      logger.warning("Failed to connect to the database");
      return false;
    }
  }
}
