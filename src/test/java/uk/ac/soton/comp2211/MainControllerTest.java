//package uk.ac.soton.comp2211;
//
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
//import javafx.scene.control.MenuItem;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import uk.ac.soton.comp2211.Utility.ErrorNotificationService;
//import uk.ac.soton.comp2211.control.MainController;
//
//public class MainControllerTest {
//
//    private ErrorNotificationService service;
//    private MainController controller;
//    @Mock
//    private MenuItem mockMenuItem;
//    @Mock
//    private ActionEvent mockActionEvent;
//
//    @BeforeEach
//    void setUp() {
//        controller = Mockito.mock(MainController.class);
//    }
//
//    @BeforeAll
//    public static void setUpClass() {
//        Platform.startup(() -> {});
//    }
//
//    @Test
//    void testNotifyError() throws InterruptedException {
//        controller.switchColorScheme(mockActionEvent);
//    }
//}