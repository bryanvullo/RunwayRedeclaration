package uk.ac.soton.comp2211;// MapOverlayTest.java 文件

import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.soton.comp2211.Utility.MapOverlay;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class MapOverlayTest {

    private static MapOverlay mapOverlay;
    private static BufferedImage realWorldImage;
    private static BufferedImage mapImage;
    private static final String realWorldImagePath = "path_to_your_real_world_image.jpg"; // 替换为你的实景图像路径
    private static final String mapImagePath = "path_to_your_map_image.png"; // 替换为你的地图图像路径

    @BeforeClass
    public static void setUp() throws IOException {
        mapOverlay = new MapOverlay();
        realWorldImage = ImageIO.read(new File(realWorldImagePath));
        mapImage = ImageIO.read(new File(mapImagePath));
    }

    @Test
    public void testOverlayMap() {
        try {
            BufferedImage combinedImage = mapOverlay.overlayMap(realWorldImage, mapImage);
            assertNotNull("叠加后的图像不应该为空", combinedImage);

            // 检查叠加后图像的尺寸
            assertEquals("叠加后图像的宽度应该与原图相同", realWorldImage.getWidth(), combinedImage.getWidth());
            assertEquals("叠加后图像的高度应该与原图相同", realWorldImage.getHeight(), combinedImage.getHeight());

            // 可以添加更多的断言来验证图像的某些像素，确保叠加是正确的

            // 如果有必要，可以将结果图像写入磁盘
            // ImageIO.write(combinedImage, "PNG", new File("path_to_output_test_image.png"));
        } catch (IOException e) {
            fail("在叠加图像时抛出了IOException");
        }
    }
}
