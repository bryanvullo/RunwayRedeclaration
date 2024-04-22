package uk.ac.soton.comp2211.control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.ac.soton.comp2211.component.RunwayBox;
import uk.ac.soton.comp2211.model.Airport;
import uk.ac.soton.comp2211.model.Runway;
import uk.ac.soton.comp2211.scene.MainScene;

import java.io.File;
import java.util.Iterator;
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
  private JFXListView<Airport> runwayListView;




  private static File selectedFile; // Store selected file
  private ObservableList<Airport> airports = FXCollections.observableArrayList();

  @FXML
  private void initialize() {
    selectFileButton2.setOnAction(event -> selectFile());
    importButton2.setOnAction(event -> loadAirportsFromFile());
    runwayListView.setItems(airports); // Set the ListView's items to the observable list
  }


  public void loadAirportsFromFile() {
    if (selectedFile == null) {
      showAlert("No File Selected", "Please select a file before importing.");
      return;
    }
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      dbFactory.setNamespaceAware(true); // enable namespaces
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(selectedFile);
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

          RunwayBox.getAirportSelection().getItems().add(airport);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      showAlert("Error Loading Airports", "Failed to load airports from file: " + e.getMessage());
    }
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
    if (file != null) {
      selectedFile = file; // Store the file
    }
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
