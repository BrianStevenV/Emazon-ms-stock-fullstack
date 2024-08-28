package com.PowerUpFullStack.ms_stock.domain.api;

import com.PowerUpFullStack.ms_stock.domain.model.Category;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.model.SortDirection;

public interface ICategoryServicePort {
    void createCategory(Category category);
    CustomPage<Category> getPaginationCategoriesByAscAndDesc(SortDirection sortDirection);
}
