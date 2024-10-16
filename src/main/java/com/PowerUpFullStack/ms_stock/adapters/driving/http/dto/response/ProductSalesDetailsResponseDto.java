package com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response;

public record ProductSalesDetailsResponseDto(
        long productId,
        int amount,
        double price
) {
}
