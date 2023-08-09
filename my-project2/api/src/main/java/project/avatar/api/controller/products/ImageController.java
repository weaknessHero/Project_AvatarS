/*package project.avatar.api.controller.products;

import com.google.cloud.vision.v1.BoundingPoly;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.avatar.api.service.Detect.DetectLabels;
import project.avatar.api.service.Detect.ImageAnalysisResult;
import project.avatar.api.service.Detect.ImageColor;
import project.avatar.api.service.Detect.ObjectAnnotation;
import com.google.cloud.vision.v1.Vertex;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "*")
@RestController
public class ImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    @PostMapping("/api/image")
    public ResponseEntity<ImageAnalysisResult> processImage(@RequestParam("image") MultipartFile imageFile) {
        try {
            List<ObjectAnnotation> objectAnnotations = DetectLabels.detectObjects(imageFile);
            LOGGER.info("Detected objects: {}", objectAnnotations);

            if (objectAnnotations != null && !objectAnnotations.isEmpty()) {
                ObjectAnnotation largestObject = getLargestObject(objectAnnotations);
                if (largestObject != null) {
                    ImageColor color = ImageColorExtractor.extractDominantColor(imageFile, largestObject);
                    LOGGER.info("Extracted dominant color: {}", color);
                    return ResponseEntity.ok(new ImageAnalysisResult(objectAnnotations, color));
                } else {
                    LOGGER.info("Cannot find the largest object in the image");
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
                }
            }
            return ResponseEntity.ok(new ImageAnalysisResult(null, null));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // 객체 검출 결과에서 가장 큰 객체를 찾는 메서드
    private ObjectAnnotation getLargestObject(List<ObjectAnnotation> objectAnnotations) {
        if (objectAnnotations == null || objectAnnotations.isEmpty()) {
            return null;
        }

        ObjectAnnotation largestObject = null;
        float maxArea = 0f;
        for (ObjectAnnotation annotation : objectAnnotations) {
            BoundingPoly boundingPoly = annotation.getBoundingPoly();
            if (boundingPoly != null) {
                List<Vertex> vertices = boundingPoly.getVerticesList();
                if (vertices != null && vertices.size() >= 4) {
                    Vertex vertex2 = vertices.get(2);
                    Vertex vertex0 = vertices.get(0);
                    if (vertex2 != null && vertex0 != null) {
                        float area = (vertex2.getX() - vertex0.getX()) * (vertex2.getY() - vertex0.getY());
                        if (area > maxArea) {
                            maxArea = area;
                            largestObject = annotation;
                        }
                    }
                } else {
                    LOGGER.warn("Invalid vertices data: either null or insufficient vertices.");
                }
            }
        }
        if (largestObject == null) {
            LOGGER.warn("No valid largest object found.");
        }
        return largestObject;
    }
}*/


package project.avatar.api.controller.products;

import java.awt.Rectangle;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.avatar.api.service.Detect.ColorExtractionService;
import project.avatar.api.service.Detect.DetectLabels;
import project.avatar.api.service.Detect.ImageColor;
import project.avatar.api.service.Detect.ObjectAnnotation;
import project.avatar.api.controller.products.ImageColorExtractor;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ImageController {

    @Autowired
    private ImageColorExtractor imageColorExtractor;
    private final ColorExtractionService colorExtractionService;

    public ImageController(ColorExtractionService colorExtractionService){
        this.colorExtractionService = colorExtractionService;
    }

    @PostMapping("/api/image")
    public ResponseEntity<List<ObjectAnnotation>> processImage(
            @RequestParam("image") MultipartFile image) {
        try {
            // 이미지 처리 로직 수행
            List<ObjectAnnotation> objectAnnotations = DetectLabels.detectObjects(image);
            Path targetDirectory = Paths.get("src", "main", "resources", "images");
            String imagePath = FileUtils.saveImage(image, targetDirectory);

            // 변경 사항 1: 예외 처리를 감지하기 위해 for-each 대신 기본 for 루프 사용
            for (int i = 0; i < objectAnnotations.size(); i++) {
                ObjectAnnotation objectAnnotation = objectAnnotations.get(i);
                int x = objectAnnotation.getX();
                int y = objectAnnotation.getY();
                int width = objectAnnotation.getWidth();
                int height = objectAnnotation.getHeight();

                Rectangle rect = new Rectangle(x, y, width, height);

                // 변경 사항 2: 예외 처리를 위해 try-catch 문 추가
                try {
                    // ColorExtractionService 대신 ImageColorExtractor 사용
                    ImageColor dominantColor = imageColorExtractor.extractDominantColor(image, objectAnnotation);

                    // ImageColor를 Color로 변환
                    Color color = new Color(dominantColor.getRed(), dominantColor.getGreen(), dominantColor.getBlue());
                    objectAnnotation.setColor(color);
                } catch (IllegalArgumentException e) {
                    // BoundingPoly 가 null 인 경우에 대한 처리
                    // 예외 발생시 리스트에서 ObjectAnnotation 제거
                    objectAnnotations.remove(i);
                    i--; // 인덱스 조정
                }
            }
            // 처리 결과 반환
            return ResponseEntity.ok(objectAnnotations);
        } catch (IOException e) {
            // 처리 중 오류 발생 시 예외 처리
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}