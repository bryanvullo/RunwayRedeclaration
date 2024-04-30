package uk.ac.soton.comp2211.component;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import uk.ac.soton.comp2211.model.obstacles.AdvancedObstacle;
import uk.ac.soton.comp2211.scene.MainScene;
import uk.ac.soton.comp2211.utility.DBUtils;
import uk.ac.soton.comp2211.model.Database;
import uk.ac.soton.comp2211.utility.ImageExporter;
import uk.ac.soton.comp2211.utility.XMLExporter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

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
    setBackground(new Background(new BackgroundFill(Color.valueOf("73a9c2"), null, null)));

    fileButton = new MenuButton("File");

    Menu importOption = new Menu("Import");
    MenuItem importObstacles = new MenuItem("Import Obstacles");
    importObstacles.setOnAction(event -> loadFXML(event, "importObstacles.fxml"));
    MenuItem importAirports = new MenuItem("Import Airports");
    importAirports.setOnAction(event -> loadFXML(event, "importAirport.fxml"));
    importOption.getItems().addAll(importObstacles, importAirports);

    Menu exportOption = new Menu("Export");
    Menu asXML = new Menu("As XML");
    Menu snapshot = new Menu("Snapshot");


    MenuItem exportTopDown = new MenuItem("Export TopDown View");
    MenuItem exportSideView = new MenuItem("Export Side View");
    MenuItem exportSimultaneous = new MenuItem("Export Simultaneous View");

    MenuItem exportObstacles = new MenuItem("Export Obstacles");
    exportObstacles.setOnAction(event -> exportObstacles());
    MenuItem exportAirports = new MenuItem("Export Airports");
//    exportAirports.setOnAction(event -> XMLExporter);

    snapshot.getItems().addAll(exportTopDown, exportSideView, exportSimultaneous);
    asXML.getItems().addAll(exportObstacles, exportAirports);

    exportOption.getItems().addAll(asXML, snapshot);

    fileButton.getItems().addAll(
        importOption,
        exportOption,
        new MenuItem("Reset")
    );

    MenuItem editObstacles = new MenuItem("Edit Obstacles");
    editObstacles.setOnAction(event -> loadFXML(event, "edit-obstacles.fxml"));

    MenuItem editAirports = new MenuItem("Edit Airports");
    editAirports.setOnAction(event -> loadFXML(event, "edit-airport.fxml"));

    MenuItem airportImg = new MenuItem("Capture Airport");
    airportImg.setOnAction(event -> captureAirport());

    editButton = new MenuButton("Edit");
    editButton.getItems().addAll(
        editObstacles,
        editAirports,
        airportImg,
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

    MenuItem manageUsersItem = new MenuItem("Manage Users");
    manageUsersItem.setOnAction(event -> loadFXML(event, "manage-users.fxml"));

    MenuItem username = new MenuItem(Database.getCurrentUser().getUsername());

    userButton = new MenuButton();

    var userImage = new ImageView(Objects.requireNonNull(getClass().getResource("/img/user.png")).toExternalForm());
    userImage.setFitWidth(20);
    userImage.setFitHeight(20);
    userButton.setGraphic(userImage);
    userButton.getItems().addAll(username, manageUsersItem, logoutItem);

    getChildren().add(userButton);

  }

  private void captureAirport() {
    ImageExporter.captureRunwayViewBoxScreenshot(MainScene.getRunwayViewBox());
  }


  private void exportObstacles() {
    XMLExporter.exportObstacles();
  }

  public void changeStyling (String backgroundColor) {
    // Change the styling of the menu bar
    setBackground(new Background(new BackgroundFill(Color.valueOf(backgroundColor), null, null)));
  }


  static void loadFXML(ActionEvent event, String filename) {
    FXMLLoader loader = new FXMLLoader(MenuBar.class.getResource("/fxml/" + filename));
    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Scene scene = new Scene(root);
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(scene);
    stage.show();
  }

  private void performLogout() {
    // Example scene change code. Adjust according to your application's structure.
    try {
      Stage stage = (Stage) userButton.getScene().getWindow(); // Retrieve the current stage
      DBUtils.closeStage(stage); // Close the current stage
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