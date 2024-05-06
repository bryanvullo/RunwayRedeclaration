  package uk.ac.soton.comp2211.component;

  import javafx.event.ActionEvent;
  import javafx.fxml.FXMLLoader;
  import javafx.geometry.Insets;
  import javafx.scene.Node;
  import javafx.scene.Parent;
  import javafx.scene.Scene;
  import javafx.scene.control.Menu;
  import javafx.scene.control.MenuButton;
  import javafx.scene.control.MenuItem;
  import javafx.scene.image.ImageView;
  import javafx.scene.layout.*;
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
  import java.util.HashMap;
  import java.util.Map;
  import java.util.Objects;

  public class MenuBar extends HBox {

    private static final Logger logger = LogManager.getLogger(MenuBar.class);
    private boolean isDarkMode = false; // defalt
    private Color originalColor = Color.valueOf("73a9c2"); // Store the original color here
    private Map<Node, Color> originalColors = new HashMap<>(); // Map to store original colors of nodes

    private MenuButton fileButton;
    private MenuButton editButton;
    private MenuButton settingsButton;
    private MenuButton helpButton;
    private MenuButton userButton;
    private MenuButton unitButton;

    public MenuBar() {
      logger.info("Creating the MenuBar");
      build();
      applyStyles();
    }

    public void build() {
      logger.info("Building the MenuBar");
      setSpacing(10);
      setPadding(new Insets(10));
      setBackground(new Background(new BackgroundFill(originalColor, null, null)));

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
            PDFExporter.exportRunwayViews(MainScene.getRunwayViewBox().getTopDownRunway(), MainScene.getRunwayViewBox().getSideRunway(), RunwayBox.getAirportSelection().getSelectionModel().getSelectedItem().getAirportName(), selectedRunway.getName(), selectedRunway, file.getPath());
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
          new MenuItem("Change Font"),
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

      MenuItem changeColourScheme = new MenuItem("Change Colour Scheme");
      changeColourScheme.setOnAction(event -> handleChangeColourScheme());
      settingsButton.getItems().add(changeColourScheme);

      MenuItem LightDark = new MenuItem("Light/Dark Mode");
      LightDark.setOnAction(event -> handleChangeDarkScheme());
      settingsButton.getItems().add(LightDark);

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

    private void applyStyles() {
      String cssPath = "/css/1.css"; // 确保这个路径与你的文件结构相匹配

      if (this.getScene() != null) {

        this.getScene().getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
      } else {
        // 如果场景尚未可用，添加一个监听器到 scene 属性
        this.sceneProperty().addListener((observable, oldScene, newScene) -> {
          // 当场景变为可用时，添加样式表
          if (newScene != null) {
            newScene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
          }
        });
      }
    }

    private void exportAirports() {
      XMLExporter.exportAirports();
    }
    private void changeBackgroundAndTextColorRecursively(Parent root, Color backgroundColor, Color textColor) {
      if (root instanceof Region) {
        Region region = (Region) root;
        region.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
        region.setStyle("-fx-text-fill: " + toHexString(textColor) + ";");
      }
      if (root instanceof Parent) {
        for (Node node : root.getChildrenUnmodifiable()) {
          if (node instanceof Parent && !(node instanceof RunwayViewBox)) {
            changeBackgroundAndTextColorRecursively((Parent) node, backgroundColor, textColor);
          }
        }
      }
    }

    // Helper method to convert Color to hex string
    private String toHexString(Color color) {
      return String.format("#%02X%02X%02X",
              (int)(color.getRed() * 255),
              (int)(color.getGreen() * 255),
              (int)(color.getBlue() * 255));
    }
    private void applyThemeRecursively(Parent root, String themeClass) {
      // 移除所有已知主题样式类
      if (!(root instanceof RunwayViewBox)) {
        root.getStyleClass().removeAll("dark-theme", "yellow-theme", "default-theme");
        // 添加新的主题样式类
        root.getStyleClass().add(themeClass);
      }

      // 遍历所有子节点，并递归调用此方法
      for (Node node : root.getChildrenUnmodifiable()) {
        if (node instanceof Parent && !(node instanceof RunwayViewBox)) {
          applyThemeRecursively((Parent) node, themeClass);
        }
      }
    }
    private void handleChangeDarkScheme() {
      System.out.println("Toggling dark mode.");
      Scene scene = settingsButton.getScene();
      if (scene != null) {
        clearAllThemes(scene);
        if(!isDarkMode){
          clearAllThemes(scene);
          applyThemeRecursively((Parent) scene.getRoot(), "dark-theme");
        }else {
          clearAllThemes(scene);
          applyThemeRecursively((Parent) scene.getRoot(), "default-theme");

        }
        System.out.println("Switched to dark mode.");
      } else {
        System.out.println("No scene found.");
      }
    }

    private void handleChangeColourScheme() {
      System.out.println("Changing colour scheme to yellow.");
      Scene scene = settingsButton.getScene();
      if (scene != null) {
        clearAllThemes(scene);
        applyThemeRecursively((Parent) scene.getRoot(), "yellow-theme");
        System.out.println("Background changed to yellow across the entire application.");
      } else {
        System.out.println("No scene found.");
      }
    }

    private void clearAllThemes(Scene scene) {
      // 移除所有可能的主题类
      scene.getRoot().getStyleClass().removeAll("dark-theme", "yellow-theme", "default-theme");
    }


//    private void handleChangeDarkScheme() {
//      System.out.println("Toggling light/dark mode.");
//      Scene scene = settingsButton.getScene();
//      if (scene != null) {
//        isDarkMode = !isDarkMode;
//        Color backgroundColor = isDarkMode ? Color.DARKGRAY : originalColor;
//        Color textColor = isDarkMode ? Color.WHITE : Color.BLACK; // White text on dark background, black text on light background
//
//        changeBackgroundAndTextColorRecursively(scene.getRoot(), backgroundColor, textColor);
//        System.out.println(isDarkMode ? "Switched to dark mode." : "Switched to light mode.");
//      } else {
//        System.out.println("No scene found.");
//      }
//    }
//
//    private void handleChangeColourScheme() {
//      System.out.println("Changing colour scheme to yellow.");
//      Scene scene = settingsButton.getScene();
//      if (scene != null) {
//        changeBackgroundAndTextColorRecursively(scene.getRoot(), Color.YELLOW, Color.BLACK); // Yellow background with black text
//        System.out.println("Background changed to yellow across the entire application.");
//      } else {
//        System.out.println("No scene found.");
//      }
//    }

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