package com.example.currency_exchange.model;

import java.math.BigDecimal;
import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private ItemCategory category;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public void setCategory(ItemCategory category) {
        this.category = category;
    }
    // Item.java
    public Item(String name, BigDecimal price, String category) {
        this.name = name;
        this.price = price;
        this.category = category != null ? ItemCategory.valueOf(category.toUpperCase()) : null;
    }
    // Optionally, add a no-args constructor for JPA
    public Item() {
    }
    // Getters, setters, constructors
}