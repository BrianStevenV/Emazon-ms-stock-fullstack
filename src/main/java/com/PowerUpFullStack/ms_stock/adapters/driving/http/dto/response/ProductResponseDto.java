package com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response;

import java.util.Set;

public record ProductResponseDto(
        Long id,
        String name,
        String description,
        Integer amount,
        Double price,
        BrandResponseDto brand,
        Set<ProductSetCategoryResponseDto> categories

) {
}
