package com.CG.CookGame.Controllers;

import com.CG.CookGame.Models.Dish;
import com.CG.CookGame.Models.Level;
import com.CG.CookGame.Models.UserDetails;
import com.CG.CookGame.Models.UserReachedLevel;
import com.CG.CookGame.Repositorys.DishRepository;
import com.CG.CookGame.Repositorys.LevelRepository;
import com.CG.CookGame.Repositorys.UserDetailsRepository;
import com.CG.CookGame.Repositorys.UserReachedLevelRepository;
import com.CG.CookGame.bean.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;

@Controller
public class ratingController {

    @Autowired
    private HttpSession session;
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private UserReachedLevelRepository userReachedLevelRepository;

    @GetMapping("/{userId}/rating")
    public String rating(@PathVariable Long userId, Model model) {
        if (session.isPresent()) {
            UserDetails currentUser = userDetailsRepository.findByUserId(userId);
            if (currentUser == null) {
                return "redirect:/";
            }

            model.addAttribute("currentUser", currentUser);

            // Получаем всех пользователей, отсортированных по количеству баллов
            List<UserDetails> allUsers = userDetailsRepository.findAll(Sort.by(Sort.Direction.DESC, "points"));

            List<UserDetails> topUsers = new ArrayList<>();
            for (int i = 0; i < 5 && i < allUsers.size(); i++) {
                topUsers.add(allUsers.get(i));
            }
            model.addAttribute("users", topUsers);

            // Проверяем, находится ли текущий пользователь вне топ-5
            boolean isCurrentUserInTop = topUsers.stream().anyMatch(user -> user.getUserId().equals(userId));
            if (!isCurrentUserInTop) {
                // Находим позицию текущего пользователя
                int currentUserIndex = allUsers.indexOf(currentUser);
                if (currentUserIndex >= 0) {
                    model.addAttribute("currentUserPosition", currentUserIndex + 1);
                } else {
                    model.addAttribute("currentUserPosition", null);
                }
            } else {
                model.addAttribute("currentUserPosition", null);
            }
            return "rating";
        } else {
            return "redirect:/";
        }
    }
}
