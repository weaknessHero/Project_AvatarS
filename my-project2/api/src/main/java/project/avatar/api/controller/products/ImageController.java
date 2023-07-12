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
