package com.CG.CookGame.Services;
import com.CG.CookGame.Enums.*;
import com.CG.CookGame.Models.*;
import com.CG.CookGame.Repositorys.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
@AllArgsConstructor
public class LevelService {
  @Autowired private final LevelRepository levelRepository;
  @Autowired private final DishHaveProductsRepository dishHaveProductsRepository;
  @Autowired private final UserDetailsRepository userDetailsRepository;
  @Autowired private final UserReachedLevelRepository userReachedLevelRepository;
  @Autowired private final UserDetailsService userDetailsService;

  public Level getLevelById(Long levelId) {
    return levelRepository.findById(levelId).orElse(null);
  }

  public Dish getDishByLevel(Level level) {
    return level.getDish();
  }
  //отримання послідовності до страви
  public Map<Product, Integer> getProductsAndSubsequencesByDish(Dish dish) {
    List<DishHaveProducts> dishHaveProductsList = dishHaveProductsRepository.findByDish(dish);

    Map<Product, Integer> productSequenceMap = new HashMap<>();

    for (DishHaveProducts dishHaveProducts : dishHaveProductsList) {
      Product product = dishHaveProducts.getProduct();
      int sequence = dishHaveProducts.getSubsequence();
      productSequenceMap.put(product, sequence);
    }
    return productSequenceMap;
  }

  public List<Level> getAllLevels(){
    return  levelRepository.findAll();
  }
  public void addScore(Long id,int score){
    Optional<UserDetails> userDetailsOptional = userDetailsService.findById(id);
    if (userDetailsOptional.isPresent()) {
      UserDetails userDetails = userDetailsOptional.get();
      userDetails.setPoints(userDetails.getPoints() + score);
      userDetailsService.save(userDetails);
    }
    else {}
  }
public void ReachedTheLevel(Long Userid, Long lvlId){
  Optional<UserDetails> userDetailsOptional = userDetailsService.findById(Userid);
  UserReachedLevel userReachedLevel = userReachedLevelRepository.findByUserDetailsUserId(Userid);

  if (userDetailsOptional.isPresent()) {
    UserDetails userDetails = userDetailsOptional.get();

    userDetails.setLevel(userDetails.getLevel() + 1);
    userDetailsRepository.save(userDetails);

    // Перевірка існування наступного левела
    Optional<Level> nextLevelOptional = levelRepository.findById(lvlId + 1);
    if (nextLevelOptional.isPresent()) {
      Level nextLevel = nextLevelOptional.get();
      if (userReachedLevel == null) {
        // створення нового запису об
        userReachedLevel = new UserReachedLevel();
        userReachedLevel.setLevel(nextLevel);
        userReachedLevel.setUserDetails(userDetails);
      } else {
        // Обновляем запись о достижении уровня
        userReachedLevel.setLevel(nextLevel);
      }
      userReachedLevelRepository.save(userReachedLevel);
    }
  }
}
  // Під час додавання нового рівня оновлюватиметься таблиця UserReachedLevel юзерів, якщо
  // вони досягли максимального рівня до оновлення
  public void SaveLevel(Level newLevel) {
    Level savedLevel = levelRepository.save(newLevel);
    // Отримаємо теперешній макс левел
    int maxLevel = savedLevel.getId().intValue();
    // Оновлюваєм дані для усіх користувачів, які пройшли на тот момент усі рівні
    List<UserDetails> allUsers = userDetailsService.getAllUsers();
    for (UserDetails userDetails : allUsers) {
      // Перевірка, чи пройшла людина останній рівень
      int currentUserLevel = userDetails.getLevel();
      if (currentUserLevel == maxLevel - 1) {
        // Перевіряємо, чи існує запис про досягнутий рівень користувача
        UserReachedLevel userReachedLevel = userReachedLevelRepository.findByUserDetailsUserId(userDetails.getUserId());
        if (userReachedLevel != null) {
          // Оновлюємо досягнутий рівень користувача
          userReachedLevel.setLevel(savedLevel);
          userReachedLevelRepository.save(userReachedLevel);
        } else {
          // Якщо запису немає, то створюємо новий
          userReachedLevel = new UserReachedLevel();
          userReachedLevel.setUserDetails(userDetails);
          //userReachedLevel.setLevel(savedLevel);//причина бага 1
          userReachedLevel.setLevel(levelRepository.getOne(1L));
          userReachedLevelRepository.save(userReachedLevel);
        }
      }
    }
  }

}
