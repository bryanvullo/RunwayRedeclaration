package uk.ac.soton.comp2211;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class MediaTest extends Application {

  @Override
  public void start(Stage primaryStage) {
    String uri = getClass().getResource("/path/to/your/video.mp4").toExternalForm();
    Media media = new Media(uri);
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    MediaView mediaView = new MediaView(mediaPlayer);

    mediaPlayer.play();

    StackPane root = new StackPane();
    root.getChildren().add(mediaView);
    Scene scene = new Scene(root, 640, 480);
    primaryStage.setTitle("Media Test");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
