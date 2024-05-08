package com.CG.CookGame.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {


    @GetMapping("/")
    public String logUndReg(Model model){
         model.addAttribute("title", "Main Page");
        return "index";}

}
