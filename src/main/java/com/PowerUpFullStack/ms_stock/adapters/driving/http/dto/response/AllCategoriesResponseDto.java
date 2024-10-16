package com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response;

import java.util.List;
import java.util.Map;

public record AllCategoriesResponseDto(
        Map<Long, List<Long>> categories
) {
}
