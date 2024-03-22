module uk.ac.soton.comp2211 {
  requires javafx.controls;
  requires javafx.fxml;
  requires org.apache.logging.log4j;
  requires java.sql;
  requires org.mongodb.driver.sync.client;
  requires org.mongodb.driver.core;
  requires org.mongodb.bson;
  requires java.logging;
  requires com.jfoenix;
  requires spring.security.crypto;
  requires fontawesomefx;

  opens uk.ac.soton.comp2211 to javafx.fxml;
  opens uk.ac.soton.comp2211.control to javafx.fxml;
  exports uk.ac.soton.comp2211;
  exports uk.ac.soton.comp2211.model;
  exports uk.ac.soton.comp2211.control;
  exports uk.ac.soton.comp2211.model.obstacles;
}
