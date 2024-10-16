package com.PowerUpFullStack.ms_stock.domain.spi;

import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.model.Product;
import com.PowerUpFullStack.ms_stock.domain.model.ProductIds;

import java.util.List;
import java.util.Optional;

public interface IProductPersistencePort {
    void saveProduct(Product product);
    void saveProducts(List<Product> products);
    CustomPage<Product> getPaginationProduct();
    Optional<Product> getProductById(Long id);

    List<Product> getProductsByProductIds(ProductIds productIds);

}
