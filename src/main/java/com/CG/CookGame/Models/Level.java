package com.CG.CookGame.Models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(length = 65535,unique = true)
    private String Hint;
    @Column
    private int PointsPerLevel;
    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;
    @OneToMany(mappedBy="level")
    private Set<UserReachedLevel> URLs;

    public  Level (){}
    public Level (Long id,String Hint,Dish dish,int PointsPerLevel){
        this.id=id;
        this.Hint=Hint;
        this.dish=dish;
        this.PointsPerLevel=PointsPerLevel;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHint() {
        return Hint;
    }

    public void setHint(String hint) {
        this.Hint = hint;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getPointsPerLevel() {
        return PointsPerLevel;
    }

    public void setPointsPerLevel(int pointsPerLevel) {
        this.PointsPerLevel = pointsPerLevel;
    }

    public Set<UserReachedLevel> getURLs() {
        return URLs;
    }

    public void setURLs(Set<UserReachedLevel> URLs) {
        this.URLs = URLs;
    }
    public Long getDishId() {
        if (dish != null) {
            return dish.getId();
        }
        return null;
    }
}
