//package uk.ac.soton.comp2211.Utility;
//
//
//import javafx.application.Platform;
//import uk.ac.soton.comp2211.control.TopDownViewController;
//import uk.ac.soton.comp2211.dataStructure.ErrorNotification;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
///**
// * Utility class for logging messages to the UI.
// */
//public class LoggerUtil {
//
//    private static TopDownViewController controller;
//
//    // DateTimeFormatter to format the timestamp for log messages
//    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//
//    public static void setController(TopDownViewController controller) {
//        LoggerUtil.controller = controller;
//    }
//
//    public static void logInfo(String message) {
//        String timestamp = LocalDateTime.now().format(formatter);
//        // Dispatch the log message to the UI thread
//        Platform.runLater(() -> {
//            if (controller != null) {
//                controller.addNotification(timestamp, "INFO", message);
//            }
//        });
//    }
//
//    public static void logError(String message) {
//        String timestamp = LocalDateTime.now().format(formatter);
//        // Dispatch the error message to the UI thread
//        Platform.runLater(() -> {
//            if (controller != null) {
//                controller.addErrorNotification(new ErrorNotification(timestamp, message));
//            }
//        });
//    }
//}