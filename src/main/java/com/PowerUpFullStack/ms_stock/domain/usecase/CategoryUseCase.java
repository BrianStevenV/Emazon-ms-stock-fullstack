package com.PowerUpFullStack.ms_stock.domain.usecase;

import com.PowerUpFullStack.ms_stock.domain.api.ICategoryServicePort;
import com.PowerUpFullStack.ms_stock.domain.exception.CategoryDescriptionIsRequiredException;
import com.PowerUpFullStack.ms_stock.domain.exception.CategoryDescriptionIsTooLongException;
import com.PowerUpFullStack.ms_stock.domain.exception.CategoryNameAlreadyExistsException;
import com.PowerUpFullStack.ms_stock.domain.exception.CategoryNameIsRequiredException;
import com.PowerUpFullStack.ms_stock.domain.exception.CategoryNameIsTooLongException;
import com.PowerUpFullStack.ms_stock.domain.model.Category;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.model.SortDirection;
import com.PowerUpFullStack.ms_stock.domain.spi.ICategoryPersistencePort;
import com.PowerUpFullStack.ms_stock.domain.usecase.utils.ValidationDomainUtils;

import java.util.Comparator;

import static com.PowerUpFullStack.ms_stock.domain.usecase.utils.ConstantsDomain.MAX_LENGTH_CATEGORY_DESCRIPTION;
import static com.PowerUpFullStack.ms_stock.domain.usecase.utils.ConstantsDomain.MAX_LENGTH_NAME;


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

    @Override
    public CustomPage<Category> getPaginationCategoriesByAscAndDesc(SortDirection sortDirection) {
        CustomPage<Category> customPageCategory = categoryPersistencePort.getPaginationCategories();
        customPageCategory.setContent(
                ValidationDomainUtils.sortList(customPageCategory.getContent(), sortDirection, Comparator.comparing(Category::getName))
        );
        return customPageCategory;
    }

//    @Override
//    public CustomPage<Category> getPaginationCategoriesByAscAndDesc(SortDirection sortDirection) {
//        CustomPage<Category> customPageCategory = categoryPersistencePort.getPaginationCategories();
//
//        if (PAGINATION_ASC.equalsIgnoreCase(sortDirection.name()) || PAGINATION_DESC.equalsIgnoreCase(sortDirection.name())) {
//            customPageCategory.setContent(
//                    customPageCategory.getContent().stream()
//                            .sorted(PAGINATION_ASC.equalsIgnoreCase(sortDirection.name()) ?
//                                    Comparator.comparing(Category::getName) :
//                                    Comparator.comparing(Category::getName).reversed())
//                            .toList()
//            );
//        }   else {
//            throw new InvalidSortDirectionException();
//        }
//
//        return customPageCategory;
//    }




//    private Boolean categoryNameValidation(String categoryName) {
//        if(categoryName == null || categoryName.isEmpty()) {
//            throw new CategoryNameIsRequired();
//        }
//        if(categoryName.length() > MAX_LENGTH_NAME) {
//            throw new CategoryNameIsTooLongException();
//        }
//
//        Category categoryFromDatabase = getCategoryfindByName(categoryName);
//        if (categoryFromDatabase != null && categoryFromDatabase.getName().equals(categoryName)) {
//            throw new CategoryNameAlreadyExistsException();
//        }
//        return true;
//    }

//    private Boolean categoryDescriptionValidation(String categoryDescription) {
//        if(categoryDescription == null || categoryDescription.isEmpty()) {
//            throw new CategoryDescriptionIsRequiredException();
//        }
//        if(categoryDescription.length() > MAX_LENGTH_CATEGORY_DESCRIPTION) {
//            throw new CategoryDescriptionIsTooLongException();
//        }
//        return true;
//    }

    private Category getCategoryfindByName(String categoryName) {
        return categoryPersistencePort.findByName(categoryName);
    }

    private Boolean categoryNameValidation(String categoryName) {
        ValidationDomainUtils.validateName(categoryName, MAX_LENGTH_NAME,
                () -> getCategoryfindByName(categoryName),
                new CategoryNameIsRequiredException(),
                new CategoryNameIsTooLongException(),
                new CategoryNameAlreadyExistsException());
        return true;
    }

    private Boolean categoryDescriptionValidation(String categoryDescription) {
        ValidationDomainUtils.validateDescription(categoryDescription,
                MAX_LENGTH_CATEGORY_DESCRIPTION,
                new CategoryDescriptionIsRequiredException(),
                new CategoryDescriptionIsTooLongException());
        return true;
    }

}
