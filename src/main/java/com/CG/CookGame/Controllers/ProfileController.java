package com.CG.CookGame.Controllers;


import com.CG.CookGame.Models.User;
import com.CG.CookGame.Models.UserDetails;
import com.CG.CookGame.Services.UserDetailsService;
import com.CG.CookGame.Services.UserService;
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
    private final UserService userService;
    @Autowired
   private final UserDetailsService userDetailsService;
    @Autowired
    private final ValidationService validationService;
    //Redirectatributes були додані, бо з моделлю не працює; Так як я через редірект залишаюсь на сторінці теж самій

    @PostMapping("/changeLogin")
    public String updateLogin(@RequestParam String login, RedirectAttributes redirectAttributes){
        if (session.isPresent()){
            User currentUser = session.getUser();
            // Перевірка валідності логіну
            if (!validationService.isLoginValid(login)) {
                redirectAttributes.addFlashAttribute("errorlogin", "Невалідний логін");
                return "redirect:/" + currentUser.getId()+"/"+currentUser.getLogin();
            }
    
            // Перевірка що новий логін не співпадає з старим
            if (currentUser.getLogin().equals(login)) {
                redirectAttributes.addFlashAttribute("errorlogin", "Ваш новий пароль співпадає зі старим");
                return "redirect:/" + currentUser.getId()+"/"+currentUser.getLogin();
            }
    
            // Перевірка унікальності нового логіну у бд
            if (!validationService.isLoginUnique(login)) {
                redirectAttributes.addFlashAttribute("errorlogin", "Логін вже існує");
                return "redirect:/" + currentUser.getId()+"/"+currentUser.getLogin();
            }

            currentUser.setLogin(login);
            //userService.save(currentUser);
            userService.update(currentUser);//баг фикс 2

        return "redirect:/" + currentUser.getId()+"/"+currentUser.getLogin();}
        return "redirect:/";
    }
    @PostMapping ("/changePassword")
    public  String updatePassword(@RequestParam String Oldpassword,@RequestParam String newpassword,RedirectAttributes redirectAttributes){
        if (session.isPresent()){
            User currentUser = session.getUser();
            // Перевірка валідності паролю
            if(!validationService.isValidPassword(newpassword)){
                redirectAttributes.addFlashAttribute("errorpassword", "Невалідний пароль");
                return "redirect:/"+currentUser.getId()+"/"+currentUser.getLogin();
            }
            // Перевірка що новий пароль не співпадає зі старим
            if (Oldpassword.equals(newpassword)){
                redirectAttributes.addFlashAttribute("errorpassword", "Ваш новий пароль співпадає зі старим");
                return "redirect:/"+ currentUser.getId()+"/"+currentUser.getLogin();
            }
            currentUser.setPassword(newpassword);
            //userService.save(currentUser);
            userService.update(currentUser);//баг фикс 2
        return "redirect:/" + currentUser.getId()+"/"+currentUser.getLogin();}
        return "redirect:/";
    }
    @PostMapping("/changeEmail")
    public String updateEmail(@RequestParam String gmail,RedirectAttributes redirectAttributes){
        if (session.isPresent()){
        User currentUser = session.getUser();
        UserDetails userDetails = currentUser.getUserDetails();
            System.out.println("Current email: " + userDetails.getUgmail());//

        // Перевірка валідності пошти
        if(!validationService.isValidEmail(gmail)){
            redirectAttributes.addFlashAttribute("erroremail", "пошта ведена некоректно");
            return "redirect:/"+currentUser.getId()+"/"+currentUser.getLogin();
        }
        // Перевірка що нова пошта не співпадає зі старою
        if(gmail.equals(userDetails.getUgmail())){
            redirectAttributes.addFlashAttribute("erroremail", "Ви вели пошту, яка було до цього");
            return "redirect:/"+currentUser.getId()+"/"+currentUser.getLogin();
        }

        if (!validationService.isEmailUnique(gmail)) {
            redirectAttributes.addFlashAttribute("erroremail", "Пошта занята іншим користувачем");
            return "redirect:/" + currentUser.getId()+"/"+currentUser.getLogin();
        }
        userDetails.setUgmail(gmail);
        //userDetailsService.save(userDetails);
        userDetailsService.update(userDetails);//баг фикс 2
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
