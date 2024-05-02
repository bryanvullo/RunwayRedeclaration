package uk.ac.soton.comp2211.control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.ac.soton.comp2211.component.RunwayBox;
import uk.ac.soton.comp2211.component.SystemMessageBox;
import uk.ac.soton.comp2211.model.Airport;
import uk.ac.soton.comp2211.model.Runway;
import uk.ac.soton.comp2211.scene.MainScene;

import java.io.File;
import java.util.Iterator;
import java.util.Optional;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class ImportAirportController {

  @FXML
  private JFXButton selectFileButton2;
  @FXML
  private JFXButton importButton2;
  @FXML
  private JFXListView<Airport> airportListView;
  private ObservableList<Airport> airports = FXCollections.observableArrayList();

  @FXML
  private void initialize() {
    selectFileButton2.setOnAction(event -> selectFile());
    importButton2.setOnAction(event -> importAirports());
    airportListView.setItems(airports); // Set the ListView's items to the observable list
  }
  private void importAirports() {
    ObservableList<Airport> mainAirports = RunwayBox.getAirportSelection().getItems();
    for (Airport previewAirport : airports) {
      Optional<ButtonType> userChoice = isDuplicate(previewAirport.getAirportName()) ?
          askUserAboutDuplicate(previewAirport.getAirportName()) : Optional.empty();

      if (userChoice.isPresent()) {
        if (userChoice.get().getText().equals("Replace")) {
          // Replace the existing airport
          mainAirports.removeIf(a -> a.getAirportName().equals(previewAirport.getAirportName()));
          mainAirports.add(previewAirport);
        } else if (userChoice.get().getText().equals("Add Anyway")) {
          // Add the airport anyway (handling it as a separate entry)
          Airport copyAirport = new Airport(previewAirport.getAirportName() + " (Copy)");
          copyAirport.getRunways().addAll(previewAirport.getRunways());
          mainAirports.add(copyAirport);
        }
        // If user chooses "Skip", do nothing
      } else {
        // No duplicate found or user cancelled, just add the airport
        mainAirports.add(previewAirport);
      }
    }
    SystemMessageBox.addMessage("Imported " + airports.size() + " airports");
    Stage stage = (Stage) importButton2.getScene().getWindow();
    stage.close();
  }



  private void loadAirportsFromFile(File file) {
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      dbFactory.setNamespaceAware(true);
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(file);
      doc.getDocumentElement().normalize();

      XPathFactory xpathFactory = XPathFactory.newInstance();
      XPath xpath = xpathFactory.newXPath();
      xpath.setNamespaceContext(new NamespaceContext() {
        public String getNamespaceURI(String prefix) {
          return prefix.equals("def") ? "http://www.github.com/bryanvullo/RunwayRedeclaration" : XMLConstants.NULL_NS_URI;
        }
        public Iterator getPrefixes(String val) { return null; }
        public String getPrefix(String uri) { return null; }
      });

      NodeList airportList = (NodeList) xpath.evaluate("/def:airports/def:airport", doc, XPathConstants.NODESET);
      for (int i = 0; i < airportList.getLength(); i++) {
        Node node = airportList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) node;
          String name = xpath.evaluate("def:name", element);
          Airport airport = new Airport(name);
          NodeList runwayList = (NodeList) xpath.evaluate("def:runways/def:runway", element, XPathConstants.NODESET);
          for (int j = 0; j < runwayList.getLength(); j++) {
            Element relement = (Element) runwayList.item(j);
            Runway runway = parseRunway(relement, xpath);
            airport.addRunway(runway);
          }
          airports.add(airport);
        }
      }
      Platform.runLater(() -> {
        airportListView.setItems(airports);
      });
    } catch (Exception e) {
      e.printStackTrace();
      showAlert("Error Loading Airports", "Failed to load airports from file: " + e.getMessage());
    }
  }

  private boolean isDuplicate(String airportName) {
    return RunwayBox.getAirportSelection().getItems().stream().anyMatch(a -> a.getAirportName().equals(airportName));
  }

  private Optional<ButtonType> askUserAboutDuplicate(String airportName) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Duplicate Airport Found");
    alert.setHeaderText("An airport with the name '" + airportName + "' already exists.");
    alert.setContentText("Choose an action:");

    ButtonType buttonTypeReplace = new ButtonType("Replace");
    ButtonType buttonTypeSkip = new ButtonType("Skip");
    ButtonType buttonTypeAddAnyway = new ButtonType("Add Anyway");

    alert.getButtonTypes().setAll(buttonTypeReplace, buttonTypeSkip, buttonTypeAddAnyway, ButtonType.CANCEL);

    return alert.showAndWait();
  }


  public static void loadInitialeAirports() {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true); // Correctly handle namespaces
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(ImportAirportController.class.getResourceAsStream("/xml/newAirports.xml"));
      doc.getDocumentElement().normalize();

      XPathFactory xpathFactory = XPathFactory.newInstance();
      XPath xpath = xpathFactory.newXPath();
      xpath.setNamespaceContext(new NamespaceContext() {
        public String getNamespaceURI(String prefix) {
          if (prefix == null) throw new IllegalArgumentException("No prefix provided!");
          return prefix.equals("def") ? "http://www.github.com/bryanvullo/RunwayRedeclaration" : XMLConstants.NULL_NS_URI;
        }

        public Iterator getPrefixes(String val) {
          return null;
        }

        public String getPrefix(String uri) {
          return null;
        }
      });

      NodeList airportList = (NodeList) xpath.evaluate("/def:airports/def:airport", doc, XPathConstants.NODESET);
//      airports.clear(); // Clear existing airports before loading new ones
      for (int i = 0; i < airportList.getLength(); i++) {
        Node node = airportList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) node;
          String name = xpath.evaluate("def:name", element);
          Airport airport = new Airport(name);
          NodeList runwayList = (NodeList) xpath.evaluate("def:runways/def:runway", element, XPathConstants.NODESET);
          for (int j = 0; j < runwayList.getLength(); j++) {
            Node rnode = runwayList.item(j);
            Element relement = (Element) rnode;
            Runway runway = parseRunway(relement, xpath);
            airport.addRunway(runway);
          }
          System.out.println(airport.getAirportName());
          RunwayBox.getAirportSelection().getItems().add(airport);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      showAlert("Error Loading Airports", "Failed to load airports from file: " + e.getMessage());
    }
  }

  private static void showAlert(String header, String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
  }

  public void selectFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Airport File");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("XML Files", "*.xml")
    );
    File file = fileChooser.showOpenDialog(selectFileButton2.getScene().getWindow());
    loadAirportsFromFile(file);
  }


  private static Runway parseRunway(Element element, XPath xpath) throws Exception {
    String name = xpath.evaluate("def:name", element);
    double tora = Double.parseDouble(xpath.evaluate("def:tora", element));
    double toda = Double.parseDouble(xpath.evaluate("def:toda", element));
    double asda = Double.parseDouble(xpath.evaluate("def:asda", element));
    double lda = Double.parseDouble(xpath.evaluate("def:lda", element));
    double clearway = Double.parseDouble(xpath.evaluate("def:clearway", element));
    double stopway = Double.parseDouble(xpath.evaluate("def:stopway", element));
    double displacedThreshold = Double.parseDouble(xpath.evaluate("def:displacedThreshold", element));
    Runway runway = new Runway(name, tora, toda, asda, lda);
    runway.setClearway(clearway);
    runway.setStopway(stopway);
    runway.setDisplacedThreshold(displacedThreshold);
    return runway;
  }

}