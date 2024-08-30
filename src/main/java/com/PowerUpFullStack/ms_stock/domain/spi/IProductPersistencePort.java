package com.PowerUpFullStack.ms_stock.domain.spi;

import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.model.Product;

public interface IProductPersistencePort {
    void saveProduct(Product product);
    CustomPage<Product> getPaginationProduct(); //TODO: FUnciones pequenas o funcion completa, cambiar nombre.
}
