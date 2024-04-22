package uk.ac.soton.comp2211.control;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Notification {
  public static void showNotification(String message) {
    Stage stage = new Stage();
    stage.initStyle(StageStyle.TRANSPARENT);

    Label label = new Label(message);
    label.getStyleClass().add("notification-label");
    label.setTextFill(Color.WHITE);
    label.setStyle("-fx-font-size: 16px;"); // Set font size as needed

    StackPane root = new StackPane(label);
    root.getStyleClass().add("notification-pane");
    root.setStyle("-fx-background-color: rgba(60, 179, 113, 0.7); -fx-padding: 20px; -fx-border-radius: 10px;");

    Scene scene = new Scene(root);
    scene.setFill(Color.TRANSPARENT);

    stage.setScene(scene);
    stage.setAlwaysOnTop(true);

    double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
    double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
    root.setAlignment(Pos.BOTTOM_RIGHT);

    // Fade out animation
    Timeline timeline = new Timeline(
        new KeyFrame(Duration.seconds(2),
            new KeyValue(stage.getScene().getRoot().opacityProperty(), 0))
    );
    timeline.setDelay(Duration.seconds(2));
    timeline.setOnFinished(event -> stage.close());
    timeline.play();

    stage.show();
  }
}

