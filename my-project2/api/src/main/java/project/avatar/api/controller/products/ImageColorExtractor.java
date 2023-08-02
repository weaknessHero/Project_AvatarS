package project.avatar.api.controller.products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import project.avatar.api.service.Detect.ImageColor;
import project.avatar.api.service.Detect.ObjectAnnotation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageColorExtractor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageColorExtractor.class);

    // 바운딩 박스 내에서 가장 많이 추출된 색상을 찾는 메서드
    public static ImageColor extractDominantColor(MultipartFile image, ObjectAnnotation largestObject) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
        if (bufferedImage == null) {
            LOGGER.error("Failed to read the image.");
            return null;
        }
        int x1 = largestObject.getBoundingPoly().getVertices(0).getX();
        int y1 = largestObject.getBoundingPoly().getVertices(0).getY();
        int x2 = largestObject.getBoundingPoly().getVertices(2).getX();
        int y2 = largestObject.getBoundingPoly().getVertices(2).getY();

        List<Integer> colors = new ArrayList<>();
        Map<Integer, Integer> colorCounts = new HashMap<>();

        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2; y++) {
                int rgb = bufferedImage.getRGB(x, y);
                colors.add(rgb);
                colorCounts.put(rgb, colorCounts.getOrDefault(rgb, 0) + 1);
            }
        }

        int maxCount = 0;
        int dominantColor = 0;

        for (Map.Entry<Integer, Integer> entry : colorCounts.entrySet()) {
            int count = entry.getValue();
            if (count > maxCount) {
                maxCount = count;
                dominantColor = entry.getKey();
            }
        }

        Color color = new Color(dominantColor);
        return new ImageColor(color.getRed(), color.getGreen(), color.getBlue());
    }
}
