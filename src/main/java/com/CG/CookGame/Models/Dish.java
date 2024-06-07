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
    @Column(unique = false,length = 65535)
    private String wikySrc;
    @Column(unique = false,length = 65535)
    private String imageSrc;

    //@Column(unique = true,length = 65535) пока Даша не кинет видосы и т.д
    @Column(length = 65535)
    private String YouTubeSrc;
    //@Column(unique = true) пока Даша не кинет видосы и т.д
    @Column(length = 65535)
    private String  VideoId;
    public Dish(){}
    public Dish(Long id,String name,String Descr,String wikySrc,String imageSrc,String YoutubeSrc,String VideoId){
        this.id=id;
        this.name=name;
        this.Descr=Descr;
        this.wikySrc=wikySrc;
        this.imageSrc=imageSrc;
        this.YouTubeSrc=YoutubeSrc;
        this.VideoId=VideoId;
    }

    public String getYouTubeSrc() {
        return YouTubeSrc;
    }

    public void setYouTubeSrc(String youTubeSrc) {
        YouTubeSrc = youTubeSrc;
    }

    public String getVideoId() {
        return VideoId;
    }

    public void setVideoId(String videoId) {
        VideoId = videoId;
    }

    public String getWikySrc() {
        return wikySrc;
    }

    public void setWikySrc(String wikySrc) {
        this.wikySrc = wikySrc;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
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
        Descr = descr;
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
