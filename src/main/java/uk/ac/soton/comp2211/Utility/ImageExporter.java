package uk.ac.soton.comp2211.Utility;

import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.embed.swing.SwingFXUtils;
import uk.ac.soton.comp2211.component.RunwayViewBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageExporter {

  public static void exportViewAsImage(Node view, String format) {
    if (view != null) {
      SnapshotParameters params = new SnapshotParameters();
      WritableImage image = view.snapshot(params, null);

      FileChooser fileChooser = new FileChooser();
      fileChooser.setInitialFileName("runway_view." + format);
      fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(format.toUpperCase(), "*." + format));
      File file = fileChooser.showSaveDialog(null);

      if (file != null) {
        saveImage(image, file, format);
      }
    } else {
      System.out.println("View is not available for snapshot.");
    }
  }

  private static void saveImage(WritableImage image, File file, String format) {
    try {
      BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
      ImageIO.write(bImage, format, file);
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
