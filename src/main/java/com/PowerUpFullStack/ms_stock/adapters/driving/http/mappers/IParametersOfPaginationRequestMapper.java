package com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.FilterByRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.SortDirectionRequestDto;
import com.PowerUpFullStack.ms_stock.domain.model.FilterBy;
import com.PowerUpFullStack.ms_stock.domain.model.SortDirection;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IParametersOfPaginationRequestMapper {
    SortDirection toSortDirection(SortDirectionRequestDto sortDirectionRequestDto);
    FilterBy toFilterBy(FilterByRequestDto filterByRequestDto);
}
