package uk.ac.soton.comp2211.component;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.Utility.PDFExporter;
import uk.ac.soton.comp2211.model.Runway;
import uk.ac.soton.comp2211.model.User;
import uk.ac.soton.comp2211.scene.MainScene;
import uk.ac.soton.comp2211.Utility.DBUtils;
import uk.ac.soton.comp2211.model.Database;
import uk.ac.soton.comp2211.Utility.ImageExporter;
import uk.ac.soton.comp2211.Utility.XMLExporter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class MenuBar extends HBox {

  private static final Logger logger = LogManager.getLogger(MenuBar.class);

  private MenuButton fileButton;
  private MenuButton editButton;
  private MenuButton settingsButton;
  private MenuButton helpButton;
  private MenuButton userButton;

  MenuItem editAirports;
  MenuItem importAirports;
  User currentUser;

  public MenuBar() {
    currentUser = Database.getCurrentUser();
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
    importAirports = new MenuItem("Import Airports");
    if (currentUser.getAccessLevel() == User.AccessLevel.VIEWER) {
      importAirports.setOnAction(e -> showAlertDialog(Alert.AlertType.INFORMATION, "You do not have permission to import airports. Only editors and administrators can import airports." + " Your access level is: " + currentUser.getAccessLevel()));
    } else {
      importAirports.setOnAction(event -> loadFXML(event, "importAirport.fxml"));
    }
    importOption.getItems().addAll(importObstacles, importAirports);

    Menu exportOption = new Menu("Export");
    Menu asXML = new Menu("As XML");
    Menu snapshot = new Menu("Snapshot");
    Menu asJPG = new Menu("as JPG");
    Menu asPNG = new Menu("as PNG");
    Menu calculations = new Menu("Calculations");
    MenuItem notifiactions = new MenuItem("Notifications");
    notifiactions.setOnAction(event -> exportNotifications());

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
          System.out.println(MainScene.getCalculationBreakdown().getAsdaBreakdown() + " " + MainScene.getCalculationBreakdown().getToraBreakdown() + " " + MainScene.getCalculationBreakdown().getTodaBreakdown() + " " + MainScene.getCalculationBreakdown().getLdaBreakdown());
          PDFExporter.exportRunwayViews(MainScene.getRunwayViewBox().getTopDownRunway(), MainScene.getRunwayViewBox().getSideRunway(), RunwayBox.getAirportSelection().getSelectionModel().getSelectedItem().getAirportName(), selectedRunway.getName(), file.getPath(), MainScene.getCalculationBreakdown(), MainScene.getCalculationTab());
          showAlertDialog(Alert.AlertType.INFORMATION, "PDF saved to " + file.getAbsolutePath());
        } else {
          showAlertDialog(Alert.AlertType.ERROR, "No runway selected.");
          System.out.println("No runway selected.");
        }
      } else {
        showAlertDialog(Alert.AlertType.ERROR, "File save cancelled.");
        System.out.println("File save cancelled.");
      }
    });


    MenuItem exportTopDownJPG = new MenuItem("Export TopDown View");
    MenuItem exportSideViewJPG = new MenuItem("Export Side View");
    MenuItem exportSimultaneousJPG = new MenuItem("Export Simultaneous View");

    exportTopDownJPG.setOnAction(event -> ImageExporter.exportViewAsImage(MainScene.getRunwayViewBox().getTopDownRunway(), "jpg"));
    exportSideViewJPG.setOnAction(event -> ImageExporter.exportViewAsImage(MainScene.getRunwayViewBox().getSideRunway(), "jpg"));
//    exportSimultaneousJPG.setOnAction(event -> ImageExporter.exportViewAsImage(MainScene.getRunwayViewBox().getSimulataneaousView(), "jpg"));

    MenuItem exportTopDownPNG = new MenuItem("Export TopDown View");
    MenuItem exportSideViewPNG = new MenuItem("Export Side View");
    MenuItem exportSimultaneousPNG = new MenuItem("Export Simultaneous View");

    exportTopDownPNG.setOnAction(event -> ImageExporter.exportViewAsImage(MainScene.getRunwayViewBox().getTopDownRunway(), "png"));
    exportSideViewPNG.setOnAction(event -> ImageExporter.exportViewAsImage(MainScene.getRunwayViewBox().getSideRunway(), "png"));
