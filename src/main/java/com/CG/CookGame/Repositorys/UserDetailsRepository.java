package com.CG.CookGame.Repositorys;

import com.CG.CookGame.Models.User;
import com.CG.CookGame.Models.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserDetailsRepository extends JpaRepository<UserDetails,Long> {
    @Query("SELECT u FROM UserDetails u WHERE u.Ugmail = :email")
    UserDetails findByEmail(@Param("email") String email);
}
