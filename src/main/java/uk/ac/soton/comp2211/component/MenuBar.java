package uk.ac.soton.comp2211.component;

import javafx.scene.control.MenuButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
        
        var toolbar = new ToolBar();
        var fileButton = new MenuButton("File");
        var editButton = new MenuButton("Edit");
        var viewButton = new MenuButton("View");
        var settingsButton = new MenuButton("Settings");
        var helpButton = new MenuButton("Help");
        
        toolbar.getItems().addAll(fileButton, editButton, viewButton, settingsButton, helpButton);
        getChildren().add(toolbar);
        
        var filler = new HBox();
        getChildren().add(filler);
        setHgrow(filler, Priority.ALWAYS);
        
        var userButton = new MenuButton("User");
        getChildren().add(userButton);
    }
    
}
