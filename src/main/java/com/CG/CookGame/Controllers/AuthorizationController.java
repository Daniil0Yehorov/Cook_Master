package com.CG.CookGame.Controllers;

import com.CG.CookGame.Models.*;
import com.CG.CookGame.Repositorys.*;
import com.CG.CookGame.Services.LevelService;
import com.CG.CookGame.Services.UserService;
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
    //репозиторії ці тільки для додавання в бд даних про левели; поки адмін панелі додавання немає
    @Autowired
    ProductRepository productRepository;
    @Autowired
    DishRepository dishRepository;
    @Autowired
    LevelRepository levelRepository;
    @Autowired
    DishHaveProductsRepository dishHaveProductsRepository;
    @Autowired
    LevelService levelService;
    private void initData() {
        Product nori = new Product(1L, "Норі");
        Product rice = new Product(2L, "Рис");
        Product creamySauce = new Product(3L, "Вершковий соус");
        Product cucumber = new Product(4L, "Огірок");
        Product fish = new Product(5L, "Риба");
        productRepository.save(nori);
        productRepository.save(rice);
        productRepository.save(creamySauce);
        productRepository.save(cucumber);
        productRepository.save(fish);
        Dish philadelfia =new Dish(1L,"Філадельфія","...");
        dishRepository.save(philadelfia);
        DishHaveProducts Dhs1=new DishHaveProducts(1L,philadelfia,nori,1);
        DishHaveProducts Dhs2=new DishHaveProducts(2L,philadelfia,rice,2);
        DishHaveProducts Dhs3=new DishHaveProducts(3L,philadelfia,creamySauce,3);
        DishHaveProducts Dhs4=new DishHaveProducts(4L,philadelfia,cucumber,4);
        DishHaveProducts Dhs5=new DishHaveProducts(5L,philadelfia,fish,5);
        dishHaveProductsRepository.save(Dhs1);
        dishHaveProductsRepository.save(Dhs2);
        dishHaveProductsRepository.save(Dhs3);
        dishHaveProductsRepository.save(Dhs4);
        dishHaveProductsRepository.save(Dhs5);
        Level level1=new Level(1L,"Підказка",philadelfia,15);
        levelService.SaveLevel(level1);
        Dish californiaRoll = new Dish(2L, "Ролл Каліфорнія", "...");
        dishRepository.save(californiaRoll);
        Product avocado = new Product(6L, "Авокадо");
        Product masago = new Product(7L, "Масаго");
        productRepository.save(avocado);
        productRepository.save(masago);
        DishHaveProducts Dhs6 = new DishHaveProducts(6L, californiaRoll, nori, 1);
        DishHaveProducts Dhs7 = new DishHaveProducts(7L, californiaRoll, rice, 2);
        DishHaveProducts Dhs8 = new DishHaveProducts(8L, californiaRoll, avocado, 3);
        DishHaveProducts Dhs9 = new DishHaveProducts(9L, californiaRoll, cucumber, 4);
        DishHaveProducts Dhs10 = new DishHaveProducts(10L, californiaRoll, masago, 5);
        dishHaveProductsRepository.save(Dhs6);
        dishHaveProductsRepository.save(Dhs7);
        dishHaveProductsRepository.save(Dhs8);
        dishHaveProductsRepository.save(Dhs9);
        dishHaveProductsRepository.save(Dhs10);
        Level level2 = new Level(2L, "підказка", californiaRoll, 20);
        levelService.SaveLevel(level2);
    }

    @Autowired
    private  final HttpSession session;
    @Autowired
    private final ValidationService validationService;
    @Autowired
    private final UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password, Model model) {
        
        User user = userService.login(login);
        initData();//поки немає адмін панелі для додавання моделєй, левели закидує через цю функцію
        if (user == null) {
            model.addAttribute("error", "Логіну не існує");
        } else {
            if (!user.getPassword().equals(password)) {
                model.addAttribute("error", "Не правильний пароль");
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
        initData();//поки немає адмін панелі для додавання моделєй, левели закидує через цю функцію
        // Перевірка валідності логіну
        if (!validationService.isLoginValid(login)) {
            model.addAttribute("error", "Невалідний логін");
            return "index";
        }
        // Перевірка унікальності нового логіну у бд
        if (!validationService.isLoginUnique(login)) {
            model.addAttribute("error", "Логін вже занят");
            return "index";
        }
        if(!validationService.isValidPassword(password)){
            model.addAttribute("error", "Невалідний пароль");
            return "index";
        }
        if (!password.equals(passwordCheck)) {
            model.addAttribute("error", "Паролі не співпадають");
            return "index";
        }
        User newUser = new User(login, password);
        userService.save(newUser);
        session.setUser(newUser);
        return "redirect:/" + newUser.getId();
    }
}
