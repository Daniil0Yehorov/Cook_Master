package com.CG.CookGame.Services;

import com.CG.CookGame.Models.Dish;
import com.CG.CookGame.Models.DishHaveProducts;
import com.CG.CookGame.Repositorys.DishHaveProductsRepository;
import com.CG.CookGame.Repositorys.DishRepository;
import com.CG.CookGame.Repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DishService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private DishHaveProductsRepository dishHaveProductsRepository;
    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public List<Dish> findDishesWithoutLevel() {
        return dishRepository.findDishesWithoutLevel();
    }
    public Dish getDishById(Long id) {
        return dishRepository.findById(id).orElse(null);
    }

    public String extractYouTubeId(String youTubeUrl) {
        String pattern = "^(?:https?://)?(?:www\\.)?(?:youtube\\.com/watch\\?v=|youtu\\.be/)([^&?/]+)";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
    public boolean SameDishFinder(String name) {
        Dish existingDish = dishRepository.findByName(name);
        return existingDish != null;
    }
}
