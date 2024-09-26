package com.PowerUpFullStack.ms_stock.domain.usecase.utils;

import com.PowerUpFullStack.ms_stock.domain.exception.ProductAmountErrorException;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductCannotHaveMoreThanThreeCategoriesException;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductCategoryRepeatedException;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductMustHaveAtLeastOneCategoryException;
import com.PowerUpFullStack.ms_stock.domain.model.Category;
import com.PowerUpFullStack.ms_stock.domain.model.ProductAmount;
import com.PowerUpFullStack.ms_stock.domain.spi.ICategoryPersistencePort;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductUseCaseUtils {

    public static Set<Category> validateCategories(List<Long> categoryIds, ICategoryPersistencePort categoryPersistencePort) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            throw new ProductMustHaveAtLeastOneCategoryException();
        }

        if (categoryIds.size() > ConstantsDomain.CATEGORY_SIZE_LIMIT) {
            throw new ProductCannotHaveMoreThanThreeCategoriesException();
        }

        Set<Long> uniqueCategories = new HashSet<>(categoryIds);
        if (uniqueCategories.size() != categoryIds.size()) {
            throw new ProductCategoryRepeatedException();
        }

        return categoryIds.stream()
                .map(categoryPersistencePort::findById)
                .collect(Collectors.toSet());
    }

    public static void validateProductAmount(ProductAmount productAmount) {
        if (productAmount == null || productAmount.getProductId() == null) {
            throw new ProductAmountErrorException();
        }
    }

    public static int calculateNewAmount(int currentAmount, int amountChange) {
        return currentAmount + amountChange;
    }


}
