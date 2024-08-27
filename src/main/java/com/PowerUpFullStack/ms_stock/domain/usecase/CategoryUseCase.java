package com.PowerUpFullStack.ms_stock.domain.usecase;

import com.PowerUpFullStack.ms_stock.domain.api.ICategoryServicePort;
import com.PowerUpFullStack.ms_stock.domain.exception.*;
import com.PowerUpFullStack.ms_stock.domain.model.Category;
import com.PowerUpFullStack.ms_stock.domain.spi.ICategoryPersistencePort;


public class CategoryUseCase implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void createCategory(Category category) {
        if(categoryNameValidation(category.getName()) && categoryDescriptionValidation(category.getDescription())) {
            categoryPersistencePort.saveCategory(category);
        }
    }

    private Boolean categoryNameValidation(String categoryName) {
        if(categoryName == null || categoryName.isEmpty()) {
            throw new CategoryNameIsRequired();
        }
        if(categoryName.length() > 50) {
            throw new CategoryNameIsTooLongException();
        }

        Category categoryFromDatabase = getCategoryfindByName(categoryName);
        if (categoryFromDatabase != null && categoryFromDatabase.getName().equals(categoryName)) {
            throw new CategoryNameAlreadyExistsException();
        }
        return true;
    }

    private Boolean categoryDescriptionValidation(String categoryDescription) {
        if(categoryDescription == null || categoryDescription.isEmpty()) {
            throw new CategoryDescriptionIsRequiredException();
        }
        if(categoryDescription.length() > 90) {
            throw new CategoryDescriptionIsTooLongException();
        }
        return true;
    }

    private Category getCategoryfindByName(String categoryName) {
        return categoryPersistencePort.findByName(categoryName);
    }
}
