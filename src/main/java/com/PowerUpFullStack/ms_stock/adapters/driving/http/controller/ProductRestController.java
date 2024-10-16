package com.PowerUpFullStack.ms_stock.adapters.driving.http.controller;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.AmountRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.AmountStockAvailableRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.FilterByRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.ProductIdsRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.ProductRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.ReduceQuantityRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.SortDirectionRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.AllCategoriesResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.AmountStockAvailableResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.PaginationResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.ProductCategoryResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.ProductResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.ProductsResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.SaleResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IParametersOfPaginationRequestMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IProductRequestMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IProductResponseMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.util.ProductRestControllerConstants;
import com.PowerUpFullStack.ms_stock.configuration.OpenApiConfig.OpenApiConstants;
import com.PowerUpFullStack.ms_stock.domain.api.IProductServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.PowerUpFullStack.ms_stock.adapters.driving.http.util.ProductRestControllerConstants.PRODUCT_REST_CONTROLLER_GET_PRODUCT_BY_ID_PATH_VARIABLE;

@RestController
@RequestMapping(ProductRestControllerConstants.PRODUCT_REST_CONTROLLER_BASE_PATH)
@RequiredArgsConstructor
public class ProductRestController {
    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;
    private final IProductResponseMapper productResponseMapper;
    private final IParametersOfPaginationRequestMapper parametersOfPaginationRequestMapper;

