package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.mappers;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.CategoryEntity;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.CategoryPaginationResponseDto;
import com.PowerUpFullStack.ms_stock.domain.model.Category;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryEntityMapper {
    Category toCategory(CategoryEntity categoryEntity);
    CategoryEntity toCategoryEntity(Category category);
    CategoryPaginationResponseDto toCategoryPaginationResponseDto(CategoryEntity categoryEntity);
    CustomPage toCustomPage(CategoryEntity categoryEntity);
}
