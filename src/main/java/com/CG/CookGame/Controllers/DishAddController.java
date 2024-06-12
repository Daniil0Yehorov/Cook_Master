package com.CG.CookGame.Controllers;

import com.CG.CookGame.Models.Dish;
import com.CG.CookGame.Models.DishHaveProducts;
import com.CG.CookGame.Models.Product;
import com.CG.CookGame.Models.User;
import com.CG.CookGame.Repositorys.DishHaveProductsRepository;
import com.CG.CookGame.Repositorys.DishRepository;
import com.CG.CookGame.Repositorys.ProductRepository;
import com.CG.CookGame.Services.DishService;
import com.CG.CookGame.bean.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/dishAdd")
public class DishAddController {
    @Autowired
    HttpSession httpSession;
    public static String UPLOAD_DIRECTORY = "src/main/resources/static/images/";
    @Autowired
    ProductRepository productRepository;
    @Autowired
    DishHaveProductsRepository dishHaveProductsRepository;
    @Autowired
    DishRepository dishRepository;
    @Autowired
    DishService dishService;

    @GetMapping("")
    public String showDishForm(Model model) {
        if(httpSession.isPresent()){


        User user= httpSession.getUser();
        model.addAttribute("user",user);
        model.addAttribute("dish", new Dish());
        model.addAttribute("products", productRepository.findAll());
        return "dishAdd";}
        return "redirect:/";
    }

    @PostMapping("/save")
    public String saveDish(@RequestParam String name, @RequestParam String descr,
                           @RequestParam("image") MultipartFile file,
                           @RequestParam String wikySrc, @RequestParam String YouTubeSrc,
                           @RequestParam("products") List<Long> productIds,
                           @RequestParam("subsequences") List<Integer> subsequences, RedirectAttributes redirectAttributes) {
        if (dishService.SameDishFinder(name)){
            redirectAttributes.addFlashAttribute("error","Страва з подібною назвою вже існує");
            return "redirect:/dishAdd";
        }
        if (productIds.size() <= 1) {
            redirectAttributes.addFlashAttribute("error", "Страва має мати більше одного продукту");
            return "redirect:/dishAdd";
        }
        String videoId = dishService.extractYouTubeId(YouTubeSrc);
        Dish dish =new Dish();
        dish.setName(name);
        dish.setDescr(descr);
        dish.setWikySrc(wikySrc);
        dish.setYouTubeSrc(YouTubeSrc);
        dish.setVideoId(videoId);
        try {
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());
            dish.setImageSrc("/images/"+file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Файл не був додан: " + file.getOriginalFilename());
            return "redirect:/dishAdd";
        }
        dishRepository.save(dish);
        for (int i = 0; i < productIds.size(); i++) {
            Product product = productRepository.findById(productIds.get(i)).orElseThrow();
            DishHaveProducts dishHaveProducts = new DishHaveProducts();
            dishHaveProducts.setDish(dish);
            dishHaveProducts.setProduct(product);
            dishHaveProducts.setSubsequence(subsequences.get(i));
            dishHaveProductsRepository.save(dishHaveProducts);
        }
        return "redirect:/dishAdd";
    }

}
