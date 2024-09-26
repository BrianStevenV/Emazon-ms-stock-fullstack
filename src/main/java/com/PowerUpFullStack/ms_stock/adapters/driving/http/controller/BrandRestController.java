package com.PowerUpFullStack.ms_stock.adapters.driving.http.controller;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.BrandRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.SortDirectionRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.BrandResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.PaginationResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IBrandRequestMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IBrandResponseMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IParametersOfPaginationRequestMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.util.BrandRestControllerConstants;
import com.PowerUpFullStack.ms_stock.configuration.OpenApiConfig.OpenApiConstants;
import com.PowerUpFullStack.ms_stock.domain.api.IBrandServicePort;
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
@RequestMapping(BrandRestControllerConstants.BRAND_REST_CONTROLLER_BASE_PATH)
@RequiredArgsConstructor
public class BrandRestController {
    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;
    private final IBrandResponseMapper brandResponseMapper;
    private final IParametersOfPaginationRequestMapper parametersOfPaginationRequestMapper;

    @Operation(summary = OpenApiConstants.SUMMARY_CREATE_BRAND,
            responses = {
                    @ApiResponse(responseCode = OpenApiConstants.CODE_201, description = OpenApiConstants.DESCRIPTION_CREATE_BRAND_201,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_MAP))),
                    @ApiResponse(responseCode = OpenApiConstants.CODE_409, description = OpenApiConstants.DESCRIPTION_CREATE_BRAND_409,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_ERROR)))})
    @PostMapping(BrandRestControllerConstants.BRAND_REST_CONTROLLER_POST_CREATE_BRAND)
    @SecurityRequirement(name = OpenApiConstants.SECURITY_REQUIREMENT)
    public ResponseEntity<Void> createBrand(@Valid @RequestBody BrandRequestDto brandRequestDto) {
        brandServicePort.createBrand(brandRequestMapper.toBrand(brandRequestDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = OpenApiConstants.SUMMARY_PAGINATION_BRANDS,
            responses = {
                    @ApiResponse(responseCode = OpenApiConstants.CODE_201, description = OpenApiConstants.DESCRIPTION_PAGINATION_BRANDS_201,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_MAP))),
                    @ApiResponse(responseCode = OpenApiConstants.CODE_409, description = OpenApiConstants.DESCRIPTION_PAGINATION_BRANDS_409,
                            content = @Content(mediaType = OpenApiConstants.APPLICATION_JSON, schema = @Schema(ref = OpenApiConstants.SCHEMAS_ERROR)))})
    @GetMapping(BrandRestControllerConstants.BRAND_REST_CONTROLLER_GET_PAGINATION_BRAND)
    public PaginationResponseDto<BrandResponseDto> getPaginationBrandByAscAndDesc(@Valid @RequestParam(defaultValue = "ASC") SortDirectionRequestDto sortDirectionRequestDto) {
        return brandResponseMapper
                .toBrandPaginationResponseDto(brandServicePort
                        .getPaginationBrandByAscAndDesc(parametersOfPaginationRequestMapper
                                .toSortDirection(sortDirectionRequestDto)));
    }
}
