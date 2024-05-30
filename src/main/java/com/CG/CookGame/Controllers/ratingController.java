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

import java.util.List;
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
                return "redirect:/login";
            }

            model.addAttribute("currentUser", currentUser);

            List<UserDetails> users = userDetailsRepository.findAll(Sort.by(Sort.Direction.DESC, "points"));
            if (users.size() > 10) {
                users = users.subList(0, 10);
            }
            model.addAttribute("users", users);

            return "rating";
        } else {
            return "redirect:/";
        }
    }
}