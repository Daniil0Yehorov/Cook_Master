package com.CG.CookGame.Repositorys;

import com.CG.CookGame.Models.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LevelRepository extends JpaRepository<Level,Long> {
    List<Level> findAllByIdIn(List<Long> ids);
    List<Level> findByIdLessThanEqual(Long id);
}

