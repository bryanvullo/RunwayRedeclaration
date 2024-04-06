//package uk.ac.soton.comp2211.Utility;
//
//
//import javafx.application.Platform;
//import uk.ac.soton.comp2211.control.TopDownViewController;
//import uk.ac.soton.comp2211.dataStructure.Notification;
//
///**
// * Handles creating and dispatching notifications to the UI.
// */
//public class NotificationService {
//    private TopDownViewController controller;
//
//    public NotificationService(TopDownViewController controller) {
//        this.controller = controller;
//    }
//
//    public void notifyEvent(String date, String time, String message) {
//        Notification notification = new Notification(date, time, message);
//        // Dispatch notification to the UI thread
//        Platform.runLater(() -> controller.addNotification(notification));
//    }
//}
