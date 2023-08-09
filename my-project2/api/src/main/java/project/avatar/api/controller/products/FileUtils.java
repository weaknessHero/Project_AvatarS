package project.avatar.api.controller.products;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileUtils {
    
    public static String saveImage(MultipartFile image, Path targetDirectory) throws IOException{
        if(!Files.exists(targetDirectory)){
            Files.createDirectory(targetDirectory);
        }
        Path targetFilePath = targetDirectory.resolve(image.getOriginalFilename());
        Files.copy(image.getInputStream(),targetFilePath, StandardCopyOption.REPLACE_EXISTING);
        return targetFilePath.toString();
    }
}