//    exportSimultaneousPNG.setOnAction(event -> ImageExporter.exportViewAsImage(MainScene.getRunwayViewBox().getSimulataneaousView(), "png"));

    asJPG.getItems().addAll(exportTopDownJPG, exportSideViewJPG, exportSimultaneousJPG);
    asPNG.getItems().addAll(exportTopDownPNG, exportSideViewPNG, exportSimultaneousPNG);
    calculations.getItems().addAll(exportPDF);

    MenuItem exportObstacles = new MenuItem("Export Obstacles");
    exportObstacles.setOnAction(event -> exportObstacles());
    MenuItem exportAirports = new MenuItem("Export Airports");
    exportAirports.setOnAction(event -> exportAirports());

    snapshot.getItems().addAll(asJPG, asPNG);
    asXML.getItems().addAll(exportObstacles, exportAirports);

    exportOption.getItems().addAll(asXML, snapshot, calculations, notifiactions);

    MenuItem reset = new MenuItem("Reset");
    reset.setOnAction(e -> resetSelections());

    fileButton.getItems().addAll(
        importOption,
        exportOption,
        reset
    );

    MenuItem editObstacles = new MenuItem("Edit Obstacles");
    editObstacles.setOnAction(event -> loadFXML(event, "edit-obstacles.fxml"));

    editAirports = new MenuItem("Edit Airports");

    if (User.AccessLevel.VIEWER == currentUser.getAccessLevel()) {
      editAirports.setOnAction(e -> showAlertDialog(Alert.AlertType.INFORMATION, "You do not have permission to edit airports. Only editors and administrators can edit airports." + " Your access level is: " + currentUser.getAccessLevel()));
    } else {
      editAirports.setOnAction(MenuBar::editAirportFXML);
    }
    editButton = new MenuButton("Edit");
    editButton.getItems().addAll(
        editObstacles,
        editAirports
    );

    settingsButton = new MenuButton("Settings");
    settingsButton.getItems().addAll(
        new MenuItem("Change Colour Scheme"),
        new MenuItem("Light/Dark Mode")
    );

    MenuItem userGuide = new MenuItem("User Guide");
    userGuide.setOnAction(e -> openUserGuide());

    MenuItem tutorialVideo = new MenuItem("Walkthrough Tutorial Video");
    tutorialVideo.setOnAction(e -> openVideoLink());

    MenuItem contactUs = new MenuItem("Contact Us");
    contactUs.setOnAction(e -> loadFXML(e, "contact-us.fxml"));

    helpButton = new MenuButton("Help");
    helpButton.getItems().addAll(
        tutorialVideo,
        userGuide,
        contactUs
    );

    getChildren().addAll(fileButton, editButton, settingsButton, helpButton);

    var filler = new HBox();
    getChildren().add(filler);
    setHgrow(filler, Priority.ALWAYS);


    MenuItem logoutItem = new MenuItem("Log Out");
    logoutItem.setOnAction(event -> performLogout());

    MenuItem manageUsersItem = new MenuItem("Manage Users");
    if (currentUser.getAccessLevel() != User.AccessLevel.ADMIN) {
      manageUsersItem.setOnAction(e -> showAlertDialog(Alert.AlertType.INFORMATION, "You do not have permission to manage users. Only administrators can manage users." + " Your access level is: " + currentUser.getAccessLevel()));
    } else {
      manageUsersItem.setOnAction(event -> loadFXML(event, "manage-users.fxml"));
    }

    MenuItem username = new MenuItem(Database.getCurrentUser().getUsername());
    userButton = new MenuButton();

    var userImage = new ImageView(Objects.requireNonNull(getClass().getResource("/img/user.png")).toExternalForm());
    userImage.setFitWidth(20);
    userImage.setFitHeight(20);
    userButton.setGraphic(userImage);
    userButton.getItems().addAll(username, manageUsersItem, logoutItem);

    getChildren().add(userButton);

  }

  private void exportNotifications() {
    String allMessages = MainScene.getSystemMessageBox().getAllMessages();  // Ensure you have a getter for SystemMessageBox in MainScene
    if (allMessages.isEmpty()) {
      showAlertDialog(Alert.AlertType.INFORMATION, "No messages to export.");
      return;
    }

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save Notifications");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Text Files", "*.txt")
    );
    fileChooser.setInitialFileName("SystemNotifications.txt");
    File file = fileChooser.showSaveDialog(null); // Use a relevant window here if available

    if (file != null) {
      try (PrintWriter out = new PrintWriter(file)) {
        out.println(allMessages);
        showAlertDialog(Alert.AlertType.INFORMATION, "Notifications exported successfully to " + file.getAbsolutePath());
      } catch (FileNotFoundException e) {
        showAlertDialog(Alert.AlertType.ERROR, "Failed to save file.");
        logger.error("Failed to save notifications file: ", e);
      }
    }
  }

  private static void showAlertDialog(Alert.AlertType alertType, String message) {
    Alert alert = new Alert(alertType);
    alert.setContentText(message);
    alert.show();
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


  private void openUserGuide() {
    if (Desktop.isDesktopSupported()) {
      try {
        // Assuming the PDF file is located under the resources directory
        URL pdfPath = getClass().getResource("/pdf/Deliverable4Specification.pdf");
        assert pdfPath != null;
        File pdfFile = new File(pdfPath.toURI());
        Desktop.getDesktop().open(pdfFile);
      } catch (IOException | URISyntaxException e) {
        logger.error("Failed to open the user guide.", e);
        showAlertDialog(Alert.AlertType.ERROR, "Failed to open the user guide.");
      }
    } else {
      showAlertDialog(Alert.AlertType.ERROR, "Desktop is not supported on this platform.");
    }
  }

  public void resetSelections() {
    if (MainScene.getObstaclesBox().getObstacleChooser() != null) {
      MainScene.getObstaclesBox().getObstacleChooser().getSelectionModel().clearSelection();
    }
    if (RunwayBox.getRunwaySelection() != null) {
      RunwayBox.getRunwaySelection().getSelectionModel().clearSelection();
    }
    if (RunwayBox.getAirportSelection() != null) {
      RunwayBox.getAirportSelection().getSelectionModel().clearSelection();
    }
    MainScene.getRunwayViewBox().changeViewToTopdown();
    // Add more resets as needed
  }


  public static void editAirportFXML(ActionEvent event) {

    if (Database.getCurrentUser().getAccessLevel() == User.AccessLevel.VIEWER) {
      showAlertDialog(Alert.AlertType.INFORMATION, "You do not have permission to edit airports. Only editors and administrators can edit airports." + " Your access level is: " + Database.getCurrentUser().getAccessLevel());
    } else {
      System.out.println(Database.getCurrentUser().getAccessLevel());
      SystemMessageBox.addMessage(Database.getCurrentUser().getAccessLevel().toString());
      FXMLLoader loader = new FXMLLoader(MenuBar.class.getResource("/fxml/edit-airport.fxml"));
      Parent root = null;
      try {
        root = loader.load();
      } catch (IOException e) {
        e.printStackTrace();
      }
      Scene scene = new Scene(root);
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setResizable(false);
      stage.setScene(scene);
      stage.show();
    }
  }

  private void openVideoLink() {
    if (Desktop.isDesktopSupported()) {
      try {
        Desktop.getDesktop().browse(new URL("https://www.youtube.com/watch?v=dQw4w9WgXcQ&ab_channel=RickAstley").toURI());
      } catch (IOException | URISyntaxException e) {
        logger.error("Failed to open the tutorial video.", e);
        showAlertDialog(Alert.AlertType.ERROR, "Failed to open the tutorial video.");
      }
    } else {
      showAlertDialog(Alert.AlertType.ERROR, "Desktop is not supported on this platform.");
    }
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
    stage.setResizable(false);
    stage.show();
  }

  private void performLogout() {
    try {
      Stage stage = (Stage) userButton.getScene().getWindow();
      DBUtils.closeStage(stage);
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login-page.fxml"));
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