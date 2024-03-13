package uk.ac.soton.comp2211.component;

import javafx.geometry.Insets;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MenuBar extends HBox {
    
    private static final Logger logger = LogManager.getLogger(MenuBar.class);
    
    public MenuBar() {
        logger.info("Creating the MenuBar");
        build();
    }
    
    public void build() {
        logger.info("Building the MenuBar");
        setSpacing(10);
        setPadding(new Insets(10));
        setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
        
//        var toolbar = new ToolBar();
//        toolbar.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
        var fileButton = new MenuButton("File");
        var editButton = new MenuButton("Edit");
        var viewButton = new MenuButton("View");
        var settingsButton = new MenuButton("Settings");
        var helpButton = new MenuButton("Help");
        
//        toolbar.getItems().addAll(fileButton, editButton, viewButton, settingsButton, helpButton);
//        getChildren().add(toolbar);
        getChildren().addAll(fileButton, editButton, viewButton, settingsButton, helpButton);
        
        var filler = new HBox();
        getChildren().add(filler);
        setHgrow(filler, Priority.ALWAYS);
        
        var userButton = new MenuButton();
        var userImage = new ImageView(getClass().getResource("/img/user.png").toExternalForm());
        userImage.setFitWidth(20);
        userImage.setFitHeight(20);
        userButton.setGraphic(userImage);
        getChildren().add(userButton);
    }
    
}
