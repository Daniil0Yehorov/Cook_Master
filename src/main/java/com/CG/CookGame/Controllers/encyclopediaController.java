package com.CG.CookGame.Controllers;

import com.CG.CookGame.Models.*;
import com.CG.CookGame.Repositorys.*;
import com.CG.CookGame.bean.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.OptionalLong;
import java.util.stream.Collectors;

@Controller
public class encyclopediaController {

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


    @GetMapping("/{userId}/encyclopedia")
    public String encyclopedia(@PathVariable Long userId, Model model) {
        if(session.isPresent()){
        UserDetails userDetails = userDetailsRepository.findByUserId(userId);
        User user =session.getUser();
        if (userDetails == null) {
            return "redirect:/";
        }
        if(user==null){
            return "redirect:/";
        }
        model.addAttribute("userdetails", userDetails);
        model.addAttribute("user",user);

        List<UserReachedLevel> userLevels = userReachedLevelRepository.findByUserDetails(userDetails);
        if (userLevels.isEmpty()) {
            return "encyclopedia";
        }

        OptionalLong potentialMaxLevelId = userLevels.stream()
                .mapToLong(userLevel -> userLevel.getLevel().getId())
                .max();

        if (!potentialMaxLevelId.isPresent()) {
            return "encyclopedia";
        }

        long maxCompletedLevelId = potentialMaxLevelId.getAsLong() - 1;


        List<Level> levels = levelRepository.findByIdLessThanEqual(maxCompletedLevelId);
        List<Long> dishIds = levels.stream()
                .map(Level::getDishId)
                .distinct()
                .collect(Collectors.toList());
        List<Dish> dishes = dishRepository.findAllByIdIn(dishIds);

        model.addAttribute("dishes", dishes);
        return "encyclopedia";}
        else return "index";
    }
}
