package uk.ac.soton.comp2211.Utility;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;
import uk.ac.soton.comp2211.control.LoginController;


import java.io.IOException;
import java.sql.*;

public class DBUtils {
  public static void changeScene(ActionEvent actionEvent, String fxmlFile, String title, String username, String acess_level) {
    Parent root = null;
    if (username != null && acess_level != null) {
      try {
        FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
        root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setUserInformation(username, acess_level);
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
    var dashboardScene = new Scene(root, 1125, 720);

//    // Calculate the center position of the screen
//    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
//    double centerX = (screenBounds.getWidth() - dashboardScene.getWidth()) / 2;
//    double centerY = (screenBounds.getHeight() - dashboardScene.getHeight()) / 2;
//    // Set the stage position to be centered
//    stage.setX(centerX);
//    stage.setY(centerY);

    stage.setScene(dashboardScene);
    stage.setResizable(false);
    stage.show();
  }

  public static void signUpUser(ActionEvent actionEvent, String username, String password, String acess_level) {
    Connection connection = null;
    PreparedStatement psInsert = null;
    PreparedStatement psCheckUserExists = null;
    ResultSet rs = null;

    try {
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/runway-redeclaration", "root", "S@rv3shull5");
      psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
      psCheckUserExists.setString(1, username);
      rs = psCheckUserExists.executeQuery();

      if (rs.isBeforeFirst()) {
        System.out.println("User already exists");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("User already exists!");
        alert.show();
      } else {
        psInsert = connection.prepareStatement("INSERT INTO users (username, password, acess_level) VALUES (?, ?, ?)");
        psInsert.setString(1, username);
        psInsert.setString(2, password);
        psInsert.setString(3, acess_level);
        psInsert.executeUpdate();
        changeScene(actionEvent, "fxml/mainPage.fxml", "Login", null, null);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (psInsert != null) {
          psInsert.close();
        }
        if (psCheckUserExists != null) {
          psCheckUserExists.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public static void logInUser(ActionEvent actionEvent, String username, String password) {
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/runway-redeclaration", "root", "S@rv3shull5");
      ps = connection.prepareStatement("SELECT password, acess_level FROM users WHERE username = ?");
      ps.setString(1, username);
      rs = ps.executeQuery();

      if (!rs.isBeforeFirst()) {
        System.out.println("Invalid username or password");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Invalid username or password! If you forgot username or password, please sign up again!");
        alert.show();
      } else {
        while (rs.next()) {
          String passwordFromDB = rs.getString("password");
          String accessLevelFromDB = rs.getString("acess_level");
          if (passwordFromDB.equals(password)) {
            changeScene(actionEvent, "fxml/logged-in.fxml", "Login", username, accessLevelFromDB);
          } else {
            System.out.println("Invalid username or password");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid username or password! If you forgot username or password, please sign up again!");
            alert.show();
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (ps != null) {
          ps.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
