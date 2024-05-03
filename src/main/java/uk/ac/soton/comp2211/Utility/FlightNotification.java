package uk.ac.soton.comp2211.Utility;

// Notification.java
public class FlightNotification {
    private String recipient;
    private String message;

    public FlightNotification(String recipient, String message) {
        this.recipient = recipient;
        this.message = message;
    }

    public void send() {
        // Simulate sending the notification
        System.out.println("Notification sent to " + recipient + ": " + message);
    }
}