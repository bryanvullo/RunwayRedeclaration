package uk.ac.soton.comp2211.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SystemMessageBox extends VBox {
    
    private static final Logger logger = LogManager.getLogger(SystemMessageBox.class);
    
    private TextFlow messageBox;
    
    public SystemMessageBox() {
        logger.info("Creating System Message Box");
        
        build();
    }
    
    private void build() {
        logger.info("Building System Message Box");
        setAlignment(Pos.TOP_CENTER);
        setSpacing(10);
        setPadding(new Insets(20));
        setBorder(new Border((new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT))));
        
        var title = new Text("System Messages");
        title.getStyleClass().add("componentTitle");
        getChildren().add(title);
        
        var scrollBox = new ScrollPane();
        setVgrow(scrollBox, Priority.ALWAYS);
        getChildren().add(scrollBox);
        VBox.setVgrow(scrollBox, Priority.ALWAYS);
        
        messageBox = new TextFlow();
        scrollBox.setContent(messageBox);
    }
    
    public void addMessage(String message) {
        logger.info("Adding message to System Message Box");
        var text = new Text(message + "\n");
        messageBox.getChildren().add(text);
    }
}
