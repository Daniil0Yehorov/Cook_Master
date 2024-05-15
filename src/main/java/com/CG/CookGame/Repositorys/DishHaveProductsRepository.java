package com.CG.CookGame.Repositorys;

import com.CG.CookGame.Models.Dish;
import com.CG.CookGame.Models.DishHaveProducts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishHaveProductsRepository extends JpaRepository<DishHaveProducts,Long> {
    List<DishHaveProducts> findByDish(Dish dish);
}
