package com.CG.CookGame.Controllers;

import com.CG.CookGame.Models.User;
import com.CG.CookGame.Models.UserDetails;
import com.CG.CookGame.Repositorys.UserDetailsRepository;
import com.CG.CookGame.Repositorys.UserRepository;
import com.CG.CookGame.bean.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class InfoPageController {
    @Autowired
    private  final HttpSession session;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private final UserDetailsRepository userDetailsRepository;

    @GetMapping("/{id}")
    public String gameMainForUserWithId(@PathVariable(value = "id") long id, Model model) {
        if(session.isPresent())
        {
        User currentUser = session.getUser();
        if (currentUser != null && currentUser.getId() == id) {
            Optional<User> currentUserOptional = userRepository.findById(id);
            if (currentUserOptional.isPresent()) {
                model.addAttribute("user", currentUserOptional.get());
                return "Main";
            } else {
                return "redirect:/";
            }
        } else {return "redirect:/";}
        }
        return "redirect:/";
    }
    @GetMapping("/logout")
    public String logout(Model model){
        if (session.isPresent()){
            session.clearUser();
            return "redirect:/"; }
        return "redirect:/";
    }
    @GetMapping("/{id}/{user}")
    public String profile(@PathVariable(value = "user") String login, @PathVariable(value = "id") long id, Model model) {
        if (session.isPresent()){
        User currentUser =  session.getUser();
        if (currentUser != null && currentUser.getId() == id) {
            Optional<User> currentUserOptional = userRepository.findById(id);
            Optional<UserDetails> currentUserDetailsOptional = userDetailsRepository.findById(id);

                model.addAttribute("user", currentUserOptional.get());
                model.addAttribute("userDetails", currentUserDetailsOptional.get());
                return "Profile";

        } else {
            return "redirect:/error";
        }
        }
        return "redirect:/";
    }


}
