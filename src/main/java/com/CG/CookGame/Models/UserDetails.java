package com.CG.CookGame.Models;

import jakarta.persistence.*;
@Entity
public class UserDetails {
    @Id
    private Long userId;
    @Column(columnDefinition = "int default 0")
    private int level;
    private String Ugmail;
    @MapsId
    @OneToOne
    @JoinColumn(name = "userId")
    private User user;
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        Ugmail = ugmail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
