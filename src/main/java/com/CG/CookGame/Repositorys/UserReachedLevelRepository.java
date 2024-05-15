package com.CG.CookGame.Repositorys;

import com.CG.CookGame.Models.UserDetails;
import com.CG.CookGame.Models.UserReachedLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReachedLevelRepository extends JpaRepository<UserReachedLevel,Long> {
    UserReachedLevel findByUserDetailsUserId(Long userId);
    UserReachedLevel findByUserDetails(UserDetails userDetails);
}
