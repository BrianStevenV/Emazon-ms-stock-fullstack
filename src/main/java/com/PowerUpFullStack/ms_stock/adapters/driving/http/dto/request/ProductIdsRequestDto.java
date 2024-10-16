package com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request;

import java.util.List;

public record ProductIdsRequestDto(
        List<Long> productIds
) {
}
