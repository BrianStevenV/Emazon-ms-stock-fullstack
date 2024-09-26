package com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.CategoryResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.PaginationResponseDto;
import com.PowerUpFullStack.ms_stock.domain.model.Category;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryResponseMapper {
    CategoryResponseDto toCategoryResponseDto(Category category);
    Category toCategory(CategoryResponseDto categoryResponseDto);
    PaginationResponseDto<CategoryResponseDto> toCategoryPaginationResponseDto(CustomPage<Category> categoryEntity);
    CustomPage<Category> toCustomPage(PaginationResponseDto categoryPaginationResponseDto);
}
