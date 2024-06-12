package com.CG.CookGame.Controllers;
import com.CG.CookGame.Models.Dish;
import com.CG.CookGame.Models.Level;
import com.CG.CookGame.Models.User;
import com.CG.CookGame.Services.DishService;
import com.CG.CookGame.Services.LevelService;
import com.CG.CookGame.bean.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Controller
public class addLevelController {
    @Autowired
    private HttpSession session;
    @Autowired
    private LevelService levelService;
    @Autowired
    private DishService dishService;

    @GetMapping("/admin/{id}/addLevel")
    public String showAddLevelPage(@PathVariable Long id, Model model) {
        if (session.isPresent()) {
            User currentUser = session.getUser();
            model.addAttribute("user", currentUser);
            List<Dish> dishesWithoutLevel = dishService.findDishesWithoutLevel();
            model.addAttribute("dishes", dishesWithoutLevel);
            model.addAttribute("adminId", id);
            return "addLevel";
        }
        return "redirect:/";
    }

    @PostMapping("/admin/{id}/addLevel")
    public String addLevel(@PathVariable Long id,
                           @RequestParam(required = false) Long dishId,
                           @RequestParam int PointsPerLevel,
                           @RequestParam String hint,
                           RedirectAttributes redirectAttributes) {
        if (session.isPresent()) {
            if (dishId == null || PointsPerLevel == 0 || hint == null || hint.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Будь ласка, виберіть страву та заповніть всі поля");
                return "redirect:/admin/" + id + "/addLevel";
            }

            Dish dish = dishService.getDishById(dishId);
            if (dish == null) {
                redirectAttributes.addFlashAttribute("error", "Страву не знайдено");
                return "redirect:/admin/" + id + "/addLevel";
            }

            Level level = new Level();
            level.setPointsPerLevel(PointsPerLevel);
            level.setHint(hint);
            level.setDish(dish);

            //levelService.addLevel(level);
            levelService.SaveLevel(level);
            redirectAttributes.addFlashAttribute("success", "Рівень успішно доданий");
            return "redirect:/admin/" + id + "/addLevel";
        }
        return "redirect:/";
    }
}
