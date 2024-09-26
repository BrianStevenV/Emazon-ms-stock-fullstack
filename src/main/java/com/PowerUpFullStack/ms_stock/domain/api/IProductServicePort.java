package com.PowerUpFullStack.ms_stock.domain.api;

import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.model.FilterBy;
import com.PowerUpFullStack.ms_stock.domain.model.Product;
import com.PowerUpFullStack.ms_stock.domain.model.ProductAmount;
import com.PowerUpFullStack.ms_stock.domain.model.SortDirection;

public interface IProductServicePort {
    void createProduct(Product product);
    CustomPage<Product> getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(SortDirection
                                                                                                      sortDirection,
                                                                                              FilterBy
                                                                                                      filterBy);

    void updateProductAmountFromSupplies(ProductAmount productAmount);

    void revertProductAmountFromSupplies(ProductAmount productAmount);
}
