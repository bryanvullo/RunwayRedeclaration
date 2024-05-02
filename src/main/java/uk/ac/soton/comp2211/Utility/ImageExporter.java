package uk.ac.soton.comp2211.utility;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import uk.ac.soton.comp2211.component.RunwayBox;
import uk.ac.soton.comp2211.component.RunwayViewBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageExporter {

  public static void captureRunwayViewBoxScreenshot(RunwayViewBox runwayViewBox) {
    if (runwayViewBox != null) {
      SnapshotParameters params = new SnapshotParameters();
      WritableImage image = runwayViewBox.snapshot(params, null);

      File file = new File("runwayViewBoxScreenshot.png");
      saveImage(image, file);
    } else {
      System.out.println("Runway view box is not available.");
    }
  }

  private static void saveImage(WritableImage image, File file) {
    try (OutputStream out = new FileOutputStream(file)) {
      BufferedImage bImage = fromFXImage(image, null);
      ImageIO.write(bImage, "png", out);
      System.out.println("Screenshot saved: " + file.getAbsolutePath());
    } catch (IOException e) {
      System.err.println("Failed to save screenshot: " + e.getMessage());
    }
  }



  private static BufferedImage fromFXImage(WritableImage image, BufferedImage bImg) {
    int width = (int) image.getWidth();
    int height = (int) image.getHeight();
    PixelReader reader = image.getPixelReader();

    // Create a new BufferedImage with TYPE_INT_ARGB to match JavaFX ARGB format
    BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    // Getting the pixel data from the image
    int[] buffer = new int[width * height];
    reader.getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), buffer, 0, width);

    // Setting the pixels of the BufferedImage
    bufImage.setRGB(0, 0, width, height, buffer, 0, width);

    return bufImage;
  }

}
