package com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response;

import java.util.List;

public record CategoryPaginationResponseDto<T>(
        List<CategoryResponseDto> content,       // Contenido de la página
        int pageNumber,        // Número de la página actual
        int pageSize,          // Tamaño de la página
        long totalElements,    // Número total de elementos
        int totalPages,        // Número total de páginas
        boolean isFirst,       // Es la primera página?
        boolean isLast
) {
}
