package uk.ac.soton.comp2211.Utility;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import uk.ac.soton.comp2211.model.Runway;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class PDFExporter {

  public static void exportRunwayViews(StackPane topDownView, StackPane sideView, String airportName, String runwayName, Runway runway, String fileName) {
    Platform.runLater(() -> {
      PDDocument document = new PDDocument();
      try {
        File file = new File(fileName);
        if (!file.getParentFile().exists()) {
          file.getParentFile().mkdirs();
        }

        SnapshotParameters params = new SnapshotParameters();
        WritableImage topImage = topDownView.snapshot(params, null);
        BufferedImage bTopImage = SwingFXUtils.fromFXImage(topImage, null);
        ByteArrayOutputStream topOut = new ByteArrayOutputStream();
        ImageIO.write(bTopImage, "PNG", topOut);
        PDImageXObject topPdfImage = PDImageXObject.createFromByteArray(document, topOut.toByteArray(), "top_down.png");

        WritableImage sideImage = sideView.snapshot(params, null);
        BufferedImage bSideImage = SwingFXUtils.fromFXImage(sideImage, null);
        ByteArrayOutputStream sideOut = new ByteArrayOutputStream();
        ImageIO.write(bSideImage, "PNG", sideOut);
        PDImageXObject sidePdfImage = PDImageXObject.createFromByteArray(document, sideOut.toByteArray(), "side_view.png");

        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Write Airport and Runway names
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contentStream.newLineAtOffset(100, 750);
        contentStream.showText("Airport: " + airportName + " - Runway: " + runwayName);
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("TORA: " + runway.getTora() + "m, TODA: " + runway.getToda() + "m, ASDA: " + runway.getAsda() + "m, LDA: " + runway.getLda() + "m");
        contentStream.endText();

        // Draw images with titles
        drawImageWithCaption(contentStream, topPdfImage, 100, 500, 400, 200, "Top-Down View");
        drawImageWithCaption(contentStream, sidePdfImage, 100, 250, 400, 200, "Side View");

        contentStream.close();
        document.save(file);
        System.out.println("PDF saved to " + file.getAbsolutePath());
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (document != null) {
          try {
            document.close();
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      }
    });
  }

  private static void drawImageWithCaption(PDPageContentStream contentStream, PDImageXObject image, float x, float y, float maxWidth, float maxHeight, String title) throws IOException {
    contentStream.beginText();
    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
    contentStream.newLineAtOffset(x, y + 30);
    contentStream.showText(title);
    contentStream.endText();

    float originalWidth = image.getWidth();
    float originalHeight = image.getHeight();
    float ratio = Math.min(maxWidth / originalWidth, maxHeight / originalHeight);
    float scaledWidth = originalWidth * ratio;
    float scaledHeight = originalHeight * ratio;
    contentStream.drawImage(image, x, y - scaledHeight + 20, scaledWidth, scaledHeight);
  }
}
