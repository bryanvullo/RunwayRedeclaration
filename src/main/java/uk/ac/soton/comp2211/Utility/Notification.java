// Notification.java
package uk.ac.soton.comp2211.Utility;

/**
 * Represents a system notification.
 */
public class Notification {
    private String date;
    private String time;
    private String message;

    public Notification(String date, String time, String message) {
        this.date = date;
        this.time = time;
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }
}
