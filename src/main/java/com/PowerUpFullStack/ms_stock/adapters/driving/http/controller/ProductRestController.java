package com.PowerUpFullStack.ms_stock.adapters.driving.http.controller;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.AmountRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.FilterByRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.ProductRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.SortDirectionRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.PaginationResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.ProductResponseDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
                    @ApiResponse(responseCode = OpenApiConstants.CODE_201, description = OpenApiConstants.DESCRIPTION_PAGINATION_PRODUCTS_201,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_MAP))),
                    @ApiResponse(responseCode = OpenApiConstants.CODE_409, description = OpenApiConstants.DESCRIPTION_PAGINATION_PRODUCTS_409,
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
        System.out.println("From Controller Cancel");
        productServicePort.revertProductAmountFromSupplies(productRequestMapper.toProductAmount(amountRequestDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
