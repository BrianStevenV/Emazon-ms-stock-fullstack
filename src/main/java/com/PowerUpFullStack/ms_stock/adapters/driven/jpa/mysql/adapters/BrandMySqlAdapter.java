package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.mappers.IBrandEntityMapper;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.repositories.IBrandRepository;
import com.PowerUpFullStack.ms_stock.domain.model.Brand;
import com.PowerUpFullStack.ms_stock.domain.spi.IBrandPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BrandMySqlAdapter implements IBrandPersistencePort {
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    @Override
    public void saveBrand(Brand brand) {
        brandRepository.save(brandEntityMapper.toBrandEntity(brand));
    }

    @Override
    public Brand findByName(String nameBrand) {
        return brandRepository.findByName(nameBrand)
                .map(brandEntityMapper::toBrand)
                .orElse(null);
    }
}
