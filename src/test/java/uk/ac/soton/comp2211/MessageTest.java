package uk.ac.soton.comp2211;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import uk.ac.soton.comp2211.Utility.ErrorNotificationService;
import uk.ac.soton.comp2211.control.TopDownViewController;
import uk.ac.soton.comp2211.dataStructure.ErrorNotification;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class MessageTest {
    private ErrorNotificationService service;
    private TopDownViewController controller;

    @BeforeEach
    void setUp() {
        controller = Mockito.mock(TopDownViewController.class);
        service = new ErrorNotificationService(controller);
    }

    @BeforeAll
    public static void setUpClass() {
        Platform.startup(() -> {});
    }

    @Test
    void testNotifyError() throws InterruptedException {
        service.notifyError("2024-10-10 12:00", "Test Error Message");
        Thread.sleep(100);
        verify(controller).addErrorNotification(any(ErrorNotification.class));
    }
}
