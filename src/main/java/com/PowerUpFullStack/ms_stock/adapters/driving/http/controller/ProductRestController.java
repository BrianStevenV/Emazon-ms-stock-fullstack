package com.PowerUpFullStack.ms_stock.adapters.driving.http.controller;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.FilterByRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.ProductRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.SortDirectionRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.PaginationResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.ProductResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IParametersOfPaginationRequestMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IProductRequestMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IProductResponseMapper;
import com.PowerUpFullStack.ms_stock.domain.api.IProductServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductRestController {
    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;
    private final IProductResponseMapper productResponseMapper;
    private final IParametersOfPaginationRequestMapper parametersOfPaginationRequestMapper;

    @Operation(summary = "Add a new Product",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Product created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Product already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("/")
    public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        productServicePort.createProduct(productRequestMapper.toProduct(productRequestDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Pagination Product",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pagination product successful",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Error in pagination Product",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/pagination/")
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
}
