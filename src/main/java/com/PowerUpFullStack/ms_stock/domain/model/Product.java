package com.PowerUpFullStack.ms_stock.domain.model;

import java.util.List;
import java.util.Set;

public class Product {
    private Long id;
    private String name;
    private String description;
    private Integer amount;
    private Double price;

    private Long brandId;
    private List<Long> categoryId;

    private Brand brand;
    private Set<Category> categories;


    public Product() {}

    public Product(Long id, String name, String description, Integer amount, Double price, Long brandId, List<Long> categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.price = price;
        this.brandId = brandId;
        this.categoryId = categoryId;
    }

    public Product(Long id, String name, String description, Integer amount, Double price, Brand brand, Set<Category> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.price = price;
        this.brand = brand;
        this.categories = categories;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public List<Long> getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(List<Long> categoryId) {
        this.categoryId = categoryId;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
