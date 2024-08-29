package com.PowerUpFullStack.ms_stock.domain.spi;

import com.PowerUpFullStack.ms_stock.domain.model.Brand;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;

public interface IBrandPersistencePort {
    void saveBrand(Brand brand);
    Brand findByName(String nameBrand);
    CustomPage<Brand> getPaginationBrand();
    Brand findById(Long brandId);
}
