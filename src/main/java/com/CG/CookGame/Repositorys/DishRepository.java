package com.CG.CookGame.Repositorys;

import com.CG.CookGame.Models.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish,Long> {

}
