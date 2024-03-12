package uk.ac.soton.comp2211.control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import uk.ac.soton.comp2211.Utility.DBUtils;
import uk.ac.soton.comp2211.model.obstacles.Obstacle;
import uk.ac.soton.comp2211.model.Tool;

public class MainController implements Initializable {

  private Tool tool;

  @FXML
  private Button recalculateButton;
  @FXML
  private Button buttonLogOut;
  @FXML
  private Button saveButton;
  @FXML
  private Button editButton;
  @FXML
  private Button removeButton;
  @FXML
  private Button addButton;

  @FXML
  private Text revisedToraText, revisedTodaText, revisedAsdaText, revisedLdaText;

  @FXML
  private TextField rThresholdField;
  @FXML
  private TextField lThresholdField, heightField;
  @FXML
  private TextField centerlineField;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    tool = new Tool();

    revisedToraText.textProperty().bind(tool.tora.asString());
    revisedTodaText.textProperty().bind(tool.toda.asString());
    revisedAsdaText.textProperty().bind(tool.asda.asString());
    revisedLdaText.textProperty().bind(tool.lda.asString());

    recalculateButton.setOnAction(this::recalculate);
    saveButton.setOnAction(this::save);
    editButton.setOnAction(this::editObstacle);
    removeButton.setOnAction(this::removeObstacle);
    addButton.setOnAction(this::addObstacle);
    buttonLogOut.setOnAction(this::logout);
  }

  public void recalculate(ActionEvent e) {
    var lthreshold = Integer.parseInt(lThresholdField.getText());

    var height = Integer.parseInt(heightField.getText());

    var obstacle = new Obstacle(height, lthreshold);

    tool.recalculate(obstacle);
  }

  public void save(ActionEvent e) {
    System.out.println("Saving");
  }

    public void logout(ActionEvent e) {
      System.out.println("Logging out");
      DBUtils.changeScene(e, "/fxml/mainPage.fxml", "Login", null, null);
    }

  public void editObstacle(ActionEvent e) {
    System.out.println("Editing obstacle");
  }

  public void addObstacle(ActionEvent e) {
    System.out.println("Adding obstacle");
  }

  public void removeObstacle(ActionEvent e) {
    System.out.println("Removing obstacle");
  }
}
