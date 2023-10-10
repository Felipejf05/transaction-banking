package com.example.cardapio.food;

import jakarta.persistence.*;


import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "foods")
public class Food implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 250)
    private String title;
    private String img;
    @Column(nullable = false)
    private Double price;

    public Food(){

    }

    public Food(Long id, String title, String img, Double price) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Food food)) return false;
        return Objects.equals(id, food.id) && Objects.equals(title, food.title) && Objects.equals(img, food.img) && Objects.equals(price, food.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, img, price);
    }
}
