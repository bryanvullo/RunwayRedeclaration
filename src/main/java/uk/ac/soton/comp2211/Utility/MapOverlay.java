package uk.ac.soton.comp2211.Utility;// MapOverlay.java 文件
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MapOverlay {

    public BufferedImage overlayMap(BufferedImage realWorldImage, BufferedImage mapImage) throws IOException {
        // 确认图像尺寸一致或调整逻辑以处理不同尺寸的图像
        BufferedImage combinedImage = new BufferedImage(
                realWorldImage.getWidth(), 
                realWorldImage.getHeight(), 
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = combinedImage.createGraphics();
        g2d.drawImage(realWorldImage, 0, 0, null);
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        g2d.setComposite(alphaComposite);
        g2d.drawImage(mapImage, 0, 0, null);
        g2d.dispose();

        return combinedImage;
    }
}
