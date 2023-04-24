package shop.mtcoding.restend.core.util;

import org.springframework.web.multipart.MultipartFile;
import shop.mtcoding.restend.core.exception.Exception500;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class MyFileUtil {
    public static String write(String uploadFolder, MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        String originalFilename = file.getOriginalFilename();
        String uuidFilename = uuid + "_" + originalFilename;
        try {
            Path filePath = Paths.get(uploadFolder + uuidFilename);
            Files.write(filePath, file.getBytes());
        } catch (Exception e) {
            throw new Exception500("파일 업로드 실패 : "+e.getMessage());
        }
        return uuidFilename;
    }
}
