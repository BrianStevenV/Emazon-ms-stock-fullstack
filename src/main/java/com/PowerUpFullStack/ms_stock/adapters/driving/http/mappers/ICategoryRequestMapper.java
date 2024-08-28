package com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.CategoryRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.SortDirectionRequestDto;
import com.PowerUpFullStack.ms_stock.domain.model.Category;
import com.PowerUpFullStack.ms_stock.domain.model.SortDirection;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryRequestMapper {

    Category toCategory(CategoryRequestDto categoryRequestDto);
    SortDirection toSortDirection(SortDirectionRequestDto sortDirectionRequestDto);
}