    @Operation(summary = OpenApiConstants.SUMMARY_CREATE_PRODUCT,
            responses = {
                    @ApiResponse(responseCode = OpenApiConstants.CODE_201, description = OpenApiConstants.DESCRIPTION_CREATE_PRODUCT_201,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_MAP))),
                    @ApiResponse(responseCode = OpenApiConstants.CODE_409, description = OpenApiConstants.DESCRIPTION_CREATE_PRODUCT_409,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_ERROR)))})
    @PostMapping(ProductRestControllerConstants.PRODUCT_REST_CONTROLLER_POST_CREATE_PRODUCT)
    @SecurityRequirement(name = OpenApiConstants.SECURITY_REQUIREMENT)
    public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        productServicePort.createProduct(productRequestMapper.toProduct(productRequestDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = OpenApiConstants.SUMMARY_PAGINATION_PRODUCTS,
            responses = {
                    @ApiResponse(responseCode = OpenApiConstants.CODE_200, description = OpenApiConstants.DESCRIPTION_PAGINATION_PRODUCTS_200,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_MAP))),
                    @ApiResponse(responseCode = OpenApiConstants.CODE_404, description = OpenApiConstants.DESCRIPTION_PAGINATION_PRODUCTS_404,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_ERROR)))})
    @GetMapping(ProductRestControllerConstants.PRODUCT_REST_CONTROLLER_GET_PAGINATION_PRODUCT)
    public PaginationResponseDto<ProductResponseDto>
    getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(@Valid
                                                                          @RequestParam(defaultValue = "ASC")
                                                                          SortDirectionRequestDto
                                                                                  sortDirectionRequestDto,
                                                                          @RequestParam(defaultValue = "PRODUCT")
                                                                          FilterByRequestDto
                                                                                  filterByRequestDto) {
        return productResponseMapper
                .toPaginationResponseDtoFromProductResponseDto(productServicePort
                        .getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(
                                parametersOfPaginationRequestMapper.toSortDirection(sortDirectionRequestDto),
                                parametersOfPaginationRequestMapper.toFilterBy(filterByRequestDto)
                        ));
    }



    //SAGA PATTERN
    @Operation(summary = OpenApiConstants.SUMMARY_UPDATE_AMOUNT,
            responses = {
                    @ApiResponse(responseCode = OpenApiConstants.CODE_201, description = OpenApiConstants.DESCRIPTION_UPDATE_AMOUNT_201,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_MAP))),
                    @ApiResponse(responseCode = OpenApiConstants.CODE_409, description = OpenApiConstants.DESCRIPTION_UPDATE_AMOUNT_409,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_ERROR)))})
    @PatchMapping(ProductRestControllerConstants.PRODUCT_REST_CONTROLLER_PATCH_UPDATE_AMOUNT)
    @SecurityRequirement(name = OpenApiConstants.SECURITY_REQUIREMENT)
    public ResponseEntity<Void> updateProductAmountFromSupplies(@Valid @RequestBody AmountRequestDto amountRequestDto) {

        productServicePort.updateProductAmountFromSupplies(productRequestMapper.toProductAmount(amountRequestDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = OpenApiConstants.SUMMARY_CANCEL_AMOUNT,
            responses = {
                    @ApiResponse(responseCode = OpenApiConstants.CODE_201, description = OpenApiConstants.DESCRIPTION_CANCEL_AMOUNT_201,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_MAP))),
                    @ApiResponse(responseCode = OpenApiConstants.CODE_409, description = OpenApiConstants.DESCRIPTION_CANCEL_AMOUNT_409,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_ERROR)))})
    @PatchMapping(ProductRestControllerConstants.PRODUCT_REST_CONTROLLER_PATCH_CANCEL_AMOUNT)
    @SecurityRequirement(name = OpenApiConstants.SECURITY_REQUIREMENT)
    public ResponseEntity<Void> revertProductAmountFromSupplies(@Valid @RequestBody AmountRequestDto amountRequestDto) {
        productServicePort.revertProductAmountFromSupplies(productRequestMapper.toProductAmount(amountRequestDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // Feign Client

    @Operation(summary = OpenApiConstants.SUMMARY_GET_PRODUCT_BY_ID,
            responses = {
                    @ApiResponse(responseCode = OpenApiConstants.CODE_200, description = OpenApiConstants.DESCRIPTION_GET_PRODUCT_BY_ID_200,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_MAP))),
                    @ApiResponse(responseCode = OpenApiConstants.CODE_404, description = OpenApiConstants.DESCRIPTION_GET_PRODUCT_BY_ID_404,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_ERROR)))})
    @GetMapping(ProductRestControllerConstants.PRODUCT_REST_CONTROLLER_GET_PRODUCT_BY_ID)
    @SecurityRequirement(name = OpenApiConstants.SECURITY_REQUIREMENT)
    public ResponseEntity<ProductCategoryResponseDto> getCategoryFromProductById(@PathVariable(PRODUCT_REST_CONTROLLER_GET_PRODUCT_BY_ID_PATH_VARIABLE) long productId){
        System.out.println("0");
        return ResponseEntity.ok(productResponseMapper.toProductCategoryResponseDto(productServicePort.getCategoryFromProductById(productId)));
    }

    @Operation(summary = OpenApiConstants.SUMMARY_GET_CATEGORIES_BY_PRODUCTS_IDS,
            responses = {
                    @ApiResponse(responseCode = OpenApiConstants.CODE_200, description = OpenApiConstants.DESCRIPTION_GET_CATEGORIES_BY_PRODUCTS_IDS_200,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_MAP))),
                    @ApiResponse(responseCode = OpenApiConstants.CODE_404, description = OpenApiConstants.DESCRIPTION_GET_CATEGORIES_BY_PRODUCTS_IDS_404,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_ERROR)))})
    @PostMapping(ProductRestControllerConstants.PRODUCT_REST_CONTROLLER_POST_CATEGORIES_BY_PRODUCTS_IDS)
    @SecurityRequirement(name = OpenApiConstants.SECURITY_REQUIREMENT)
    public ResponseEntity<AllCategoriesResponseDto> getCategoriesByProductIds(@RequestBody ProductIdsRequestDto productIdsRequestDto) {
        System.out.println("1");
        return ResponseEntity.ok(productResponseMapper.toAllCategoriesResponseDto(productServicePort.
                getCategoriesByProductIds(productRequestMapper.toProductIds(productIdsRequestDto))));
    }

    @Operation(summary = OpenApiConstants.SUMMARY_AMOUNT_STOCK_AVAILABLE,
            responses = {
                    @ApiResponse(responseCode = OpenApiConstants.CODE_200, description = OpenApiConstants.DESCRIPTION_AMOUNT_STOCK_AVAILABLE_200,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_MAP))),
                    @ApiResponse(responseCode = OpenApiConstants.CODE_404, description = OpenApiConstants.DESCRIPTION_AMOUNT_STOCK_AVAILABLE_404,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_ERROR)))})
    @PostMapping(ProductRestControllerConstants.PRODUCT_REST_CONTROLLER_POST_AMOUNT_STOCK_AVAILABLE)
    @SecurityRequirement(name = OpenApiConstants.SECURITY_REQUIREMENT)
    public ResponseEntity<AmountStockAvailableResponseDto> getAmountStockAvailable(@RequestBody AmountStockAvailableRequestDto amountStockAvailableRequestDto) {
        return ResponseEntity.ok(productResponseMapper.toAmountStockAvailableResponseDto(productServicePort.getAmountStockAvailable(productRequestMapper.toAmountStock(amountStockAvailableRequestDto))));
    }

    @Operation(summary = OpenApiConstants.SUMMARY_GET_PRODUCT_BY_PRODUCTS_IDS,
            responses = {
                    @ApiResponse(responseCode = OpenApiConstants.CODE_200, description = OpenApiConstants.DESCRIPTION_POST_PRODUCT_BY_PRODUCTS_IDS_200,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_MAP))),
                    @ApiResponse(responseCode = OpenApiConstants.CODE_404, description = OpenApiConstants.DESCRIPTION_POST_PRODUCT_BY_PRODUCTS_IDS_404,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_ERROR)))})
    @PostMapping(ProductRestControllerConstants.PRODUCT_REST_CONTROLLER_POST_PRODUCT_BY_PRODUCTS_IDS)
    @SecurityRequirement(name = OpenApiConstants.SECURITY_REQUIREMENT)
    public ResponseEntity<List<ProductsResponseDto>> getProductsByProductsIds(@RequestBody ProductIdsRequestDto productIdsRequestDto) {
        return ResponseEntity.ok(productResponseMapper.toListProductsResponseDto(productServicePort.getProductsByProductsIds(productRequestMapper.toProductIds(productIdsRequestDto))));
    }

    @Operation(summary = OpenApiConstants.SUMMARY_REDUCE_QUANTITY,
            responses = {
                    @ApiResponse(responseCode = OpenApiConstants.CODE_200, description = OpenApiConstants.DESCRIPTION_REDUCE_QUANTITY_200,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_MAP))),
                    @ApiResponse(responseCode = OpenApiConstants.CODE_404, description = OpenApiConstants.DESCRIPTION_REDUCE_QUANTITY_404,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_ERROR)))})
    @PostMapping(ProductRestControllerConstants.PRODUCT_REST_CONTROLLER_POST_REDUCE_QUANTITY)
    @SecurityRequirement(name = OpenApiConstants.SECURITY_REQUIREMENT)
    public ResponseEntity<SaleResponseDto> reduceStockQuantity(@RequestBody ReduceQuantityRequestDto reduceQuantityRequestDto) {
        return new ResponseEntity<>(
                productResponseMapper
                        .toSaleResponseDto(productServicePort
                                .reduceStockQuantity(productRequestMapper.toReduceQuantity(reduceQuantityRequestDto))),
                HttpStatus.OK);
    }
}
