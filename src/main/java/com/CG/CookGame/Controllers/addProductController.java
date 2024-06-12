package com.CG.CookGame.Controllers;

import com.CG.CookGame.Models.Product;
import com.CG.CookGame.Models.User;
import com.CG.CookGame.Services.ProductService;
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
public class addProductController {
    @Autowired
    private HttpSession session;
    @Autowired
    private ProductService productService;

    @GetMapping("/admin/{id}/addProduct")
    public String showAddProductPage(@PathVariable Long id, Model model) {
        if (session.isPresent()) {
            User currentUser = session.getUser();
            model.addAttribute("user", currentUser);
            model.addAttribute("products", productService.getAllProducts());
            model.addAttribute("adminId", id);
            return "addProduct";
        }
        return "redirect:/";
    }

    @PostMapping("/admin/{id}/addProduct")
    public String addProduct(@PathVariable Long id, @RequestParam String name, RedirectAttributes redirectAttributes) {
        if (session.isPresent()) {
            if (name == null || name.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Назва продукту не може бути пустою");
                return "redirect:/admin/" + id + "/addProduct";
            }

            if (productService.existsByName(name)) {
                redirectAttributes.addFlashAttribute("error", "Продукт з такою назвою вже існує в базі даних!");
                return "redirect:/admin/" + id + "/addProduct";
            }

            Product product = new Product();
            product.setName(name);

            productService.addProduct(product);
            redirectAttributes.addFlashAttribute("success", "Продукт успішно доданий");
            return "redirect:/admin/" + id + "/addProduct";
        }
        return "redirect:/";
    }

}