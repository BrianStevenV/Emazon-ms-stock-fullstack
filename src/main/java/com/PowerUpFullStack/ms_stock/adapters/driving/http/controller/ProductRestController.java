package com.PowerUpFullStack.ms_stock.adapters.driving.http.controller;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.ProductRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IProductRequestMapper;
import com.PowerUpFullStack.ms_stock.domain.api.IProductServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductRestController {
    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;

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
}
