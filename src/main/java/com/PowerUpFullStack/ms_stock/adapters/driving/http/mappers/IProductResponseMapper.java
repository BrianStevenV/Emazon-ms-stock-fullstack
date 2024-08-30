package com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.PaginationResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.ProductResponseDto;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IProductResponseMapper {
    PaginationResponseDto<ProductResponseDto> toPaginationResponseDtoFromProductResponseDto(CustomPage<Product> product);
}
