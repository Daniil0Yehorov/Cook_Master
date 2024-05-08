package com.CG.CookGame.Controllers;

import com.CG.CookGame.Models.User;
import com.CG.CookGame.Repositorys.UserRepository;
import com.CG.CookGame.Services.ValidationService;
import com.CG.CookGame.bean.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
public class AuthorizationController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private  final HttpSession session;
    @Autowired
    private final ValidationService validationService;

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password, Model model) {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            model.addAttribute("error", "Login doesn't exist");
        } else {
            if (!user.getPassword().equals(password)) {
                model.addAttribute("error", "Password is wrong");
                return "index";
            } else {
                session.setUser(user);
                return "redirect:/" + user.getId();
            }
        }
        return "index";
    }

    @PostMapping("/registration")
    public String register(@RequestParam String login, @RequestParam String password,
                           @RequestParam String passwordCheck, Model model) {
        if (!validationService.isLoginValid(login)) {
            model.addAttribute("error", "Invalid login");
            return "index";
        }
        if (!validationService.isLoginUnique(login)) {
            model.addAttribute("error", "Login already exists");
            return "index";
        }
        if(!validationService.isValidPassword(password)){
            model.addAttribute("error", "Invalid password");
            return "index";
        }
        if (!password.equals(passwordCheck)) {
            model.addAttribute("error", "Passwords do not match");
            return "index";
        }
        User newUser = new User(login, password);
        userRepository.save(newUser);
        session.setUser(newUser);
        return "redirect:/" + newUser.getId();
    }

}
