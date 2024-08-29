package com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.ProductRequestDto;
import com.PowerUpFullStack.ms_stock.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IProductRequestMapper {

    @Mapping(target = "brandId", source = "brandId")
    @Mapping(target = "categoryId", source = "categoryId")
    Product toProduct(ProductRequestDto productRequestDto);
}
