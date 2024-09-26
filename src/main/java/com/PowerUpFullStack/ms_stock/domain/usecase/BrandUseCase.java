package com.PowerUpFullStack.ms_stock.domain.usecase;

import com.PowerUpFullStack.ms_stock.domain.api.IBrandServicePort;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandDescriptionIsRequiredException;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandDescriptionIsTooLongException;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandNameAlreadyExistsException;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandNameIsRequiredException;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandNameIsTooLongException;
import com.PowerUpFullStack.ms_stock.domain.model.Brand;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.model.SortDirection;
import com.PowerUpFullStack.ms_stock.domain.spi.IBrandPersistencePort;
import com.PowerUpFullStack.ms_stock.domain.usecase.utils.ValidationDomainUtils;

import java.util.Comparator;

import static com.PowerUpFullStack.ms_stock.domain.usecase.utils.ConstantsDomain.MAX_LENGTH_BRAND_DESCRIPTION;
import static com.PowerUpFullStack.ms_stock.domain.usecase.utils.ConstantsDomain.MAX_LENGTH_NAME;

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
        customPageBrand.setContent(
                ValidationDomainUtils.sortList(customPageBrand.getContent(), sortDirection, Comparator.comparing(Brand::getName))
        );
        return customPageBrand;
    }

//    @Override
//    public CustomPage<Brand> getPaginationBrandByAscAndDesc(SortDirection sortDirection) {
//        CustomPage<Brand> customPageBrand = brandPersistencePort.getPaginationBrand();
//        if (PAGINATION_ASC.equalsIgnoreCase(sortDirection.name()) || PAGINATION_DESC.equalsIgnoreCase(sortDirection.name())) {
//            customPageBrand.setContent(
//                    customPageBrand.getContent().stream()
//                            .sorted(PAGINATION_ASC.equalsIgnoreCase(sortDirection.name()) ?
//                                    Comparator.comparing(Brand::getName) :
//                                    Comparator.comparing(Brand::getName).reversed())
//                            .toList()
//            );
//        }   else {
//            throw new InvalidSortDirectionException();
//        }
//        return customPageBrand;
//    }


//    private Boolean brandDescriptionValidation(String brandDescription) {
//        if(brandDescription == null || brandDescription.isEmpty()) {
//            throw new BrandDescriptionIsRequiredException();
//        }
//        if(brandDescription.length() > MAX_LENGTH_BRAND_DESCRIPTION) {
//            throw new BrandDescriptionIsTooLongException();
//        }
//        return true;
//    }
//
//    private Boolean brandNameValidation(String brandName) {
//        if(brandName == null || brandName.isEmpty()) {
//            throw new BrandNameIsRequiredException();
//        }
//        if(brandName.length() > MAX_LENGTH_NAME) {
//            throw new BrandNameIsTooLongException();
//        }
//
//        Brand brandFromDatabase = getBrandFindByName(brandName);
//        if(brandFromDatabase != null && brandFromDatabase.getName().equals(brandName)) {
//            throw new BrandNameAlreadyExistsException();
//        }
//        return true;
//    }
    private Boolean brandNameValidation(String brandName) {
        ValidationDomainUtils.validateName(brandName, MAX_LENGTH_NAME,
                () -> getBrandFindByName(brandName),
                new BrandNameIsRequiredException(),
                new BrandNameIsTooLongException(),
                new BrandNameAlreadyExistsException());
        return true;
    }

    private Boolean brandDescriptionValidation(String brandDescription) {
        ValidationDomainUtils.validateDescription(brandDescription,
                MAX_LENGTH_BRAND_DESCRIPTION,
                new BrandDescriptionIsRequiredException(),
                new BrandDescriptionIsTooLongException());
        return true;
    }

    private Brand getBrandFindByName(String name) {
        return brandPersistencePort.findByName(name);
    }
}


