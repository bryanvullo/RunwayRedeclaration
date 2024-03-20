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
import uk.ac.soton.comp2211.UI.AppWindow;
import uk.ac.soton.comp2211.model.Database;
import uk.ac.soton.comp2211.scene.MainScene;


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

  public static <AppWindow> void changeSceneToMainScene(ActionEvent actionEvent, uk.ac.soton.comp2211.UI.AppWindow appWindow) {
    // Assuming AppWindow is accessible and correctly initialized earlier in your application
    MainScene mainScene = new MainScene(appWindow);
    mainScene.initialise(); // If needed to initialise components
    mainScene.build(); // Builds the UI components

    // Get the current stage from the action event
    Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

    // Set the scene to your main scene
    Scene scene = new Scene(mainScene.getRoot()); // Ensure getRoot() provides access to the root pane of MainScene
    stage.setTitle("Runway Re-declaration Tool");
    stage.setScene(scene);

    stage.show();
  }


  public static void signUpUser(ActionEvent actionEvent, String username, String password, String acess_level) {
    if (!username.isEmpty() && !password.isEmpty()) {
      Database.insertUser(username, password, acess_level);
      logger.info("User registered successfully");
      changeScene(actionEvent, "/fxml/login-page.fxml", "Login", null, null);
    } else {
      logger.warning("Username or password is empty");
    }
  }

  public static void logInUser(ActionEvent actionEvent, String username, String password) throws IOException {
    if (username != null && password != null) {
      if (Database.userExists(username)) {
        logger.info("User exists");
        if (Database.checkPassword(username, password)) {
          changeSceneToMainScene(actionEvent, new AppWindow(new Stage(), 1000, 800));
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
