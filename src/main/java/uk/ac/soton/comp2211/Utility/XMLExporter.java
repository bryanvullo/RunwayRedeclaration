package uk.ac.soton.comp2211.Utility;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import uk.ac.soton.comp2211.component.RunwayBox;
import uk.ac.soton.comp2211.model.Airport;
import uk.ac.soton.comp2211.model.Runway;
import uk.ac.soton.comp2211.model.obstacles.AdvancedObstacle;
import uk.ac.soton.comp2211.scene.MainScene;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLExporter {
  public static void exportObstacles() {
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
      File file = fileChooser.showSaveDialog(new Stage());

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

  public static void exportAirports() {
    try {
      DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

      Document document = documentBuilder.newDocument();
      Element root = document.createElementNS("http://www.github.com/bryanvullo/RunwayRedeclaration", "airports");
      root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
      root.setAttribute("xsi:schemaLocation", "http://www.github.com/bryanvullo/RunwayRedeclaration newAirportSchema.xsd");
      document.appendChild(root);

      for (Airport airport : RunwayBox.getAirportSelection().getItems()) {
        Element airportElement = document.createElement("airport");
        root.appendChild(airportElement);

        createElement(airportElement, "name", airport.getAirportName());

        Element runwaysElement = document.createElement("runways");
        airportElement.appendChild(runwaysElement);

        for (Runway runway : airport.getRunways()) {
          Element runwayElement = document.createElement("runway");
          runwaysElement.appendChild(runwayElement);

          createElement(runwayElement, "name", runway.getRunwayName());
          createElement(runwayElement, "tora", String.valueOf(runway.getTora()));
          createElement(runwayElement, "toda", String.valueOf(runway.getToda()));
          createElement(runwayElement, "asda", String.valueOf(runway.getAsda()));
          createElement(runwayElement, "lda", String.valueOf(runway.getLda()));
          createElement(runwayElement, "clearway", String.valueOf(runway.getClearway()));
          createElement(runwayElement, "stopway", String.valueOf(runway.getStopway()));
          createElement(runwayElement, "displacedThreshold", String.valueOf(runway.getDisplacedThreshold()));
        }
      }

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

      DOMSource domSource = new DOMSource(document);

      FileChooser fileChooser = new FileChooser();
      fileChooser.setInitialFileName("exported_airports.xml");
      File file = fileChooser.showSaveDialog(new Stage());

      if (file != null) {
        StreamResult streamResult = new StreamResult(file);
        transformer.transform(domSource, streamResult);
        System.out.println("Airports exported successfully to " + file.getAbsolutePath());
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Failed to export airports");
    }
  }

  private static void createElement(Element parent, String name, String value) {
    Element element = parent.getOwnerDocument().createElement(name);
    element.appendChild(parent.getOwnerDocument().createTextNode(value));
    parent.appendChild(element);
  }
}
