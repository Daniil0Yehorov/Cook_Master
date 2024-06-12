package com.CG.CookGame.Repositorys;
import com.CG.CookGame.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {
    User findByLogin(String login);
    boolean existsByLogin(String login);
}
