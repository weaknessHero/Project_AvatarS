package project.avatar.api.service.Detect;

/*import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Rect;
import org.opencv.core.Core;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import org.springframework.stereotype.Service;
import java.awt.*;

@Service
public class ColorExtractionService {

    static {
        System.load("/Users/hwangsedong/Desktop/Project_AvatarS/my-project2/Library/opencv2.framework");
    }

    public Color calculateAverageColor(String imagePath, Rect rect) {
        Mat image = Imgcodecs.imread(imagePath);
        Mat objectArea = new Mat(image, rect);
        Scalar avgColor = Core.mean(objectArea);

        return new Color((int) avgColor.val[0], (int) avgColor.val[1], (int) avgColor.val[2]);
    }
}*/

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import java.awt.Rectangle;

@Service
public class ColorExtractionService {

    public Color calculateAverageColor(String imagePath, Rectangle rect) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            BufferedImage objectArea = image.getSubimage(rect.x, rect.y, rect.width, rect.height);
            Color avgColor = getAverageColor(objectArea);
            return avgColor;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Color getAverageColor(BufferedImage img) {
        long sumRed = 0;
        long sumGreen = 0;
        long sumBlue = 0;
        int width = img.getWidth();
        int height = img.getHeight();
        int totalPixels = width * height;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color pixel = new Color(img.getRGB(x, y));
                sumRed += pixel.getRed();
                sumGreen += pixel.getGreen();
                sumBlue += pixel.getBlue();
            }
        }

        int avgRed = (int) (sumRed / totalPixels);
        int avgGreen = (int) (sumGreen / totalPixels);
        int avgBlue = (int) (sumBlue / totalPixels);

        return new Color(avgRed, avgGreen, avgBlue);
    }
}

