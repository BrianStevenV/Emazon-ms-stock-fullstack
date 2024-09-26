package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.mappers;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.ProductEntity;
import com.PowerUpFullStack.ms_stock.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IProductEntityMapper {
    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "categories", source = "categories")
    Product toProduct(ProductEntity productEntity);

    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "categories", source = "categories")
    ProductEntity toProductEntity(Product product);
}
