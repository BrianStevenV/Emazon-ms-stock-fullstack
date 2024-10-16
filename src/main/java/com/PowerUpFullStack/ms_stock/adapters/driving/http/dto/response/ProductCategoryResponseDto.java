package com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response;

import java.util.List;

public record ProductCategoryResponseDto(
        List<Long> categories
) {
}
