module uk.ac.soton.comp2211 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    
    opens uk.ac.soton.comp2211 to javafx.fxml;
    exports uk.ac.soton.comp2211;
    exports uk.ac.soton.comp2211.model;
}
