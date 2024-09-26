package com.PowerUpFullStack.ms_stock.adapters.driving.http.controller;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.CategoryRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.SortDirectionRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.CategoryResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.PaginationResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.ICategoryRequestMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.ICategoryResponseMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IParametersOfPaginationRequestMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.util.CategoryRestControllerConstants;
import com.PowerUpFullStack.ms_stock.configuration.OpenApiConfig.OpenApiConstants;
import com.PowerUpFullStack.ms_stock.domain.api.ICategoryServicePort;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(CategoryRestControllerConstants.CATEGORY_REST_CONTROLLER_BASE_PATH)
public class CategoryRestController {

    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;
    private final ICategoryResponseMapper categoryResponseMapper;
    private final IParametersOfPaginationRequestMapper parametersOfPaginationRequestMapper;

    @Operation(summary = OpenApiConstants.SUMMARY_CREATE_CATEGORY,
            responses = {
                    @ApiResponse(responseCode = OpenApiConstants.CODE_201, description = OpenApiConstants.DESCRIPTION_CREATE_CATEGORY_201,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref =OpenApiConstants.SCHEMAS_MAP))),
                    @ApiResponse(responseCode = OpenApiConstants.CODE_409, description = OpenApiConstants.DESCRIPTION_CREATE_CATEGORY_409,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_ERROR)))})
    @PostMapping(CategoryRestControllerConstants.CATEGORY_REST_CONTROLLER_POST_CREATE_CATEGORY)
    @SecurityRequirement(name = OpenApiConstants.SECURITY_REQUIREMENT)
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        categoryServicePort.createCategory(categoryRequestMapper.toCategory(categoryRequestDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = OpenApiConstants.SUMMARY_PAGINATION_CATEGORIES,
            responses = {
                    @ApiResponse(responseCode = OpenApiConstants.CODE_201, description = OpenApiConstants.DESCRIPTION_PAGINATION_CATEGORIES_201,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_MAP))),
                    @ApiResponse(responseCode = OpenApiConstants.CODE_409, description = OpenApiConstants.DESCRIPTION_PAGINATION_CATEGORIES_409,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_ERROR)))})
    @GetMapping(CategoryRestControllerConstants.CATEGORY_REST_CONTROLLER_GET_PAGINATION_CATEGORY)
    public PaginationResponseDto<CategoryResponseDto> getPaginationCategoriesByAscAndDesc(@Valid @RequestParam(defaultValue = "ASC") SortDirectionRequestDto sortDirectionRequestDto){
        return categoryResponseMapper
                .toCategoryPaginationResponseDto(categoryServicePort
                        .getPaginationCategoriesByAscAndDesc(parametersOfPaginationRequestMapper
                                .toSortDirection(sortDirectionRequestDto)));
    }
}
