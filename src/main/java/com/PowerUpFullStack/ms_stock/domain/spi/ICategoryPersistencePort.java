package com.PowerUpFullStack.ms_stock.domain.spi;

import com.PowerUpFullStack.ms_stock.domain.model.Category;


public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    Category findByName(String nameCategory);
}
