package com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request;

public record ProductAndQuantityRequestDto(
        Long productId,
        Integer quantity
) {
}
