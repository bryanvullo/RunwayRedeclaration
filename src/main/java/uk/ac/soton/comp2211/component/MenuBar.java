package uk.ac.soton.comp2211.component;

import javafx.geometry.Insets;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
    
    private MenuButton fileButton;
    private MenuButton editButton;
    private MenuButton viewButton;
    private MenuButton settingsButton;
    private MenuButton helpButton;
    private MenuButton userButton;
    
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
        fileButton = new MenuButton("File");
        editButton = new MenuButton("Edit");
        viewButton = new MenuButton("View");
        settingsButton = new MenuButton("Settings");
        helpButton = new MenuButton("Help");
        
//        toolbar.getItems().addAll(fileButton, editButton, viewButton, settingsButton, helpButton);
//        getChildren().add(toolbar);
        getChildren().addAll(fileButton, editButton, viewButton, settingsButton, helpButton);
        
        var filler = new HBox();
        getChildren().add(filler);
        setHgrow(filler, Priority.ALWAYS);
        
        userButton = new MenuButton();
        var userImage = new ImageView(getClass().getResource("/img/user.png").toExternalForm());
        userImage.setFitWidth(20);
        userImage.setFitHeight(20);
        userButton.setGraphic(userImage);
        userButton.getItems().addAll(
            new MenuItem("Username"),
            new MenuItem("Access Level"),
            new MenuItem("Logout"));
        
        getChildren().add(userButton);
    }
    
    public MenuButton fileButton() {
        return fileButton;
    }
    
    public MenuButton editButton() {
        return editButton;
    }
    
    public MenuButton viewButton() {
        return viewButton;
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
