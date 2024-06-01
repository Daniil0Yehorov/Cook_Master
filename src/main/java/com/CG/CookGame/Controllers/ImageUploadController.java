package com.CG.CookGame.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ImageUploadController {
    public static String UPLOAD_DIRECTORY = "src/main/resources/static/images/";

    @GetMapping("/uploadimage")
    public String displayUploadForm() {
        return "test";
    }

    @PostMapping("/upload")
    public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            model.addAttribute("msg", "Please select a file to upload");
            return "test";
        }

        try {
            // Путь к сохранению файла
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
            // Сохранение файла
            Files.write(fileNameAndPath, file.getBytes());
            model.addAttribute("msg", "Uploaded image: " + file.getOriginalFilename());
            model.addAttribute("imageUrl", "/images/" + file.getOriginalFilename()); // Добавляем URL для отображения загруженной фотографии
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("msg", "Failed to upload image: " + file.getOriginalFilename());
        }

        return "test1";
    }
}
