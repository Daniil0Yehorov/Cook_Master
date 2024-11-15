package com.CG.CookGame.Models;

import jakarta.persistence.*;

@Entity
public class UserReachedLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    @ManyToOne
    @JoinColumn(name="Level_id", nullable=false)
    private Level level;
    @ManyToOne
    @JoinColumn(name="UserDetails_user_id", nullable=false)
    private UserDetails userDetails;

    public  UserReachedLevel(){}
    public  UserReachedLevel(Long id,Level level,UserDetails userDetails){
        this.id=id;
        this.level=level;
        this.userDetails=userDetails;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
