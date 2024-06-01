package com.CG.CookGame.Controllers;

import com.CG.CookGame.Models.User;
import com.CG.CookGame.Services.UserService;
import com.CG.CookGame.Services.ValidationService;
import com.CG.CookGame.bean.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminProfileControlller {
    @Autowired
    private  HttpSession session;
    @Autowired
    private UserService userService;
    @Autowired
    private ValidationService validationService;

    @GetMapping("/admin/{id}")
    public String bla(Model model, @PathVariable Long id){
        if(session.isPresent())
        {
            User currentUser = session.getUser();
            model.addAttribute("user", currentUser);
            return "adminProfile";}
        return "redirect:/";
    }
    @PostMapping("/changeAdminsPassword")
    public  String updatePassword(@RequestParam String Oldpassword, @RequestParam String newpassword, RedirectAttributes redirectAttributes){
        if (session.isPresent()){
            User currentUser = session.getUser();
            // Перевірка валідності паролю
            if(!validationService.isValidPassword(newpassword)){
                redirectAttributes.addFlashAttribute("errorpassword", "Невалідний пароль");
                return "redirect:/admin/"+currentUser.getId();
            }
            // Перевірка що новий пароль не співпадає зі старим
            if (Oldpassword.equals(newpassword)){
                redirectAttributes.addFlashAttribute("errorpassword", "Ваш новий пароль співпадає зі старим");
                return "redirect:/admin/"+currentUser.getId();
            }
            currentUser.setPassword(newpassword);
            userService.update(currentUser);
            return "redirect:/admin/"+currentUser.getId();}
        return "redirect:/";
    }
}
