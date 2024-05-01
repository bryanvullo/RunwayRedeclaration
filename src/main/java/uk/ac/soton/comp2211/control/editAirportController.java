package uk.ac.soton.comp2211.control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uk.ac.soton.comp2211.component.RunwayBox;
import uk.ac.soton.comp2211.model.Airport;
import uk.ac.soton.comp2211.model.Runway;
import uk.ac.soton.comp2211.scene.MainScene;

public class editAirportController {

  @FXML
  private JFXListView<Airport> airportList;
  @FXML
  private JFXListView<Runway> runwayList;
  @FXML
  private JFXButton editButton1, deleteButton1, saveChangesButton1, cancelButton1, editButton2, deleteButton2, saveChangesButton2, cancelButton2, closeButton2, reloadButton2;
  @FXML
  private TextField airportNameField, runwayNameField, toraField, todaField, asdaField, ldaField, clearwayField, stopwayField, displacedThresholdField;
  @FXML
  private Label airportNameLabel, runwayNameLabel, toraLabel, todaLabel, asdaLabel, ldaLabel, clearwayLabel, stopwayLabel, displacedThresholdLabel;

  @FXML
  public void initialize() {
    airportList.setItems(RunwayBox.getAirportSelection().getItems());
    airportList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        runwayList.setItems(FXCollections.observableArrayList(newVal.getRunways())); // Correct conversion
        runwayList.getSelectionModel().selectFirst();
        airportNameLabel.setText(newVal.getAirportName());
      }
    });

    runwayList.setCellFactory(lv -> new ListCell<Runway>() {
      @Override
      protected void updateItem(Runway item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty || item == null ? null : item.getRunwayName());
      }
    });

    runwayList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        runwayNameLabel.setText(newVal.getRunwayName());
        toraLabel.setText(String.valueOf(newVal.getTora()));
        todaLabel.setText(String.valueOf(newVal.getToda()));
        asdaLabel.setText(String.valueOf(newVal.getAsda()));
        ldaLabel.setText(String.valueOf(newVal.getLda()));
        clearwayLabel.setText(String.valueOf(newVal.getClearway()));
        stopwayLabel.setText(String.valueOf(newVal.getStopway()));
        displacedThresholdLabel.setText(String.valueOf(newVal.getDisplacedThreshold()));
      }
    });

    initialiseFields(false);
    setupButtonActions();
  }

  private void setupButtonActions() {
    editButton1.setOnAction(e -> toggleAirportEdit(true));
    editButton2.setOnAction(e -> toggleRunwayEdit(true));

    cancelButton1.setOnAction(e -> toggleAirportEdit(false));
    cancelButton2.setOnAction(e -> toggleRunwayEdit(false));

    saveChangesButton1.setOnAction(e -> saveAirportChanges());
    saveChangesButton2.setOnAction(e -> saveRunwayChanges());

    deleteButton1.setOnAction(e -> deleteAirport());
    deleteButton2.setOnAction(e -> deleteRunway());

    closeButton2.setOnAction(e -> closeStage());
    reloadButton2.setOnAction(e -> reloadList());
  }

  public void reloadList() {
    airportList.refresh();
    runwayList.refresh();
    airportList.getSelectionModel().selectFirst();
    runwayList.getSelectionModel().selectFirst();
  }

  public void closeStage() {
    Stage stage = (Stage) closeButton2.getScene().getWindow();
    stage.close();
  }

  private void saveAirportChanges() {
    Airport selectedAirport = airportList.getSelectionModel().getSelectedItem();
    if (selectedAirport != null) {
      selectedAirport.setAirportName(airportNameField.getText());
    }

    airportList.refresh();
    toggleAirportEdit(false);

    reloadList();
  }

  private void deleteRunway() {
    Runway selectedRunway = runwayList.getSelectionModel().getSelectedItem();
    if (selectedRunway != null) {
      airportList.getSelectionModel().getSelectedItem().getRunways().remove(selectedRunway);
      runwayList.getItems().remove(selectedRunway);
    } else {
      showAlert("No airport selected", "Please select one Airport to continue this operation.");
    }
  }

  private void saveRunwayChanges() {
    Runway selectedRunway = runwayList.getSelectionModel().getSelectedItem();
    if (selectedRunway != null) {

      selectedRunway.setName(runwayNameField.getText());
      selectedRunway.setTora(Double.parseDouble(toraField.getText()));
      selectedRunway.setToda(Double.parseDouble(todaField.getText()));
      selectedRunway.setAsda(Double.parseDouble(asdaField.getText()));
      selectedRunway.setLda(Double.parseDouble(ldaField.getText()));
      selectedRunway.setClearway(Double.parseDouble(clearwayField.getText()));
      selectedRunway.setStopway(Double.parseDouble(stopwayField.getText()));
      selectedRunway.setDisplacedThreshold(Double.parseDouble(displacedThresholdField.getText()));

//      airportList.refresh();
      runwayList.refresh();
      runwayList.getSelectionModel().selectFirst();g
      toggleRunwayEdit(false);
    }
  }


  private void deleteAirport() {
    Airport selectedAirport = airportList.getSelectionModel().getSelectedItem();
    if (selectedAirport != null) {
      MainScene.getRunwayBox().getAirportSelection().getItems().remove(selectedAirport);
      airportList.getItems().remove(selectedAirport);
    } else {
      showAlert("No airport selected", "Please select one Airport to continue this operation.");
    }
  }

  private void toggleAirportEdit(boolean editing) {
    if (airportList.getSelectionModel().getSelectedItem() != null) {
      airportNameField.setVisible(editing);
      airportNameField.setText(airportNameLabel.getText());

      airportNameLabel.setVisible(!editing);

      saveChangesButton1.setVisible(editing);
      cancelButton1.setVisible(editing);
      editButton1.setVisible(!editing);
      deleteButton1.setVisible(!editing);
      closeButton2.setVisible(!editing);

      // Disable the lists
      airportList.setDisable(editing);
      runwayList.setDisable(editing);
    } else {
      showAlert("No airport selected", "Please select one AIRPORT to edit.");
    }
  }


  private void toggleRunwayEdit(boolean editing) {
    if (runwayList.getSelectionModel().getSelectedItem() != null) {
      runwayNameField.setVisible(editing);
      toraField.setVisible(editing);
      todaField.setVisible(editing);
      asdaField.setVisible(editing);
      ldaField.setVisible(editing);
      clearwayField.setVisible(editing);
      stopwayField.setVisible(editing);
      displacedThresholdField.setVisible(editing);

      runwayNameField.setText(runwayNameLabel.getText());
      toraField.setText(toraLabel.getText());
      todaField.setText(todaLabel.getText());
      asdaField.setText(asdaLabel.getText());
      ldaField.setText(ldaLabel.getText());
      clearwayField.setText(clearwayLabel.getText());
      stopwayField.setText(stopwayLabel.getText());
      displacedThresholdField.setText(displacedThresholdLabel.getText());

      runwayNameLabel.setVisible(!editing);
      toraLabel.setVisible(!editing);
      todaLabel.setVisible(!editing);
      asdaLabel.setVisible(!editing);
      ldaLabel.setVisible(!editing);
      clearwayLabel.setVisible(!editing);
      stopwayLabel.setVisible(!editing);
      displacedThresholdLabel.setVisible(!editing);

      editButton1.setVisible(!editing);
      deleteButton1.setVisible(!editing);
      editButton2.setVisible(!editing);
      deleteButton2.setVisible(!editing);

      saveChangesButton2.setVisible(editing);
      cancelButton2.setVisible(editing);
      closeButton2.setVisible(!editing);

      // Disable the lists
      airportList.setDisable(editing);
      runwayList.setDisable(editing);
    } else {
      showAlert("No runway selected", "Please select one runway to edit.");
    }
  }



  private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setContentText(content);
    alert.showAndWait();
  }

  public void initialiseFields(boolean editing) {
    airportList.getSelectionModel().selectFirst();
    runwayList.getSelectionModel().selectFirst();

    airportNameField.setVisible(editing);
    runwayNameField.setVisible(editing);
    toraField.setVisible(editing);
    todaField.setVisible(editing);
    asdaField.setVisible(editing);
    ldaField.setVisible(editing);
    clearwayField.setVisible(editing);
    stopwayField.setVisible(editing);
    displacedThresholdField.setVisible(editing);

    saveChangesButton1.setVisible(editing);
    cancelButton1.setVisible(editing);
    saveChangesButton2.setVisible(editing);
    cancelButton2.setVisible(editing);
  }
}
