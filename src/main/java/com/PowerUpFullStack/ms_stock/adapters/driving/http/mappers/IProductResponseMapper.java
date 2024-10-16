package com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.AllCategoriesResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.AmountStockAvailableResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.PaginationResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.ProductCategoryResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.ProductResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.ProductsResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.SaleResponseDto;
import com.PowerUpFullStack.ms_stock.domain.model.AllCategories;
import com.PowerUpFullStack.ms_stock.domain.model.AmountStockAvailable;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.model.Product;
import com.PowerUpFullStack.ms_stock.domain.model.ProductCategory;
import com.PowerUpFullStack.ms_stock.domain.model.Products;
import com.PowerUpFullStack.ms_stock.domain.model.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IProductResponseMapper {
    PaginationResponseDto<ProductResponseDto> toPaginationResponseDtoFromProductResponseDto(CustomPage<Product> product);
    ProductResponseDto toProductResponseDto(Product product);
    AllCategoriesResponseDto toAllCategoriesResponseDto(AllCategories allCategories);
    AmountStockAvailableResponseDto toAmountStockAvailableResponseDto(AmountStockAvailable amountStockAvailable);
    ProductCategoryResponseDto toProductCategoryResponseDto(ProductCategory productCategory);
    List<ProductsResponseDto> toListProductsResponseDto(List<Products> products);
    SaleResponseDto toSaleResponseDto(Sale sale);
}
