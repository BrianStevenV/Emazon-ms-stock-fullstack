package com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response;

import java.util.List;

public record PaginationResponseDto<T>(
        List<ProductResponseDto> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean isFirst,
        boolean isLast
) {
}
