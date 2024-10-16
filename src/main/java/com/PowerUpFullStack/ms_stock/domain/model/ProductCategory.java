package com.PowerUpFullStack.ms_stock.domain.model;

import java.util.List;

public class ProductCategory {
    List<Long> categories;

    public ProductCategory(List<Long> categories) {
        this.categories = categories;
    }

    public ProductCategory() {
    }

    public List<Long> getCategories() {
        return categories;
    }

    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }
}
