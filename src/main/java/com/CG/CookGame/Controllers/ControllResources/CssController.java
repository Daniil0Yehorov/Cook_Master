package com.CG.CookGame.Controllers.ControllResources;

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
public class CssController {

    @GetMapping("/css/{cssName}")
    public ResponseEntity<byte[]> getCss(@PathVariable String cssName) throws IOException {
        // Загрузка CSS файла из ресурсов
        Path path = Paths.get("src/main/resources/static/css/" + cssName);
        byte[] cssBytes = Files.readAllBytes(path);

        // Определение MIME типа для CSS файла
        String contentType = "text/css";

        // Возврат ответа с CSS файлом
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(cssBytes);
    }
}