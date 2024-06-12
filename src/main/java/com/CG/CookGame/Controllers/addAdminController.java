package com.CG.CookGame.Controllers;

import com.CG.CookGame.Enums.Role;
import com.CG.CookGame.Models.User;
import com.CG.CookGame.Models.UserDetails;
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
public class addAdminController {
    @Autowired
    private HttpSession session;
    @Autowired
    private UserService userService;
    @Autowired
    private ValidationService validationService;

    @GetMapping("/admin/{id}/addAdmin")
    public String showAddAdminPage(@PathVariable Long id, Model model) {
        if (session.isPresent()) {
            User currentUser = session.getUser();
            model.addAttribute("user", currentUser);
            model.addAttribute("adminId", id);
            return "addAdmin";
        }
        return "redirect:/";
    }

    @PostMapping("/admin/{id}/addAdmin")
    public String addAdmin(@PathVariable Long id,
                           @RequestParam String login,
                           @RequestParam String password,
                           RedirectAttributes redirectAttributes) {
        if (session.isPresent()) {

            if (!validationService.isLoginValid(login)) {
                redirectAttributes.addFlashAttribute("error", "Невалідний логін");
                return "redirect:/admin/" + id + "/addAdmin";
            }

            if (!validationService.isLoginUnique(login)) {
                redirectAttributes.addFlashAttribute("error", "Логін вже занят");
                return "redirect:/admin/" + id + "/addAdmin";
            }

            if (!validationService.isValidPassword(password)) {
                redirectAttributes.addFlashAttribute("error", "Невалідний пароль");
                return "redirect:/admin/" + id + "/addAdmin";
            }

            User admin = new User(login, password, Role.ADMIN_ROLE);
            userService.save(admin);

            redirectAttributes.addFlashAttribute("success", "Адміністратор успішно доданий");
            return "redirect:/admin/" + id + "/addAdmin";
        }
        return "redirect:/";
    }
}