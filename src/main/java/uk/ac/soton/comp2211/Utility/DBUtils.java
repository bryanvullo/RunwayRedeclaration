package uk.ac.soton.comp2211.Utility;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import uk.ac.soton.comp2211.UI.AppWindow;
import uk.ac.soton.comp2211.model.Database;
import uk.ac.soton.comp2211.scene.MainScene;


import java.io.IOException;
import java.util.logging.Logger;

public class DBUtils {

  static Database database = null;
  static MongoClient mongoClient = null;

  private static final Logger logger = Logger.getLogger(Database.class.getName());
  private static Window stage;

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

  public static void changeSceneToMainScene(ActionEvent actionEvent, AppWindow appWindow) {
    MainScene mainScene = MainScene.getInstance(appWindow);
    mainScene.initialise();
    mainScene.build();

    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Scene scene = new Scene(mainScene.getRoot());
    scene.getStylesheets().add(DBUtils.class.getResource("/css/main.css").toExternalForm());
    stage.setTitle("Runway Re-declaration Tool");
    stage.setScene(scene);
    stage.show();
  }

  public static void logInUser(ActionEvent actionEvent, String username, String password) throws IOException {
    changeSceneToMainScene(actionEvent, new AppWindow(new Stage(), 1000, 800));
  }

  public static void closeStage(Stage stage) {
    stage.close();
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