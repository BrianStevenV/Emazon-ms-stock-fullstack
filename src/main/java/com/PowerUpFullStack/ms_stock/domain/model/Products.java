package com.PowerUpFullStack.ms_stock.domain.model;

import java.util.List;

public class Products {
    long id;
    String name;
    int amount;
    double price;
    String brandName;
    List<String> categoryNames;

    public Products(long id, String name, int amount, double price, String brandName, List<String> categoryNames) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.brandName = brandName;
        this.categoryNames = categoryNames;
    }

    public Products(){};

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<String> getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(List<String> categoryNames) {
        this.categoryNames = categoryNames;
    }
}
