package com.PowerUpFullStack.ms_stock.domain.api;

import com.PowerUpFullStack.ms_stock.domain.model.AllCategories;
import com.PowerUpFullStack.ms_stock.domain.model.AmountStock;
import com.PowerUpFullStack.ms_stock.domain.model.AmountStockAvailable;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.model.FilterBy;
import com.PowerUpFullStack.ms_stock.domain.model.Product;
import com.PowerUpFullStack.ms_stock.domain.model.ProductAmount;
import com.PowerUpFullStack.ms_stock.domain.model.ProductCategory;
import com.PowerUpFullStack.ms_stock.domain.model.ProductIds;
import com.PowerUpFullStack.ms_stock.domain.model.Products;
import com.PowerUpFullStack.ms_stock.domain.model.ReduceQuantity;
import com.PowerUpFullStack.ms_stock.domain.model.Sale;
import com.PowerUpFullStack.ms_stock.domain.model.SortDirection;

import java.util.List;

public interface IProductServicePort {
    void createProduct(Product product);
    CustomPage<Product> getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(SortDirection
                                                                                                      sortDirection,
                                                                                              FilterBy
                                                                                                      filterBy);

    void updateProductAmountFromSupplies(ProductAmount productAmount);

    void revertProductAmountFromSupplies(ProductAmount productAmount);

    ProductCategory getCategoryFromProductById(long id);
    AllCategories getCategoriesByProductIds(ProductIds productIds);
    AmountStockAvailable getAmountStockAvailable(AmountStock amountStock);
    List<Products> getProductsByProductsIds(ProductIds productIds);

    Sale reduceStockQuantity(ReduceQuantity reduceQuantity);
}
