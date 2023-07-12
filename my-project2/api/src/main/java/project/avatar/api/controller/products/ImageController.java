package project.avatar.api.controller.products;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.avatar.api.service.Detect.DetectLabels;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ImageController {

    @PostMapping("/api/image")
    public String processImage(@RequestBody MultipartFile image) {
        try {
            // 이미지 처리 로직 수행
            String result = DetectLabels.detectLabels(image.getInputStream());

            // 처리 결과 반환
            return result;
        } catch (IOException e) {
            // 처리 중 오류 발생 시 예외 처리
            return "Error processing image";
        }
    }
}
