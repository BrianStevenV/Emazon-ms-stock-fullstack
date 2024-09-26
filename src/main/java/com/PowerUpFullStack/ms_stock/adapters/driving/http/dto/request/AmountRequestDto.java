package com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotNull;

public record AmountRequestDto(
        @NotNull
        Long productId,
        @NotNull
        Integer amount
) {
}
