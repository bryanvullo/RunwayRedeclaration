package uk.ac.soton.comp2211.component;

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
        
        var title = new Text("System Messages");
        getChildren().add(title);
        
        var messageBox = new VBox();
        getChildren().add(messageBox);
    }
    
}
