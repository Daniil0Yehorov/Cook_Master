package com.CG.CookGame.Models;

import jakarta.persistence.*;
import com.CG.CookGame.Enums.Role;
@Entity
public class User {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.AUTO)//better
    @Column(unique = true)
    private Long id;
    @Column(length = 16,nullable = false,unique = true)
    private String login;
    @Column(length=20,nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserDetails userDetails;

    public User(){}
    public  User(String login,String password){
        this.login=login;
        this.password=password;
        this.role = Role.USER_ROLE;//по стандарту юзеррол
        this.userDetails = new UserDetails();
        this.userDetails.setUser(this);
    }
    //конструктор для створення адмінів
    public  User(String login,String password,Role role){
        this.login=login;
        this.password=password;
        this.role = Role.ADMIN_ROLE;
        this.userDetails = new UserDetails();
        this.userDetails.setUser(this);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
