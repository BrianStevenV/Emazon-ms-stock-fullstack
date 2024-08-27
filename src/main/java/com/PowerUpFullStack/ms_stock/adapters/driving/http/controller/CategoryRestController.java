package com.PowerUpFullStack.ms_stock.adapters.driving.http.controller;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.CategoryRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.ICategoryRequestMapper;
import com.PowerUpFullStack.ms_stock.domain.api.ICategoryServicePort;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryRestController {

    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;

    @Operation(summary = "Add a new Category",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Category created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Category already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("/")
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        categoryServicePort.createCategory(categoryRequestMapper.toCategory(categoryRequestDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
