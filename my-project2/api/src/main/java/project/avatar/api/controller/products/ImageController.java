package project.avatar.api.controller.products;

import lombok.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/images")
@Component
public class ImageController {

    //@Value("${image.storage.path}")
    private String imageStoragePath;

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
    }
}
