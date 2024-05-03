// ErrorNotification.java
package uk.ac.soton.comp2211.Utility;

/**
 * Represents an error notification.
 */
public class ErrorNotification {
    private String timestamp;
    private String errorMessage;

    public ErrorNotification(String timestamp, String errorMessage) {
        this.timestamp = timestamp;
        this.errorMessage = errorMessage;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
