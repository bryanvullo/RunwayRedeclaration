package uk.ac.soton.comp2211.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.ac.soton.comp2211.model.obstacles.AdvancedObstacle;
import uk.ac.soton.comp2211.scene.MainScene;

import java.io.File;
import java.util.Optional;

public class ImportObstacleController {

  @FXML
  JFXButton selectFileButton = new JFXButton();
  @FXML
  JFXButton importButton = new JFXButton();
  @FXML
  JFXListView<AdvancedObstacle> obstaclesListView;
  private static ObservableList<AdvancedObstacle> obstacles = FXCollections.observableArrayList();

  public void initialize() {
    selectFileButton.setOnAction(e -> selectFile());
    importButton.setOnAction(e -> importObstacles());
    obstaclesListView.setItems(obstacles);
    obstaclesListView.setCellFactory(lv -> new ListCell<>() {
      @Override
      protected void updateItem(AdvancedObstacle obstacle, boolean empty) {
        super.updateItem(obstacle, empty);
        setText(empty || obstacle == null ? null : obstacle.getObstacleName() + " - Height: " + obstacle.getHeight());
      }
    });
  }

  private void importObstacles() {
    ObservableList<AdvancedObstacle> currentObstacles = MainScene.getObstaclesBox().getObstacleChooser().getItems();

    for (AdvancedObstacle newObstacle : obstacles) {
      Optional<ButtonType> userChoice = isDuplicate(newObstacle.getObstacleName()) ?
          askUserAboutDuplicate(newObstacle.getObstacleName()) : Optional.empty();

      if (!userChoice.isPresent() || userChoice.get().getText().equals("Add Anyway")) {
        // If no duplicate or user chooses to add anyway, add as a new entry
        currentObstacles.add(newObstacle);
      } else if (userChoice.get().getText().equals("Replace")) {
        // If user chooses to replace, remove the existing and add the new one
        currentObstacles.removeIf(obstacle -> obstacle.getObstacleName().equals(newObstacle.getObstacleName()));
        currentObstacles.add(newObstacle);
      }
      // If user chooses "Skip", do nothing
    }
    // Close the stage after importing
    Stage stage = (Stage) importButton.getScene().getWindow();
    stage.close();
  }

  private void selectFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Obstacle File");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
    File file = fileChooser.showOpenDialog(importButton.getScene().getWindow());
    if (file != null) {
      loadObstacles(file);
    }
  }

  private void loadObstacles(File file) {
    try {
      DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document doc = dBuilder.parse(file);
      doc.getDocumentElement().normalize();

      NodeList nList = doc.getElementsByTagName("obstacle");
      for (int i = 0; i < nList.getLength(); i++) {
        Node node = nList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) node;
          AdvancedObstacle obstacle = createObstacleFromElement(element);
          obstacles.add(obstacle); // Add normally or as a copy
        }
      }
    } catch (Exception e) {
      showAlert("Error", "Failed to load obstacles: " + e.getMessage());
    }
  }

  private boolean isDuplicate(String obstacleName) {
    return MainScene.getObstaclesBox().getObstacleChooser().getItems().stream()
        .anyMatch(o -> o.getObstacleName().equals(obstacleName));
  }

  private Optional<ButtonType> askUserAboutDuplicate(String obstacleName) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Duplicate Obstacle Found");
    alert.setHeaderText("An obstacle with the name '" + obstacleName + "' already exists.");
    alert.setContentText("Choose an action:");
    ButtonType buttonTypeReplace = new ButtonType("Replace");
    ButtonType buttonTypeAddAnyway = new ButtonType("Add Anyway");
    ButtonType buttonTypeSkip = new ButtonType("Skip");
    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    alert.getButtonTypes().setAll(buttonTypeReplace, buttonTypeAddAnyway, buttonTypeSkip, buttonTypeCancel);
    return alert.showAndWait();
  }

  private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  public static void loadInitialeObstacles() {

    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(ImportAirportController.class.getResourceAsStream("/xml/obstacles.xml"));
      doc.getDocumentElement().normalize();

      ObservableList<AdvancedObstacle> obstacles = FXCollections.observableArrayList();
      NodeList nList = doc.getElementsByTagName("obstacle");
      for (int i = 0; i < nList.getLength(); i++) {
        Node node = nList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) node;
          AdvancedObstacle obstacle = createObstacleFromElement(element);
          obstacles.add(obstacle);
        }
      }

      MainScene.getObstaclesBox().addObstacleOptions(obstacles);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static AdvancedObstacle createObstacleFromElement(Element element) {
    String name = element.getElementsByTagName("obstacleName").item(0).getTextContent();
    double height = Double.parseDouble(element.getElementsByTagName("obstacleHeight").item(0).getTextContent());
    double width = Double.parseDouble(element.getElementsByTagName("obstacleWidth").item(0).getTextContent());
    double length = Double.parseDouble(element.getElementsByTagName("obstacleLength").item(0).getTextContent());
    double leftThreshold = Double.parseDouble(element.getElementsByTagName("obstacleLeftThresholdDistance").item(0).getTextContent());
    double rightThreshold = Double.parseDouble(element.getElementsByTagName("obstacleRightThresholdDistance").item(0).getTextContent());
    double centreDistance = Double.parseDouble(element.getElementsByTagName("obstacleDistanceFromCentre").item(0).getTextContent());

    return new AdvancedObstacle(name, height, width, length, rightThreshold, leftThreshold, centreDistance);
  }

  public ObservableList<AdvancedObstacle> getLoadedObstaclesNames() {
    return obstacles;
  }
}
