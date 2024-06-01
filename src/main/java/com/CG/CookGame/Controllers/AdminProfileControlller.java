package com.CG.CookGame.Controllers;

import com.CG.CookGame.Models.User;
import com.CG.CookGame.bean.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminProfileControlller {
    @Autowired
    private  HttpSession session;

    @GetMapping("/admin/{id}")
    public String bla(Model model, @PathVariable Long id){
        if(session.isPresent())
        {
            User currentUser = session.getUser();
            model.addAttribute("user", currentUser);
            return "adminProfile";}
        return "index";
    }
}
