package com.PowerUpFullStack.ms_stock.domain.usecase;

import com.PowerUpFullStack.ms_stock.domain.api.IProductServicePort;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductCannotHaveMoreThanThreeCategoriesException;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductCategoryRepeatedException;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductMustHaveAtLeastOneCategoryException;
import com.PowerUpFullStack.ms_stock.domain.model.Brand;
import com.PowerUpFullStack.ms_stock.domain.model.Category;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.model.FilterBy;
import com.PowerUpFullStack.ms_stock.domain.model.Product;
import com.PowerUpFullStack.ms_stock.domain.model.SortDirection;
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

    @Override
    public CustomPage<Product> getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(SortDirection sortDirection, FilterBy filterBy) {
        CustomPage<Product> customPage = productPersistencePort.getPaginationProduct();

        // Obtener la lista de productos
        List<Product> products = customPage.getContent();

        // Ordenar la lista de productos según el criterio y la dirección proporcionados
        List<Product> sortedProducts = products.stream()
                .sorted((p1, p2) -> {
                    int comparisonResult = 0;

                    switch (filterBy) {
                        case PRODUCT:
                            comparisonResult = p1.getName().compareToIgnoreCase(p2.getName());
                            break;
                        case BRAND:
                            comparisonResult = p1.getBrand().getName().compareToIgnoreCase(p2.getBrand().getName());
                            break;
                        case CATEGORY:
                            comparisonResult = compareCategories(p1.getCategories(), p2.getCategories());
                            break;
                    }

                    return sortDirection == SortDirection.ASC ? comparisonResult : -comparisonResult;
                })
                .collect(Collectors.toList());

        // Crear un nuevo CustomPage con los productos ordenados
        return new CustomPage<>(
                sortedProducts,
                customPage.getPageNumber(),
                customPage.getPageSize(),
                customPage.getTotalElements(),
                customPage.getTotalPages(),
                customPage.isFirst(),
                customPage.isLast()
        );
    }

    private int compareCategories(Set<Category> categories1, Set<Category> categories2) {
        // Implement a comparison logic for categories
        // For simplicity, let's compare the size of the sets
        return Integer.compare(categories1.size(), categories2.size());
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
