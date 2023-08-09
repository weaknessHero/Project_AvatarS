package project.avatar.api.controller.products;

import com.google.cloud.vision.v1.BoundingPoly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
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

@Service
public class ImageColorExtractor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageColorExtractor.class);

    // 바운딩 박스 내에서 가장 많이 추출된 색상을 찾는 메서드
    public static ImageColor extractDominantColor(MultipartFile image, ObjectAnnotation largestObject) throws IOException {
        // 이미지 입력 스트림에서 BufferedImage 객체 생성
        BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
        if (bufferedImage == null) {
            LOGGER.error("Failed to read the image.");
            return null;
        }

        // largestObject의 BoundingPoly가 `null`인 경우를 처리
        BoundingPoly boundingPoly = largestObject.getBoundingPoly();
        if (boundingPoly == null) {
            throw new IllegalArgumentException("BoundingPoly should not be null.");
        }

        // BoundingPoly의 정점 값 추출
        int x1 = boundingPoly.getVertices(0).getX();
        int y1 = boundingPoly.getVertices(0).getY();
        int x2 = boundingPoly.getVertices(2).getX();
        int y2 = boundingPoly.getVertices(2).getY();

        // 색상 및 색상 빈도수 집계
        List<Integer> colors = new ArrayList<>();
        Map<Integer, Integer> colorCounts = new HashMap<>();

        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2; y++) {
                int rgb = bufferedImage.getRGB(x, y);
                colors.add(rgb);
                colorCounts.put(rgb, colorCounts.getOrDefault(rgb, 0) + 1);
            }
        }

        // 지배 색상 찾기
        int maxCount = 0;
        int dominantColor = 0;

        for (Map.Entry<Integer, Integer> entry : colorCounts.entrySet()) {
            int count = entry.getValue();
            if (count > maxCount) {
                maxCount = count;
                dominantColor = entry.getKey();
            }
        }

        // 지배 색상을 ImageColor 객체로 반환
        Color color = new Color(dominantColor);
        return new ImageColor(color.getRed(), color.getGreen(), color.getBlue());
    }

}
