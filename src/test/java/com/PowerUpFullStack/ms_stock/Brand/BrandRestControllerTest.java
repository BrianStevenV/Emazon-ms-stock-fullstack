package com.PowerUpFullStack.ms_stock.Brand;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.controller.BrandRestController;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.SortDirectionRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.BrandPaginationResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.BrandResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IBrandRequestMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IBrandResponseMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IParametersOfPaginationRequestMapper;
import com.PowerUpFullStack.ms_stock.domain.api.IBrandServicePort;
import com.PowerUpFullStack.ms_stock.domain.model.Brand;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Validated
@TestPropertySource(locations = "classpath:application-dev.yml")
@WebMvcTest(controllers = BrandRestController.class)
public class BrandRestControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    @MockBean
    private IBrandServicePort brandServicePort;
    @MockBean
    private IBrandRequestMapper brandRequestMapper;
    @MockBean
    private IBrandResponseMapper brandResponseMapper;
    @MockBean
    private IParametersOfPaginationRequestMapper parametersOfPaginationRequestMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Create Brand return Bad request")
    void createBrand_InvalidRequest_ShouldReturnBadRequest() throws Exception {
        // Arrange
        String brandRequestJson = "{\"name\":\"123@\",\"description\":\"46%5\"}";

        // Act & Assert
        mockMvc.perform(post("/api/v1/brand/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandRequestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Create Brand return Created")
    void createBrand_ValidRequest_ShouldReturnCreated() throws Exception {
        // Arrange
        String brandRequestJson = "{\"name\":\"Electronics\",\"description\":\"Devices\"}";

        // Act & Assert
        mockMvc.perform(post("/api/v1/brand/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandRequestJson))
                .andExpect(status().isCreated());
    }


    @Test
    @DisplayName("Validator Sort Direction Asc")
    void getPaginationBrands_ValidSortDirectionAsc_ShouldReturnOk() throws Exception {
        // Arrange
        BrandResponseDto brandResponseDto = new BrandResponseDto(1L, "Nike", "Sportswear");
        BrandPaginationResponseDto<BrandResponseDto> paginationResponseDto = new BrandPaginationResponseDto<>(
                List.of(brandResponseDto),
                0,
                10,
                1,
                1,
                true,
                true
        );

        CustomPage<Brand> customPage = new CustomPage<>();

        when(brandServicePort.getPaginationBrandByAscAndDesc(parametersOfPaginationRequestMapper.toSortDirection(SortDirectionRequestDto.ASC)))
                .thenReturn(customPage);

        when(brandResponseMapper.toBrandPaginationResponseDto(customPage))
                .thenReturn(paginationResponseDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/brand/pagination/")
                        .param("sortDirection", "ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Nike")))
                .andExpect(jsonPath("$.content[0].description", is("Sportswear")));
    }

    @Test
    @DisplayName("Validator Sort Direction Desc")
    void getPaginationBrands_ValidSortDirectionDesc_ShouldReturnOk() throws Exception {
        // Arrange
        BrandResponseDto brandResponseDto = new BrandResponseDto(1L, "Nike", "Sportswear");
        BrandPaginationResponseDto<BrandResponseDto> paginationResponseDto = new BrandPaginationResponseDto<>(
                List.of(brandResponseDto),
                0,
                10,
                1,
                1,
                true,
                true
        );

        CustomPage<Brand> customPage = new CustomPage<>();

        when(brandServicePort.getPaginationBrandByAscAndDesc(parametersOfPaginationRequestMapper.toSortDirection(SortDirectionRequestDto.DESC)))
                .thenReturn(customPage);

        when(brandResponseMapper.toBrandPaginationResponseDto(customPage))
                .thenReturn(paginationResponseDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/brand/pagination/")
                        .param("sortDirection", "DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Nike")))
                .andExpect(jsonPath("$.content[0].description", is("Sportswear")));
    }

    @Test
    @DisplayName("Validator Sort Direction Default")
    void getPaginationBrands_NoSortDirection_ShouldUseDefaultValue() throws Exception {
        // Arrange
        BrandResponseDto brandResponseDto = new BrandResponseDto(1L, "Nike", "Sportswear");
        BrandPaginationResponseDto<BrandResponseDto> paginationResponseDto = new BrandPaginationResponseDto<>(
                List.of(brandResponseDto),
                0,
                10,
                1,
                1,
                true,
                true
        );

        CustomPage<Brand> customPage = new CustomPage<>();

        when(brandServicePort.getPaginationBrandByAscAndDesc(parametersOfPaginationRequestMapper.toSortDirection(SortDirectionRequestDto.ASC)))
                .thenReturn(customPage);

        when(brandResponseMapper.toBrandPaginationResponseDto(customPage))
                .thenReturn(paginationResponseDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/brand/pagination/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Nike")))
                .andExpect(jsonPath("$.content[0].description", is("Sportswear")));
    }


}
