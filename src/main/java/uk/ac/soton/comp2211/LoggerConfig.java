package uk.ac.soton.comp2211;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfig {
  public static void configure() {
    try {
      File logFolder = new File("logs");
      if (!logFolder.exists()) {
        logFolder.mkdirs();
      }

      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
      String logFileName = dateFormat.format(new Date()) + ".log";
      String logFilePath = "logs" + File.separator + logFileName;

      // Create a file handler to log messages to a file
      FileHandler fileHandler = new FileHandler(logFilePath);

      // Optionally, set log formatter
      fileHandler.setFormatter(new SimpleFormatter());

      // Get the root logger and add the file handler
      Logger logger = Logger.getLogger("");
      logger.addHandler(fileHandler);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}