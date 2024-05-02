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
import uk.ac.soton.comp2211.Utility.PDFExporter;
import uk.ac.soton.comp2211.model.Runway;
import uk.ac.soton.comp2211.scene.MainScene;
import uk.ac.soton.comp2211.Utility.DBUtils;
import uk.ac.soton.comp2211.model.Database;
import uk.ac.soton.comp2211.Utility.ImageExporter;
import uk.ac.soton.comp2211.Utility.XMLExporter;

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
    Menu asJPG = new Menu("as JPG");
    Menu asPNG = new Menu("as PNG");
    Menu calculations = new Menu("Calculations");

    MenuItem exportPDF = new MenuItem("as PDF");
    exportPDF.setOnAction(event -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Save PDF");
      fileChooser.getExtensionFilters().addAll(
          new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
      );
      File file = fileChooser.showSaveDialog(null); // You might want to provide a window here instead of null if available
      if (file != null) {
        Runway selectedRunway = RunwayBox.getRunwaySelection().getSelectionModel().getSelectedItem();
        if (selectedRunway != null) {
          PDFExporter.exportRunwayViews(MainScene.getRunwayViewBox().getTopDownRunway(), MainScene.getRunwayViewBox().getSideRunway(), RunwayBox.getAirportSelection().getSelectionModel().getSelectedItem().getAirportName(), selectedRunway.getRunwayName(), selectedRunway, file.getPath());
        } else {
          System.out.println("No runway selected.");
        }
      } else {
        System.out.println("File save cancelled.");
      }
    });



    MenuItem exportTopDownJPG = new MenuItem("Export TopDown View");
    MenuItem exportSideViewJPG = new MenuItem("Export Side View");
    MenuItem exportSimultaneousJPG = new MenuItem("Export Simultaneous View");

    exportTopDownJPG.setOnAction(event -> ImageExporter.exportViewAsImage(MainScene.getRunwayViewBox().getTopDownRunway(), "jpg"));
    exportSideViewJPG.setOnAction(event -> ImageExporter.exportViewAsImage(MainScene.getRunwayViewBox().getSideRunway(), "jpg"));
    exportSimultaneousJPG.setOnAction(event -> ImageExporter.exportViewAsImage(MainScene.getRunwayViewBox().getSimulataneaousView(), "jpg"));

    MenuItem exportTopDownPNG = new MenuItem("Export TopDown View");
    MenuItem exportSideViewPNG = new MenuItem("Export Side View");
    MenuItem exportSimultaneousPNG = new MenuItem("Export Simultaneous View");

    exportTopDownPNG.setOnAction(event -> ImageExporter.exportViewAsImage(MainScene.getRunwayViewBox().getTopDownRunway(), "png"));
    exportSideViewPNG.setOnAction(event -> ImageExporter.exportViewAsImage(MainScene.getRunwayViewBox().getSideRunway(), "png"));
    exportSimultaneousPNG.setOnAction(event -> ImageExporter.exportViewAsImage(MainScene.getRunwayViewBox().getSimulataneaousView(), "png"));

    asJPG.getItems().addAll(exportTopDownJPG, exportSideViewJPG, exportSimultaneousJPG);
    asPNG.getItems().addAll(exportTopDownPNG, exportSideViewPNG, exportSimultaneousPNG);
    calculations.getItems().addAll(exportPDF);

    MenuItem exportObstacles = new MenuItem("Export Obstacles");
    exportObstacles.setOnAction(event -> exportObstacles());
    MenuItem exportAirports = new MenuItem("Export Airports");
    exportAirports.setOnAction(event -> exportAirports());

    snapshot.getItems().addAll(asJPG, asPNG);
    asXML.getItems().addAll(exportObstacles, exportAirports);

    exportOption.getItems().addAll(asXML, snapshot, calculations);

    fileButton.getItems().addAll(
        importOption,
        exportOption,
        new MenuItem("Reset")
    );

    MenuItem editObstacles = new MenuItem("Edit Obstacles");
    editObstacles.setOnAction(event -> loadFXML(event, "edit-obstacles.fxml"));

    MenuItem editAirports = new MenuItem("Edit Airports");
    editAirports.setOnAction(event -> loadFXML(event, "edit-airport.fxml"));

    editButton = new MenuButton("Edit");
    editButton.getItems().addAll(
        editObstacles,
        editAirports,
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


  private void exportObstacles() {
    XMLExporter.exportObstacles();
  }

  private void exportAirports() {
    XMLExporter.exportAirports();
  }

  public void changeStyling(String backgroundColor) {
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