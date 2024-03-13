package uk.ac.soton.comp2211.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SystemMessageBox extends VBox {
    
    private static final Logger logger = LogManager.getLogger(SystemMessageBox.class);
    
    public SystemMessageBox() {
        logger.info("Creating System Message Box");
        
        build();
    }
    
    private void build() {
        logger.info("Building System Message Box");
        setAlignment(Pos.TOP_CENTER);
        setSpacing(10);
        setPadding(new Insets(20));
        
        var title = new Text("System Messages");
        getChildren().add(title);
        
        var messageBox = new ScrollPane();
        getChildren().add(messageBox);
        VBox.setVgrow(messageBox, Priority.ALWAYS);
    }
    
}
