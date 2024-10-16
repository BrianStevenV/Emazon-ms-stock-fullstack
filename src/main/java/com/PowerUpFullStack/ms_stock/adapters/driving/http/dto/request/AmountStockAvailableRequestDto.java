package com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request;

public record AmountStockAvailableRequestDto(
        Long productId,
        Integer amount
) {
}
