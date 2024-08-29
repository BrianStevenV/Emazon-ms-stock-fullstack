package com.PowerUpFullStack.ms_stock.domain.spi;

import com.PowerUpFullStack.ms_stock.domain.model.Category;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;


public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    Category findByName(String nameCategory);
    CustomPage<Category> getPaginationCategories();
    Category findById(Long categoryId);
}
