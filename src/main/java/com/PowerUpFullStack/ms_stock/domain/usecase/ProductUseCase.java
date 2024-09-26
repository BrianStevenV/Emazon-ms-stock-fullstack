package com.PowerUpFullStack.ms_stock.domain.usecase;

import com.PowerUpFullStack.ms_stock.domain.api.IProductServicePort;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandNotFoundException;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductNotFoundException;
import com.PowerUpFullStack.ms_stock.domain.model.Brand;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.model.FilterBy;
import com.PowerUpFullStack.ms_stock.domain.model.Product;
import com.PowerUpFullStack.ms_stock.domain.model.ProductAmount;
import com.PowerUpFullStack.ms_stock.domain.model.SortDirection;
import com.PowerUpFullStack.ms_stock.domain.spi.IBrandPersistencePort;
import com.PowerUpFullStack.ms_stock.domain.spi.ICategoryPersistencePort;
import com.PowerUpFullStack.ms_stock.domain.spi.IProductPersistencePort;
import com.PowerUpFullStack.ms_stock.domain.usecase.utils.ProductUseCaseUtils;
import com.PowerUpFullStack.ms_stock.domain.usecase.utils.ValidationDomainUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.PowerUpFullStack.ms_stock.domain.usecase.utils.ConstantsDomain.COMPARISON_RESULT_INIT;
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
        product.setCategories(ProductUseCaseUtils.validateCategories(product.getCategoryId(), categoryPersistencePort));
        product.setBrand(brandFindById(product.getBrandId()));

        productPersistencePort.saveProduct(product);
    }

    @Override
    public CustomPage<Product> getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(SortDirection sortDirection, FilterBy filterBy) {
        CustomPage<Product> customPage = productPersistencePort.getPaginationProduct();

        List<Product> products = customPage.getContent();

        List<Product> sortedProducts = products.stream()
                .sorted((p1, p2) -> {
                    int comparisonResult = COMPARISON_RESULT_INIT;

                    switch (filterBy) {
                        case PRODUCT:
                            comparisonResult = p1.getName().compareToIgnoreCase(p2.getName());
                            break;
                        case BRAND:
                            comparisonResult = p1.getBrand().getName().compareToIgnoreCase(p2.getBrand().getName());
                            break;
                        case CATEGORY:
                            comparisonResult = ValidationDomainUtils.compareCategories(p1.getCategories(), p2.getCategories());
                            break;
                    }

                    return sortDirection == SortDirection.ASC ? comparisonResult : -comparisonResult;
                })
                .collect(Collectors.toList());

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

    @Override
    public void updateProductAmountFromSupplies(ProductAmount productAmount) {
        ProductUseCaseUtils.validateProductAmount(productAmount);
        updateProductAmount(productAmount.getProductId(), productAmount.getAmount());
    }

    @Override
    public void revertProductAmountFromSupplies(ProductAmount productAmount) {
        ProductUseCaseUtils.validateProductAmount(productAmount);
        updateProductAmount(productAmount.getProductId(), -productAmount.getAmount());
    }

    private void updateProductAmount(Long productId, int amountChange) {
        Product product = productFindById(productId);
        product.setAmount(ProductUseCaseUtils.calculateNewAmount(product.getAmount(), amountChange));
        productPersistencePort.saveProduct(product);
    }

    private Product productFindById(Long productId) {
        return productPersistencePort.getProductById(productId).orElseThrow(ProductNotFoundException::new);
    }

    private Brand brandFindById(Long brandId) {
        return brandPersistencePort.findById(brandId).orElseThrow(BrandNotFoundException::new);
    }

}

