package com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.AmountRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.AmountStockAvailableRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.ProductIdsRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.ProductRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.ReduceQuantityRequestDto;
import com.PowerUpFullStack.ms_stock.domain.model.AmountStock;
import com.PowerUpFullStack.ms_stock.domain.model.Product;
import com.PowerUpFullStack.ms_stock.domain.model.ProductAmount;
import com.PowerUpFullStack.ms_stock.domain.model.ProductIds;
import com.PowerUpFullStack.ms_stock.domain.model.ReduceQuantity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IProductRequestMapper {

    @Mapping(target = "brandId", source = "brandId")
    @Mapping(target = "categoryId", source = "categoryId")
    Product toProduct(ProductRequestDto productRequestDto);
    ProductAmount toProductAmount(AmountRequestDto amountRequestDto);
    ProductIds toProductIds(ProductIdsRequestDto productIdsRequestDto);
    AmountStock toAmountStock(AmountStockAvailableRequestDto amountRequestDto);
    ReduceQuantity toReduceQuantity(ReduceQuantityRequestDto reduceQuantityRequestDto);
}
