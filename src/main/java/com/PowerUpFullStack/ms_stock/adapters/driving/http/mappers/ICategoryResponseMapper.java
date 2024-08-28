package com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.CategoryPaginationResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.CategoryResponseDto;
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
    CategoryPaginationResponseDto<CategoryResponseDto> toCategoryPaginationResponseDto(CustomPage<Category> categoryEntity);
    CustomPage<Category> toCustomPage(CategoryPaginationResponseDto categoryPaginationResponseDto);
}
