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
    setBackground(new Background(new BackgroundFill(Color.valueOf("051bff"), null, null)));

    fileButton = new MenuButton("File");
    MenuItem importObstacles = new MenuItem("Import Obstacles");
    importObstacles.setOnAction(event -> loadFXML(event, "importObstacles.fxml"));
    MenuItem exportObstacles = new MenuItem("Export Obstacles");
    exportObstacles.setOnAction(event -> exportObstacles());

    fileButton.getItems().addAll(
        importObstacles,
        exportObstacles,
        new MenuItem("Reset"),
        new MenuItem("Add Airport")
    );

    MenuItem editObstacles = new MenuItem("Edit Obstacles");
    editObstacles.setOnAction(event -> loadFXML(event, "edit-obstacles.fxml"));
    editButton = new MenuButton("Edit");
    editButton.getItems().addAll(
        new MenuItem("Edit Airports"),
        editObstacles,
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
    userButton.getItems().addAll(username ,manageUsersItem, logoutItem);

    getChildren().add(userButton);

  }

  private void exportObstacles() {
    try {
      DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

      // root element with namespace and schema
      Document document = documentBuilder.newDocument();
      Element root = document.createElementNS("http://www.github.com/bryanvullo/RunwayRedeclaration", "obstacles");
      root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
      root.setAttribute("xsi:schemaLocation", "http://www.github.com/bryanvullo/RunwayRedeclaration obstacleSchema.xsd");
      document.appendChild(root);

      // Export each obstacle as an <obstacle> element
      for (AdvancedObstacle obstacle : MainScene.getObstaclesBox().getObstacleChooser().getItems()) {
        Element obstacleElement = document.createElement("obstacle");
        root.appendChild(obstacleElement);

        // Obstacle properties using the specified tags from the sample
        createElement(obstacleElement, "obstacleName", obstacle.getObstacleName());
        createElement(obstacleElement, "obstacleHeight", String.valueOf(obstacle.getHeight()));
        createElement(obstacleElement, "obstacleWidth", String.valueOf(obstacle.getWidth()));
        createElement(obstacleElement, "obstacleLength", String.valueOf(obstacle.getLength()));
        createElement(obstacleElement, "obstacleLeftThresholdDistance", String.valueOf(obstacle.getDistanceLeftThreshold()));
        createElement(obstacleElement, "obstacleRightThresholdDistance", String.valueOf(obstacle.getDistanceRightThreshold()));
        createElement(obstacleElement, "obstacleDistanceFromCentre", String.valueOf(obstacle.getDistanceFromCentre()));
      }

      // Create the XML file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2"); // Set indentation amount

      DOMSource domSource = new DOMSource(document);

      // Let the user choose where to save the file
      FileChooser fileChooser = new FileChooser();
      fileChooser.setInitialFileName("exported_obstacles.xml");
      File file = fileChooser.showSaveDialog((Stage) fileButton.getScene().getWindow());

      if (file != null) {
        StreamResult streamResult = new StreamResult(file);
        transformer.transform(domSource, streamResult);
        MainScene.getSystemMessageBox().addMessage("Obstacles exported successfully to " + file.getAbsolutePath());
      }
    } catch (Exception e) {
      e.printStackTrace();
      MainScene.getSystemMessageBox().addMessage("Failed to export obstacles");
    }
  }

  private void createElement(Element parent, String name, String value) {
    Element element = parent.getOwnerDocument().createElement(name);
    element.appendChild(parent.getOwnerDocument().createTextNode(value));
    parent.appendChild(element);
  }


  private void loadFXML(ActionEvent event, String filename) {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + filename));
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