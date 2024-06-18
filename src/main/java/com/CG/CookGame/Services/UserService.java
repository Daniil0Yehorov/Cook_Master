package com.CG.CookGame.Services;

import com.CG.CookGame.Enums.Role;
import com.CG.CookGame.Models.User;
import com.CG.CookGame.Models.UserDetails;
import com.CG.CookGame.Repositorys.UserDetailsRepository;
import com.CG.CookGame.Repositorys.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public User login(String login) {
        return repository.findByLogin(login);
    }
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }
    public User update(User user) {
        User existingUser = repository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        existingUser.setLogin(user.getLogin());
        existingUser.setPassword(user.getPassword());
        return repository.save(existingUser);
    }
}


