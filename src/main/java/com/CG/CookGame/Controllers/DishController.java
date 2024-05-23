package com.CG.CookGame.Controllers;

import com.CG.CookGame.Models.Dish;
import com.CG.CookGame.Models.User;
import com.CG.CookGame.Repositorys.DishRepository;
import com.CG.CookGame.Repositorys.UserDetailsRepository;
import com.CG.CookGame.bean.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DishController {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private HttpSession session;

    @GetMapping("/dishes/{dishId}")
    public String showDish(@PathVariable Long dishId, Model model) {
        if(session.isPresent()){
        Dish dish = dishRepository.findById(dishId).orElse(null);
        if (dish == null) {
            return "redirect:/encyclopedia";
        }

        User user = session.getUser();
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("dish", dish);
        return "dish";}
        else return "index";
    }
}