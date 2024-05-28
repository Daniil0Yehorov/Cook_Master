package com.CG.CookGame.Controllers;

import com.CG.CookGame.Models.*;
import com.CG.CookGame.Repositorys.*;
import com.CG.CookGame.Services.*;
import com.CG.CookGame.bean.HttpSession;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/{id}/Game")
public class GameController {
    @Autowired
    private  final HttpSession session;
   @Autowired
   private final UserDetailsService userDetailsService;
   @Autowired
   private final UserService userService;
    @Autowired
    private final LevelService levelService;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final UserReachedLevelRepository userReachedLevelRepository;

@GetMapping("")
public String goToGame(@PathVariable(value = "id") long id, Model model) {
    if (session.isPresent()){
    User currentUser = session.getUser();
    if (currentUser != null && currentUser.getId() == id) {
        Optional<UserDetails> currentUserDetailsOptional = userDetailsService.findById(id);
        if (currentUserDetailsOptional.isPresent()) {
            UserDetails userDetails = currentUserDetailsOptional.get();
            int currentLevel = userDetails.getLevel();

            List<Level> levels = levelService.getAllLevels();

            Level nextLevel = null;
            for (Level level : levels) {
                if (level.getId() == currentLevel + 1) {
                    nextLevel = level;
                    break;
                }
            }

            if (nextLevel == null) {
                UserReachedLevel userReachedLevel = userReachedLevelRepository.findByUserDetailsUserId(id);
                if (userReachedLevel != null) {
                    Level currentMaxLevel = userReachedLevel.getLevel();
                    if (currentMaxLevel.getId() == currentLevel + 1) {
                        nextLevel = currentMaxLevel;
                    }
                }
            }
            if (nextLevel != null) {
                model.addAttribute("nextLevel", nextLevel);
            }
            model.addAttribute("user", currentUser);
            model.addAttribute("userDetails", userDetails);
            return "Game";
        }
    }
    return "redirect:/error";}
    return "redirect:/";
}

    @GetMapping("/{idlvl}")
    public String showLvl(@PathVariable(value = "idlvl") long LevelId,@PathVariable(value = "id") long id, Model model) {
        if (session.isPresent()){
        User currentUser = session.getUser();
        if (currentUser != null && currentUser.getId() == id) {

            Optional<User> currentUserOptional = userService.findById(id);
            Optional<UserDetails> currentUserDetailsOptional = userDetailsService.findById(id);
            Level currentLevel=levelService.getLevelById(LevelId);
            if(currentLevel!=null){
                Dish currentDish=levelService.getDishByLevel(currentLevel);
                if(currentDish!=null){
                    Map<Product, Integer> products=levelService.getProductsAndSubsequencesByDish(currentDish);
                    if(products!=null){
                        int maxSubsequence = products.values().stream().mapToInt(Integer::intValue).max().orElse(0);

                        model.addAttribute("maxSubsequence", maxSubsequence);
                        model.addAttribute("products", products);
                        model.addAttribute("user", currentUserOptional.get());
                        model.addAttribute("userDetails", currentUserDetailsOptional.get());
                        model.addAttribute("level", currentLevel);
                        return "LevelH";
                    }
                    else return "redirect:/error";
                }
                else return "redirect:/error";
            }
            else return "redirect:/error";
        }
        else return "redirect:/error";
    }
        return "redirect:/";
    }
    @PostMapping("/submitSequence")
    public String checkSequence(@RequestParam("levelId") Long levelId,
                                @RequestParam("dishName") String dishName,
                                @RequestParam("productId") List<Long> productIds,
                                @RequestParam("subsequence") List<Integer> subsequences,
                                RedirectAttributes redirectAttributes,Model model) {
        if (session.isPresent())
        {

            User currentUser = session.getUser();
            Level currentLevel = levelService.getLevelById(levelId);
            if (currentLevel == null) {return "redirect:/error";}

            Dish currentDish = currentLevel.getDish();

            Map<Product, Integer> expectedSubsequences = levelService.getProductsAndSubsequencesByDish(currentDish);

            for (int i = 0; i < productIds.size(); i++) {
                Long productId = productIds.get(i);
                Integer userSubsequence = subsequences.get(i);
                Optional<Product> productOptional = productRepository.findById(productId);
                if (!productOptional.isPresent()) {
                    return "redirect:/error";
                }
                Product product = productOptional.get();

                // очікувана послідовність
                Integer expectedSubsequence = expectedSubsequences.get(product);

                // Перевірка відповіді
                if (!userSubsequence.equals(expectedSubsequence) && (currentDish == null || !currentDish.getName().equals(dishName))) {
                    redirectAttributes.addFlashAttribute("error", "Помилка, ви ввели неправильну комбінацію і не вгадали назву страви.");
                    return "redirect:/" + currentUser.getId() + "/Game/" + levelId;
                }
                else if (currentDish == null || !currentDish.getName().equals(dishName)) {
                    redirectAttributes.addFlashAttribute("error", "Помилка, ви не вгадали назву страви.");
                    return "redirect:/" + currentUser.getId() + "/Game/" + levelId;
                }
                else if (!userSubsequence.equals(expectedSubsequence)) {
                    redirectAttributes.addFlashAttribute("error", "Помилка, ви ввели неправильну комбінацію і не вгадали назву страви.");
                     return "redirect:/" + currentUser.getId() + "/Game/" + levelId;
                }
            }
            if (currentUser != null)
            {
                Optional<User> currentUserOptional = userService.findById(currentUser.getId());
                Optional<UserDetails> currentUserDetailsOptional = userDetailsService.findById(currentUser.getId());
                model.addAttribute("user", currentUserOptional.get());
                model.addAttribute("userDetails", currentUserDetailsOptional.get());
                List<Level> levels = levelService.getAllLevels();
                model.addAttribute("levels", levels);
                levelService.addScore(currentUser.getId(),currentLevel.getPointsPerLevel());
                levelService.ReachedTheLevel(currentUser.getId(),currentLevel.getId());
                return "redirect:/"+currentUserOptional.get().getId()+"/Game";
            }
        }
        return "redirect:/";
    }

}

