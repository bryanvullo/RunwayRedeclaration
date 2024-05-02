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
import uk.ac.soton.comp2211.component.CalculationTab;
import uk.ac.soton.comp2211.model.CalculationBreakdown;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class PDFExporter {
  public static void exportRunwayViews(StackPane topDownView, StackPane sideView,
                                       String airportName, String runwayName,
                                       String fileName, CalculationBreakdown calculationBreakdown, CalculationTab calculationTab) {
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

        PDPage firstPage = new PDPage(PDRectangle.A4);
        document.addPage(firstPage);
        PDPageContentStream contentStream = new PDPageContentStream(document, firstPage);

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
        contentStream.newLineAtOffset(50, 800);
        contentStream.showText("Runway ReDeclaration Calculation Report");
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contentStream.newLineAtOffset(0, -50);
        contentStream.showText("Airport: " + airportName + " - Runway: " + runwayName);
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(0, -30);
        contentStream.showText(String.format("Original TORA: %.2fm, TODA: %.2fm, ASDA: %.2fm, LDA: %.2fm", calculationTab.orignalTora.get(), calculationTab.originalToda.get(), calculationTab.originalAsda.get(), calculationTab.originalLda.get()));
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText(String.format("Recalculated TORA: %.2fm, TODA: %.2fm, ASDA: %.2fm, LDA: %.2fm", calculationTab.recalculatedTora.get(), calculationTab.recalculatedToda.get(), calculationTab.recalculatedAsda.get(), calculationTab.recalculatedLda.get()));
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText(String.format("Previous TORA: %.2fm, TODA: %.2fm, ASDA: %.2fm, LDA: %.2fm", calculationTab.previousTora.get(), calculationTab.previousToda.get(), calculationTab.previousAsda.get(), calculationTab.previousLda.get()));
        contentStream.endText();

        drawImageWithCaption(contentStream, topPdfImage, 50, 500, 400, 200, "Top-Down View");
        drawImageWithCaption(contentStream, sidePdfImage, 50, 280, 400, 200, "Side View");
        contentStream.close();

        PDPage secondPage = new PDPage(PDRectangle.A4);
        document.addPage(secondPage);
        contentStream = new PDPageContentStream(document, secondPage);

        contentStream = new PDPageContentStream(document, secondPage);

// Calculation Breakdown
        writeTextWithNewLines(contentStream, "Calculation Breakdown:", 50, 750, -20);

// Now write the individual breakdowns, each call adjusts the y-coordinate to start appropriately
        int currentY = 730; // Adjust starting y-coordinate as needed
        writeTextWithNewLines(contentStream, "TORA Breakdown: " + calculationBreakdown.getToraBreakdown().get(), 50, currentY, -15);
        currentY -= 40; // Adjust spacing as needed based on content length
        writeTextWithNewLines(contentStream, "TODA Breakdown: " + calculationBreakdown.getTodaBreakdown().get(), 50, currentY -20, -15);
        currentY -= 40;
        writeTextWithNewLines(contentStream, "ASDA Breakdown: " + calculationBreakdown.getAsdaBreakdown().get(), 50, currentY - 20, -15);
        currentY -= 40;
        writeTextWithNewLines(contentStream, "LDA Breakdown: " + calculationBreakdown.getLdaBreakdown().get(), 50, currentY -20, -15);

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

  private static void writeTextWithNewLines(PDPageContentStream contentStream, String text, float startX, float startY, float lineOffset) throws IOException {
    String[] lines = text.split("\n");
    contentStream.beginText();
    contentStream.setFont(PDType1Font.HELVETICA, 10); // Consider using a smaller font if space is an issue
    contentStream.newLineAtOffset(startX, startY);

    for (String line : lines) {
      contentStream.showText(line);
      contentStream.newLineAtOffset(0, lineOffset); // Adjust line spacing if needed
    }
    contentStream.endText();
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
