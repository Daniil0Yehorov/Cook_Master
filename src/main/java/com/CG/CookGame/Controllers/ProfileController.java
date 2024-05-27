package com.CG.CookGame.Controllers;


import com.CG.CookGame.Models.User;
import com.CG.CookGame.Models.UserDetails;
import com.CG.CookGame.Repositorys.UserDetailsRepository;
import com.CG.CookGame.Repositorys.UserRepository;
import com.CG.CookGame.Services.ValidationService;
import com.CG.CookGame.bean.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@AllArgsConstructor
@Controller("/{id}/{user}")
public class ProfileController {
    @Autowired
    private  final HttpSession session;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDetailsRepository userDetailsRepository;
    @Autowired
    private final ValidationService validationService;

    @PostMapping("/changeLogin")
    public String updateLogin(@RequestParam String login, RedirectAttributes redirectAttributes){
     if (session.isPresent()){
        User currentUser = session.getUser();
        if (!validationService.isLoginValid(login)) {
            redirectAttributes.addFlashAttribute("errorlogin", "Невалідний логін");
            return "redirect:/" + currentUser.getId()+"/"+currentUser.getLogin();
        }

        if (currentUser.getLogin().equals(login)) {
            redirectAttributes.addFlashAttribute("errorlogin", "Ваш новий пароль співпадає зі старим");
            return "redirect:/" + currentUser.getId()+"/"+currentUser.getLogin();
        }

        if (!validationService.isLoginUnique(login)) {
            redirectAttributes.addFlashAttribute("errorlogin", "Логін вже існує");
            return "redirect:/" + currentUser.getId()+"/"+currentUser.getLogin();
        }
        currentUser.setLogin(login);
        userRepository.save(currentUser);

        return "redirect:/" + currentUser.getId()+"/"+currentUser.getLogin();}
        return "redirect:/";
    }
    @PostMapping ("/changePassword")
    public  String updatePassword(@RequestParam String Oldpassword,@RequestParam String newpassword,RedirectAttributes redirectAttributes){
        if (session.isPresent()){
        User currentUser = session.getUser();
        if(!validationService.isValidPassword(newpassword)){
            redirectAttributes.addFlashAttribute("errorpassword", "Невалідний пароль");
            return "redirect:/"+currentUser.getId()+"/"+currentUser.getLogin();
        }
        if (Oldpassword.equals(newpassword)){
            redirectAttributes.addFlashAttribute("errorpassword", "Ваш новий пароль співпадає зі старим");
            return "redirect:/"+ currentUser.getId()+"/"+currentUser.getLogin();
        }
        currentUser.setPassword(newpassword);
        userRepository.save(currentUser);
        return "redirect:/" + currentUser.getId()+"/"+currentUser.getLogin();}
        return "redirect:/";
    }
    @PostMapping("/changeEmail")
    public String updateEmail(@RequestParam String gmail,RedirectAttributes redirectAttributes){
        if (session.isPresent()){
        User currentUser = session.getUser();
        UserDetails userDetails = currentUser.getUserDetails();
        if(!validationService.isValidEmail(gmail)){
            redirectAttributes.addFlashAttribute("erroremail", "пошта ведена некоректно");
            return "redirect:/"+currentUser.getId()+"/"+currentUser.getLogin();
        }
        if(gmail.equals(userDetails.getUgmail())){
            redirectAttributes.addFlashAttribute("erroremail", "Ви вели пошту, яка було до цього");
            return "redirect:/"+currentUser.getId()+"/"+currentUser.getLogin();
        }
        if (!validationService.isEmailUnique(gmail)) {
            redirectAttributes.addFlashAttribute("erroremail", "Пошта занята іншим користувачем");
            return "redirect:/" + currentUser.getId()+"/"+currentUser.getLogin();
        }
        userDetails.setUgmail(gmail);
        userDetailsRepository.save(userDetails);
        return "redirect:/" + currentUser.getId()+"/"+currentUser.getLogin();}
        return "redirect:/";
    }

    @GetMapping("/Main")
    public String toMainPage(){
        if(session.isPresent())
        {
        User user =session.getUser();
        return "redirect:/" + user.getId();
        }
        return "redirect:/";
    }

}
