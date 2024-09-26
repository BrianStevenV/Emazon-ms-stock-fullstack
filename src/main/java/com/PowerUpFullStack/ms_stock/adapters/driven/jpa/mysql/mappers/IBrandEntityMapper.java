package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.mappers;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.BrandEntity;
import com.PowerUpFullStack.ms_stock.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBrandEntityMapper {

    Brand toBrand(BrandEntity brandEntity);
    BrandEntity toBrandEntity(Brand brand);
}
