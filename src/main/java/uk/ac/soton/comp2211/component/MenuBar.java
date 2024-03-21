package uk.ac.soton.comp2211.component;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.Utility.DBUtils;

import java.io.IOException;

public class MenuBar extends HBox {

  private static final Logger logger = LogManager.getLogger(MenuBar.class);

  private MenuButton fileButton;
  private MenuButton editButton;
  private MenuButton settingsButton;
  private MenuButton helpButton;
  private MenuButton userButton;
  private MenuButton unitButton;

  public MenuBar() {
    logger.info("Creating the MenuBar");
    build();
  }

  public void build() {
    logger.info("Building the MenuBar");
    setSpacing(10);
    setPadding(new Insets(10));
    setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
    
    fileButton = new MenuButton("File");
    fileButton.getItems().addAll(
        new MenuItem("Import"),
        new MenuItem("Export"),
        new MenuItem("Reset"),
        new MenuItem("Add Airport"),
        new MenuItem("PDF Report")
    );
    editButton = new MenuButton("Edit");
    editButton.getItems().addAll(
        new MenuItem("Edit Airports"),
        new MenuItem("Edit Obstacles"),
        new MenuItem("Undo")
    );
    unitButton = new MenuButton("Units");
    unitButton.getItems().addAll(
        new MenuItem("Metric"),
        new MenuItem("Imperial")
    );
    settingsButton = new MenuButton("Settings");
    settingsButton.getItems().addAll(
        new MenuItem("Change Colour Scheme"),
        new MenuItem("Change Font"),
        new MenuItem("Light/Dark Mode"),
        new MenuItem("System Notifications"),
        new MenuItem("Clear System Messages")
    );
    helpButton = new MenuButton("Help");
    helpButton.getItems().addAll(
        new MenuItem("FAQs"),
        new MenuItem("Walkthrough Tutorial Video"),
        new MenuItem("User Guide"),
        new MenuItem("Contact Us")
    );

    getChildren().addAll(fileButton, editButton, unitButton, settingsButton, helpButton);

    var filler = new HBox();
    getChildren().add(filler);
    setHgrow(filler, Priority.ALWAYS);


    MenuItem logoutItem = new MenuItem("Log Out");
    logoutItem.setOnAction(event -> performLogout());

    userButton = new MenuButton();

    var userImage = new ImageView(getClass().getResource("/img/user.png").toExternalForm());
    userImage.setFitWidth(20);
    userImage.setFitHeight(20);
    userButton.setGraphic(userImage);
    userButton.getItems().addAll(new MenuItem("Username"), new MenuItem("Access Level"), logoutItem);

    getChildren().add(userButton);

  }


  private void performLogout() {
    // Example scene change code. Adjust according to your application's structure.
    try {
      Stage stage = (Stage) userButton.getScene().getWindow(); // Retrieve the current stage
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login-page.fxml")); // Adjust the path to your FXML
      Parent root = loader.load();
      Scene scene = new Scene(root);

      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public MenuButton fileButton() {
    return fileButton;
  }

  public MenuButton editButton() {
    return editButton;
  }

  public MenuButton settingsButton() {
    return settingsButton;
  }

  public MenuButton helpButton() {
    return helpButton;
  }

  public MenuButton userButton() {
    return userButton;
  }


}
