package com.PowerUpFullStack.ms_stock.domain.spi;

import com.PowerUpFullStack.ms_stock.domain.model.Brand;

public interface IBrandPersistencePort {
    void saveBrand(Brand brand);
    Brand findByName(String nameBrand);
}
