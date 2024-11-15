package com.CG.CookGame.Models;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Set;

@Entity
public class UserDetails {
    @Id
    private Long userId;
   @Column(columnDefinition = "int default 1")
    private int level;
    @Column(length = 255,unique = true)
    private String Ugmail;
    @MapsId
    @OneToOne
    @JoinColumn(name = "userId")
    private User user;
    @OneToMany(mappedBy="userDetails")
    private Set<UserReachedLevel> URLs;

    @Column(columnDefinition = "int default 0")
    private int points;
    public  UserDetails (){}

    public Set<UserReachedLevel> getURLs() {
        return URLs;
    }

    public void setURLs(Set<UserReachedLevel> URLs) {
        this.URLs = URLs;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getUgmail() {
        return Ugmail;
    }

    public void setUgmail(String ugmail) {
        this.Ugmail = ugmail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
