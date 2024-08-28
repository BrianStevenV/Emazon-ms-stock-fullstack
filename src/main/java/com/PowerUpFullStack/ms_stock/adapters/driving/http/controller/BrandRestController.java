package com.PowerUpFullStack.ms_stock.adapters.driving.http.controller;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.BrandRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IBrandRequestMapper;
import com.PowerUpFullStack.ms_stock.domain.api.IBrandServicePort;
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
@RequestMapping("/api/v1/brand")
@RequiredArgsConstructor
public class BrandRestController {
    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;

    @Operation(summary = "Add a new Brand",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Brand created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Brand already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("/")
    public ResponseEntity<Void> createBrand(@Valid @RequestBody BrandRequestDto brandRequestDto) {
        brandServicePort.createBrand(brandRequestMapper.toBrand(brandRequestDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
