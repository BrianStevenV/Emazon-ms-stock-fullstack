package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.mappers.IProductEntityMapper;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.repositories.IProductRepository;
import com.PowerUpFullStack.ms_stock.domain.model.Product;
import com.PowerUpFullStack.ms_stock.domain.spi.IProductPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductMySqlAdapter implements IProductPersistencePort{
    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;

    @Override
    public void saveProduct(Product product) {
        productRepository.save(productEntityMapper.toProductEntity(product));

    }
}
