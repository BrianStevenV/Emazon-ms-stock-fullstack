package com.PowerUpFullStack.ms_stock.domain.model;

import java.util.List;
import java.util.Map;

public class AllCategories {
    Map<Long, List<Long>> categories;

    public AllCategories(Map<Long, List<Long>> categories) {
        this.categories = categories;
    }

    public AllCategories() {
    }

    public Map<Long, List<Long>> getCategories() {
        return categories;
    }

    public void setCategories(Map<Long, List<Long>> categories) {
        this.categories = categories;
    }
}
