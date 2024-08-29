package com.PowerUpFullStack.ms_stock.domain.api;

import com.PowerUpFullStack.ms_stock.domain.model.Product;

public interface IProductServicePort {
    void createProduct(Product product);
}
