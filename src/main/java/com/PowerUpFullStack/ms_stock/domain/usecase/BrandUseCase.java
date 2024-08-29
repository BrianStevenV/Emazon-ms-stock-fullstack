package com.PowerUpFullStack.ms_stock.domain.usecase;

import com.PowerUpFullStack.ms_stock.domain.api.IBrandServicePort;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandDescriptionIsRequiredException;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandDescriptionIsTooLongException;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandNameAlreadyExistsException;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandNameIsRequiredException;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandNameIsTooLongException;
import com.PowerUpFullStack.ms_stock.domain.exception.InvalidSortDirectionException;
import com.PowerUpFullStack.ms_stock.domain.model.Brand;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.model.SortDirection;
import com.PowerUpFullStack.ms_stock.domain.spi.IBrandPersistencePort;

import java.util.Comparator;

public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void createBrand(Brand brand) {
        if(brandNameValidation(brand.getName()) && brandDescriptionValidation(brand.getDescription())) {
            brandPersistencePort.saveBrand(brand);
        }

    }

    @Override
    public CustomPage<Brand> getPaginationBrandByAscAndDesc(SortDirection sortDirection) {
        CustomPage<Brand> customPageBrand = brandPersistencePort.getPaginationBrand();
        if ("ASC".equalsIgnoreCase(sortDirection.name()) || "DESC".equalsIgnoreCase(sortDirection.name())) {
            customPageBrand.setContent(
                    customPageBrand.getContent().stream()
                            .sorted("ASC".equalsIgnoreCase(sortDirection.name()) ?
                                    Comparator.comparing(Brand::getName) :
                                    Comparator.comparing(Brand::getName).reversed())
                            .toList()
            );
        }   else {
            throw new InvalidSortDirectionException();
        }
        return customPageBrand;
    }


    private Boolean brandDescriptionValidation(String brandDescription) {
        if(brandDescription == null || brandDescription.isEmpty()) {
            throw new BrandDescriptionIsRequiredException();
        }
        if(brandDescription.length() > 120) {
            throw new BrandDescriptionIsTooLongException();
        }
        return true;
    }

    private Boolean brandNameValidation(String brandName) {
        if(brandName == null || brandName.isEmpty()) {
            throw new BrandNameIsRequiredException();
        }
        if(brandName.length() > 50) {
            throw new BrandNameIsTooLongException();
        }

        Brand brandFromDatabase = getBrandFindByName(brandName);
        if(brandFromDatabase != null && brandFromDatabase.getName().equals(brandName)) {
            throw new BrandNameAlreadyExistsException();
        }
        return true;
    }

    private Brand getBrandFindByName(String name) {
        return brandPersistencePort.findByName(name);
    }
}


