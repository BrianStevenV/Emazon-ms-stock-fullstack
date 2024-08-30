package com.PowerUpFullStack.ms_stock.Product;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.controller.ProductRestController;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.FilterByRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.SortDirectionRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.BrandResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.PaginationResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.ProductResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.ProductSetCategoryResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IParametersOfPaginationRequestMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IProductRequestMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IProductResponseMapper;
import com.PowerUpFullStack.ms_stock.domain.api.IProductServicePort;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Validated
@TestPropertySource(locations = "classpath:application-dev.yml")
@WebMvcTest(controllers = ProductRestController.class)
public class ProductRestControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    @MockBean
    private IProductServicePort productServicePort;
    @MockBean
    private IProductRequestMapper productRequestMapper;
    @MockBean
    private IProductResponseMapper productResponseMapper;
    @MockBean
    private IParametersOfPaginationRequestMapper parametersOfPaginationRequestMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createProduct_InvalidRequest_ShouldReturnBadRequest() throws Exception {
        // Arrange
        String productRequestJson = "{\"name\":\"123@\",\"description\":\"46%5\",\"amount\":-10,\"price\":-100.0,\"brandId\":0,\"categoryId\":[-1,0]}";

        // Act & Assert
        mockMvc.perform(post("/api/v1/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProduct_ValidRequest_ShouldReturnCreated() throws Exception {
        // Arrange
        String productRequestJson = "{\"name\":\"Electronics\",\"description\":\"Devices\",\"amount\":10,\"price\":100.0,\"brandId\":1,\"categoryId\":[1,2]}";

        // Act & Assert
        mockMvc.perform(post("/api/v1/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestJson))
                .andExpect(status().isCreated());
    }

    @Test
    void createProduct_InvalidPrice_ShouldReturnBadRequest() throws Exception {
        // Arrange
        String productRequestJson = "{\"name\":\"ValidName\",\"description\":\"ValidDescription\",\"amount\":10,\"price\":-100.0,\"brandId\":1,\"categoryId\":[1,2]}";

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProduct_MissingCategory_ShouldReturnBadRequest() throws Exception {
        // Arrange
        String productRequestJson = "{\"name\":\"ValidName\",\"description\":\"ValidDescription\",\"amount\":10,\"price\":100.0,\"brandId\":1,\"categoryId\":[]}";

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPaginationProducts_ValidSortDirectionAsc_ShouldReturnOk() throws Exception {
        // Arrange
        ProductResponseDto productResponseDto = new ProductResponseDto(
                1L, "Laptop", "High-end gaming laptop", 5, 1200.0,
                new BrandResponseDto(1L, "BrandX", "Top quality"),
                Set.of(new ProductSetCategoryResponseDto(1L, "Electronics"))
        );

        PaginationResponseDto<ProductResponseDto> paginationResponseDto = new PaginationResponseDto<>(
                List.of(productResponseDto),
                0,
                10,
                1,
                1,
                true,
                true
        );

        CustomPage<Product> customPage = new CustomPage<>();

        when(productServicePort.getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(parametersOfPaginationRequestMapper.toSortDirection(SortDirectionRequestDto.ASC),
                parametersOfPaginationRequestMapper.toFilterBy(FilterByRequestDto.PRODUCT)))
                .thenReturn(customPage);

        when(productResponseMapper.toPaginationResponseDtoFromProductResponseDto(customPage))
                .thenReturn(paginationResponseDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/product/pagination/")
                        .param("sortDirection", "ASC")
                        .param("filterBy", "PRODUCT")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Laptop")))
                .andExpect(jsonPath("$.content[0].description", is("High-end gaming laptop")));
    }

    @Test
    void getPaginationProducts_ValidSortDirectionDesc_ShouldReturnOk() throws Exception {
        // Arrange
        ProductResponseDto productResponseDto = new ProductResponseDto(
                1L, "Laptop", "High-end gaming laptop", 5, 1200.0,
                new BrandResponseDto(1L, "BrandX", "Top quality"),
                Set.of(new ProductSetCategoryResponseDto(1L, "Electronics"))
        );

        PaginationResponseDto<ProductResponseDto> paginationResponseDto = new PaginationResponseDto<>(
                List.of(productResponseDto),
                0,
                10,
                1,
                1,
                true,
                true
        );

        CustomPage<Product> customPage = new CustomPage<>();

        when(productServicePort.getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(parametersOfPaginationRequestMapper.toSortDirection(SortDirectionRequestDto.DESC),
                parametersOfPaginationRequestMapper.toFilterBy(FilterByRequestDto.PRODUCT)))
                .thenReturn(customPage);

        when(productResponseMapper.toPaginationResponseDtoFromProductResponseDto(customPage))
                .thenReturn(paginationResponseDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/product/pagination/")
                        .param("sortDirection", "DESC")
                        .param("filterBy", "PRODUCT")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Laptop")))
                .andExpect(jsonPath("$.content[0].description", is("High-end gaming laptop")));
    }

    @Test
    void getPaginationProducts_NoSortDirection_ShouldUseDefaultValue() throws Exception {
        // Arrange
        ProductResponseDto productResponseDto = new ProductResponseDto(
                1L, "Laptop", "High-end gaming laptop", 5, 1200.0,
                new BrandResponseDto(1L, "BrandX", "Top quality"),
                Set.of(new ProductSetCategoryResponseDto(1L, "Electronics"))
        );

        PaginationResponseDto<ProductResponseDto> paginationResponseDto = new PaginationResponseDto<>(
                List.of(productResponseDto),
                0,
                10,
                1,
                1,
                true,
                true
        );

        CustomPage<Product> customPage = new CustomPage<>();

        when(productServicePort.getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(parametersOfPaginationRequestMapper.toSortDirection(SortDirectionRequestDto.ASC),
                parametersOfPaginationRequestMapper.toFilterBy(FilterByRequestDto.PRODUCT)))
                .thenReturn(customPage);

        when(productResponseMapper.toPaginationResponseDtoFromProductResponseDto(customPage))
                .thenReturn(paginationResponseDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/product/pagination/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Laptop")))
                .andExpect(jsonPath("$.content[0].description", is("High-end gaming laptop")));
    }

    @Test
    void getPaginationProducts_ValidSortDirectionAscAndFilterByBrand_ShouldReturnOk() throws Exception {
        // Arrange
        ProductResponseDto productResponseDto = new ProductResponseDto(
                1L, "Laptop", "High-end gaming laptop", 5, 1200.0,
                new BrandResponseDto(1L, "BrandX", "Top quality"),
                Set.of(new ProductSetCategoryResponseDto(1L, "Electronics"))
        );

        PaginationResponseDto<ProductResponseDto> paginationResponseDto = new PaginationResponseDto<>(
                List.of(productResponseDto),
                0,
                10,
                1,
                1,
                true,
                true
        );

        CustomPage<Product> customPage = new CustomPage<>();

        when(productServicePort.getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(parametersOfPaginationRequestMapper.toSortDirection(SortDirectionRequestDto.ASC),
                parametersOfPaginationRequestMapper.toFilterBy(FilterByRequestDto.BRAND)))
                .thenReturn(customPage);

        when(productResponseMapper.toPaginationResponseDtoFromProductResponseDto(customPage))
                .thenReturn(paginationResponseDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/product/pagination/")
                        .param("sortDirection", "ASC")
                        .param("filterBy", "BRAND")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Laptop")))
                .andExpect(jsonPath("$.content[0].description", is("High-end gaming laptop")));
    }

    @Test
    void getPaginationProducts_ValidSortDirectionDescAndFilterByBrand_ShouldReturnOk() throws Exception {
        // Arrange
        ProductResponseDto productResponseDto = new ProductResponseDto(
                1L, "Laptop", "High-end gaming laptop", 5, 1200.0,
                new BrandResponseDto(1L, "BrandX", "Top quality"),
                Set.of(new ProductSetCategoryResponseDto(1L, "Electronics"))
        );

        PaginationResponseDto<ProductResponseDto> paginationResponseDto = new PaginationResponseDto<>(
                List.of(productResponseDto),
                0,
                10,
                1,
                1,
                true,
                true
        );

        CustomPage<Product> customPage = new CustomPage<>();

        when(productServicePort.getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(parametersOfPaginationRequestMapper.toSortDirection(SortDirectionRequestDto.DESC),
                parametersOfPaginationRequestMapper.toFilterBy(FilterByRequestDto.BRAND)))
                .thenReturn(customPage);

        when(productResponseMapper.toPaginationResponseDtoFromProductResponseDto(customPage))
                .thenReturn(paginationResponseDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/product/pagination/")
                        .param("sortDirection", "DESC")
                        .param("filterBy", "BRAND")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Laptop")))
                .andExpect(jsonPath("$.content[0].description", is("High-end gaming laptop")));
    }

    @Test
    void getPaginationProducts_ValidSortDirectionAscAndFilterByCategory_ShouldReturnOk() throws Exception {
        // Arrange
        ProductResponseDto productResponseDto = new ProductResponseDto(
                1L, "Laptop", "High-end gaming laptop", 5, 1200.0,
                new BrandResponseDto(1L, "BrandX", "Top quality"),
                Set.of(new ProductSetCategoryResponseDto(1L, "Electronics"))
        );

        PaginationResponseDto<ProductResponseDto> paginationResponseDto = new PaginationResponseDto<>(
                List.of(productResponseDto),
                0,
                10,
                1,
                1,
                true,
                true
        );

        CustomPage<Product> customPage = new CustomPage<>();

        when(productServicePort.getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(parametersOfPaginationRequestMapper.toSortDirection(SortDirectionRequestDto.ASC),
                parametersOfPaginationRequestMapper.toFilterBy(FilterByRequestDto.CATEGORY)))
                .thenReturn(customPage);

        when(productResponseMapper.toPaginationResponseDtoFromProductResponseDto(customPage))
                .thenReturn(paginationResponseDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/product/pagination/")
                        .param("sortDirection", "ASC")
                        .param("filterBy", "CATEGORY")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Laptop")))
                .andExpect(jsonPath("$.content[0].description", is("High-end gaming laptop")));
    }

    @Test
    void getPaginationProducts_ValidSortDirectionDescAndFilterByCategory_ShouldReturnOk() throws Exception {
        // Arrange
        ProductResponseDto productResponseDto = new ProductResponseDto(
                1L, "Laptop", "High-end gaming laptop", 5, 1200.0,
                new BrandResponseDto(1L, "BrandX", "Top quality"),
                Set.of(new ProductSetCategoryResponseDto(1L, "Electronics"))
        );

        PaginationResponseDto<ProductResponseDto> paginationResponseDto = new PaginationResponseDto<>(
                List.of(productResponseDto),
                0,
                10,
                1,
                1,
                true,
                true
        );

        CustomPage<Product> customPage = new CustomPage<>();

        when(productServicePort.getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(parametersOfPaginationRequestMapper.toSortDirection(SortDirectionRequestDto.DESC),
                parametersOfPaginationRequestMapper.toFilterBy(FilterByRequestDto.CATEGORY)))
                .thenReturn(customPage);

        when(productResponseMapper.toPaginationResponseDtoFromProductResponseDto(customPage))
                .thenReturn(paginationResponseDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/product/pagination/")
                        .param("sortDirection", "DESC")
                        .param("filterBy", "CATEGORY")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Laptop")))
                .andExpect(jsonPath("$.content[0].description", is("High-end gaming laptop")));
    }

}
