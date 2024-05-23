package com.CG.CookGame.Controllers.ControllResources;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageController {

    @GetMapping("/images/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        // Загрузка изображения из ресурсов
        Path path = Paths.get("src/main/resources/static/images/" + imageName);
        byte[] imageBytes = Files.readAllBytes(path);

        // Определение MIME типа изображения
        String contentType = imageName.endsWith(".png") ? MediaType.IMAGE_PNG_VALUE : MediaType.IMAGE_JPEG_VALUE;

        // Возврат ответа с изображением
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(imageBytes);
    }
}