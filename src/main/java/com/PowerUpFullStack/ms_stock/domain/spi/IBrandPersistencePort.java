package com.PowerUpFullStack.ms_stock.domain.spi;

import com.PowerUpFullStack.ms_stock.domain.model.Brand;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;

import java.util.Optional;

public interface IBrandPersistencePort {
    void saveBrand(Brand brand);
    Brand findByName(String nameBrand);
    CustomPage<Brand> getPaginationBrand();
    Optional<Brand> findById(Long brandId);
}
