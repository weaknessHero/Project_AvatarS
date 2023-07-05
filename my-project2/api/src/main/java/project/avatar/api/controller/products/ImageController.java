package project.avatar.api.controller.products;

import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/images")
@Component
public class ImageController {

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generateImage() throws IOException {
        // 이미지 생성 로직
        File imageFile = createMergedImage();

        // 파일을 읽어서 byte 배열로 변환
        byte[] imageBytes = readImageBytes(imageFile);

        // 파일 다운로드를 위한 응답 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "merged_image.png");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        // 이미지 파일과 헤더를 포함하는 응답 반환
        return ResponseEntity.ok()
                .headers(headers)
                .body(imageBytes);
    }

    private File createMergedImage() throws IOException {
        int width = 300;
        int height = 250;
        // 이미지 생성 로직 작성
        BufferedImage mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // 이미지 그리기
        Graphics2D graphics = mergedImage.createGraphics();
        // ...

        // 이미지 파일로 저장
        File imageFile = new File("merged_image.png");
        ImageIO.write(mergedImage, "png", imageFile);

        return imageFile;
    }

    private byte[] readImageBytes(File file) throws IOException {
        try (InputStream inputStream = new FileInputStream(file)) {
            return inputStream.readAllBytes();
        }
    }

    //@Value("${image.storage.path}")
    /*private String imageStoragePath;

    @PostMapping
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException{
        String filename = file.getOriginalFilename();
        File newFile = new File(imageStoragePath + '/' + filename);
        file.transferTo(newFile);
        return filename;
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException{
        File file = new File(imageStoragePath + "/" + filename);
        if(!file.exists()){
            return ResponseEntity.notFound().build();
        }

        Resource resource = (Resource) new UrlResource(file.toURI());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Files.probeContentType(file.toPath())))
                .body(resource);
    }*/
}
