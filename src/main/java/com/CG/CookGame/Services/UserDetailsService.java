package com.CG.CookGame.Services;

import com.CG.CookGame.Models.UserDetails;
import com.CG.CookGame.Repositorys.UserDetailsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsService {
    private final UserDetailsRepository repository;
    public UserDetails save(UserDetails userDetails){
        return repository.save(userDetails);
    }
    public Optional<UserDetails> findById(Long id) {
        return repository.findById(id);
    }
    public List<UserDetails> getAllUsers() {
        return repository.findAll();
    }

     @Transactional
    public UserDetails update(UserDetails userDetails) {
        UserDetails existingDetails = repository.findById(userDetails.getUserId()).orElseThrow(() -> new IllegalArgumentException("UserDetails not found"));

        existingDetails.setUgmail(userDetails.getUgmail());
        return repository.save(existingDetails);
    }
}
