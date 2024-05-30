package com.CG.CookGame.Models;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    @Column(length = 256,nullable = false,unique = true)
    private String name;
    @Column(nullable = false,unique = true,length = 65535)
    private String Descr;
    @OneToMany(mappedBy="dish")
    private Set<DishHaveProducts> DHPs;
    @OneToMany(mappedBy="dish")
    private Set<Level> levels;

    public Dish(){}
    public Dish(Long id,String name,String Descr){
        this.id=id;
        this.name=name;
        this.Descr=Descr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return Descr;
    }

    public void setDescr(String descr) {
        this.Descr = descr;
    }

    public Set<DishHaveProducts> getDHPs() {
        return DHPs;
    }

    public void setDHPs(Set<DishHaveProducts> DHPs) {
        this.DHPs = DHPs;
    }

    public Set<Level> getLevels() {
        return levels;
    }

    public void setLevels(Set<Level> levels) {
        this.levels = levels;
    }
}
