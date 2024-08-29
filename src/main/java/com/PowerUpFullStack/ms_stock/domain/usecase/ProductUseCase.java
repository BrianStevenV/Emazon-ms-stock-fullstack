package com.PowerUpFullStack.ms_stock.domain.usecase;

import com.PowerUpFullStack.ms_stock.domain.api.IProductServicePort;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductCannotHaveMoreThanThreeCategoriesException;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductCategoryRepeatedException;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductMustHaveAtLeastOneCategoryException;
import com.PowerUpFullStack.ms_stock.domain.model.Brand;
import com.PowerUpFullStack.ms_stock.domain.model.Category;
import com.PowerUpFullStack.ms_stock.domain.model.Product;
import com.PowerUpFullStack.ms_stock.domain.spi.IBrandPersistencePort;
import com.PowerUpFullStack.ms_stock.domain.spi.ICategoryPersistencePort;
import com.PowerUpFullStack.ms_stock.domain.spi.IProductPersistencePort;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductUseCase implements IProductServicePort {
    private final IProductPersistencePort productPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IBrandPersistencePort brandPersistencePort;

    public ProductUseCase(IProductPersistencePort productPersistencePort,
                          ICategoryPersistencePort categoryPersistencePort,
                          IBrandPersistencePort brandPersistencePort) {
        this.productPersistencePort = productPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void createProduct(Product product) {
        product.setCategories(validateCategories(product.getCategoryId()));
        product.setBrand(brandFindById(product.getBrandId()));

        productPersistencePort.saveProduct(product);
    }


    private Set<Category> validateCategories(List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            throw new ProductMustHaveAtLeastOneCategoryException();
        }

        if (categoryIds.size() > 3) {
            throw new ProductCannotHaveMoreThanThreeCategoriesException();
        }

        Set<Long> uniqueCategories = new HashSet<>(categoryIds);
        if (uniqueCategories.size() != categoryIds.size()) {
            throw new ProductCategoryRepeatedException();
        }

        return categoryIds.stream()
                .map(this::categoryFindById)
                .collect(Collectors.toSet());
    }


    private Category categoryFindById(Long categoryId) {
        return categoryPersistencePort.findById(categoryId);
    }

    private Brand brandFindById(Long brandId) {
        return brandPersistencePort.findById(brandId);
    }
}
