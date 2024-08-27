package com.PowerUpFullStack.ms_stock.domain.api;

import com.PowerUpFullStack.ms_stock.domain.model.Category;

public interface ICategoryServicePort {
    void createCategory(Category category);
}
