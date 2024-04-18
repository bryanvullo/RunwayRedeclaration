package uk.ac.soton.comp2211.control;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class ImportController {

  @FXML
  JFXButton selectFileButton;
  @FXML
  JFXButton importButton;
  @FXML
  JFXListView<AdvancedObstacle> obstaclesListView;
  private ObservableList<String> loadedObstaclesNames = FXCollections.observableArrayList();

  public void initialize() {
    selectFileButton.setOnAction(e -> loadObstacles());
    importButton.setOnAction(e -> importObstacles());
    obstaclesListView.setCellFactory(lv -> new ListCell<AdvancedObstacle>() {
      @Override
      protected void updateItem(AdvancedObstacle obstacle, boolean empty) {
        super.updateItem(obstacle, empty);
        if (empty || obstacle == null) {
          setText(null);
        } else {
          setText(obstacle.getObstacleName() + " - Height: " + obstacle.getHeight());
        }
      }
    });

  }

  private void importObstacles() {
    Stage stage = (Stage) importButton.getScene().getWindow();
    for (int i = 0; i < 100; i++){
      MainScene.getSystemMessageBox().addMessage("Obstacles loaded successfully");
    }

    stage.close();
  }


  private void loadObstacles() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Obstacle File");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("XML Files", "*.xml")
    );

    // You need a reference to the primary stage, modify this part as needed.
    Stage stage = (Stage) obstaclesListView.getScene().getWindow();
    File xmlFile = fileChooser.showOpenDialog(stage);

    if (xmlFile != null) {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      try {
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("obstacle");
        for (int i = 0; i < nList.getLength(); i++) {
          Node node = nList.item(i);
          if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            AdvancedObstacle obstacle = createObstacleFromElement(element);
            obstaclesListView.getItems().add(obstacle);
            loadedObstaclesNames.add(obstacle.getObstacleName());
          }
        }

        MainScene.getObstaclesBox().addObstacleOptions(obstaclesListView.getItems());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private AdvancedObstacle createObstacleFromElement(Element element) {
    String name = element.getElementsByTagName("obstacleName").item(0).getTextContent();
    double height = Double.parseDouble(element.getElementsByTagName("obstacleHeight").item(0).getTextContent());
    double width = Double.parseDouble(element.getElementsByTagName("obstacleWidth").item(0).getTextContent());
    double length = Double.parseDouble(element.getElementsByTagName("obstacleLength").item(0).getTextContent());
    double leftThreshold = Double.parseDouble(element.getElementsByTagName("obstacleLeftThresholdDistance").item(0).getTextContent());
    double rightThreshold = Double.parseDouble(element.getElementsByTagName("obstacleRightThresholdDistance").item(0).getTextContent());
    double centreDistance = Double.parseDouble(element.getElementsByTagName("obstacleDistanceFromCentre").item(0).getTextContent());

    return new AdvancedObstacle(name, height, width, length, rightThreshold, leftThreshold, centreDistance);
  }

  public ObservableList<String> getLoadedObstaclesNames() {
    System.out.println(loadedObstaclesNames);
    return loadedObstaclesNames;
  }
}
