package com.CG.CookGame.Controllers.ControllResources;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class JsController {

    @GetMapping("/js/{jsName}")
    public ResponseEntity<byte[]> getJs(@PathVariable String jsName) {
        // Путь к файлу JavaScript в ресурсах
        String jsPath = "static/js/" + jsName;

        // Попытка загрузить файл JavaScript из ресурсов
        try {
            ClassPathResource resource = new ClassPathResource(jsPath);
            byte[] jsBytes = Files.readAllBytes(resource.getFile().toPath());

            // Определение MIME типа для JavaScript файла
            String contentType = "text/javascript";

            // Возврат ответа с JavaScript файлом
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(jsBytes);
        } catch (IOException e) {
            // Если файл JavaScript не найден, вернуть ошибку 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}