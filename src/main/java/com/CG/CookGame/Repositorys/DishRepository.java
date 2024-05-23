package com.CG.CookGame.Repositorys;

import com.CG.CookGame.Models.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish,Long> {
    Optional<Dish> findById(Long id);
    List<Dish> findAllByIdIn(List<Long> ids);
}
