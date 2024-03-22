package uk.ac.soton.comp2211.Utility;


import javafx.application.Platform;
import uk.ac.soton.comp2211.control.TopDownViewController;
import uk.ac.soton.comp2211.dataStructure.ErrorNotification;

/**
 * Handles creating and dispatching error notifications to the UI.
 */
public class ErrorNotificationService {
    private TopDownViewController controller;

    public ErrorNotificationService(TopDownViewController controller) {
        this.controller = controller;
    }

    public void notifyError(String timestamp, String errorMessage) {
        ErrorNotification errorNotification = new ErrorNotification(timestamp, errorMessage);
        // Dispatch the error notification to the UI thread
        Platform.runLater(() -> controller.addErrorNotification(errorNotification));
    }
}
