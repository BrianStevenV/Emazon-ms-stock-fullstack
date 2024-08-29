package com.PowerUpFullStack.ms_stock.domain.api;

import com.PowerUpFullStack.ms_stock.domain.model.Brand;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.model.SortDirection;

public interface IBrandServicePort {
    void createBrand(Brand brand);
    CustomPage<Brand> getPaginationBrandByAscAndDesc(SortDirection sortDirection);
}
