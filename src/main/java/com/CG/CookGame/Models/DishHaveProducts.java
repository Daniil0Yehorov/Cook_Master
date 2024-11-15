package com.CG.CookGame.Models;

import jakarta.persistence.*;

@Entity
public class DishHaveProducts {

    //связь каскад и т.д реализовать
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    @ManyToOne
    @JoinColumn(name="Dish_id", nullable=false)
    private Dish dish;
    @ManyToOne
    @JoinColumn(name="Product_id", nullable=false)
    private Product product;
    @Column(nullable = false)
    private int subsequence;
    public DishHaveProducts(){}
    public DishHaveProducts(Long id,Dish dish,Product product,int subsequence){
        this.id=id;
        this.dish=dish;
        this.product=product;
        this.subsequence=subsequence;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getSubsequence() {
        return subsequence;
    }

    public void setSubsequence(int subsequence) {
        this.subsequence = subsequence;
    }
}
