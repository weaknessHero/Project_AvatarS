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

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.avatar.api.service.Detect.DetectLabels;
import project.avatar.api.service.Detect.ObjectAnnotation;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ImageController {

    @PostMapping("/api/image")
    public List<ObjectAnnotation> processImage(@RequestBody MultipartFile image) {
        try {
            // 이미지 처리 로직 수행
            List<ObjectAnnotation> objectAnnotations = DetectLabels.detectObjects(image);

            // 처리 결과 반환
            return objectAnnotations;
        } catch (IOException e) {
            // 처리 중 오류 발생 시 예외 처리
            e.printStackTrace();
            return null;
        }
    }
}
