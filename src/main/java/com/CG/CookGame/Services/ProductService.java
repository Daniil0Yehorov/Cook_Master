package com.CG.CookGame.Services;


import com.CG.CookGame.Models.Product;
import com.CG.CookGame.Repositorys.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
}