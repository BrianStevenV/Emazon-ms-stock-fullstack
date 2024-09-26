package com.PowerUpFullStack.ms_stock.Category;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.controller.CategoryRestController;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request.SortDirectionRequestDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.CategoryResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.response.PaginationResponseDto;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.ICategoryRequestMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.ICategoryResponseMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IParametersOfPaginationRequestMapper;
import com.PowerUpFullStack.ms_stock.domain.api.ICategoryServicePort;
import com.PowerUpFullStack.ms_stock.domain.model.Category;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import org.junit.jupiter.api.BeforeEach;
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

import static com.PowerUpFullStack.ms_stock.configuration.Security.utils.ConstantsSecurity.CATEGORY_CONTROLLER_GET_PAGINATION_CATEGORY;
import static com.PowerUpFullStack.ms_stock.configuration.Security.utils.ConstantsSecurity.CATEGORY_CONTROLLER_POST_CREATE_CATEGORY;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Validated
@TestPropertySource(locations = "classpath:application-dev.yml")
@WebMvcTest(controllers = CategoryRestController.class)
public class CategoryRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    @MockBean
    private ICategoryServicePort categoryServicePort;
    @MockBean
    private ICategoryRequestMapper categoryRequestMapper;
    @MockBean
    private ICategoryResponseMapper categoryResponseMapper;
    @MockBean
    private IParametersOfPaginationRequestMapper parametersOfPaginationRequestMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createCategory_InvalidRequest_ShouldReturnBadRequest() throws Exception {
        // Arrange
        String categoryRequestJson = "{\"name\":\"123@\",\"description\":\"46%5\"}";
        // Act & Assert
        mockMvc.perform(post(CATEGORY_CONTROLLER_POST_CREATE_CATEGORY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryRequestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCategory_ValidRequest_ShouldReturnCreated() throws Exception {
        // Arrange
        String categoryRequestJson = "{\"name\":\"Electronics\",\"description\":\"Devices\"}";

        // Act & Assert
        mockMvc.perform(post(CATEGORY_CONTROLLER_POST_CREATE_CATEGORY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryRequestJson))
                .andExpect(status().isCreated());
    }

    @Test
    void getPaginationCategories_ValidSortDirectionAsc_ShouldReturnOk() throws Exception {
        // Arrange
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto(1L, "Electronics", "Devices");
        PaginationResponseDto<CategoryResponseDto> paginationResponseDto = new PaginationResponseDto<>(
                List.of(categoryResponseDto),
                0,
                10,
                1,
                1,
                true,
                true
        );

        CustomPage<Category> customPage = new CustomPage<>();

        when(categoryServicePort.getPaginationCategoriesByAscAndDesc(parametersOfPaginationRequestMapper.toSortDirection(SortDirectionRequestDto.DESC)))
                .thenReturn(customPage);

        when(categoryResponseMapper.toCategoryPaginationResponseDto(customPage))
                .thenReturn(paginationResponseDto);

        // Act & Assert
        mockMvc.perform(get(CATEGORY_CONTROLLER_GET_PAGINATION_CATEGORY)
                        .param("sortDirection", "ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Electronics")))
                .andExpect(jsonPath("$.content[0].description", is("Devices")));
    }



    @Test
    void getPaginationCategories_ValidSortDirectionDesc_ShouldReturnOk() throws Exception {
        // Arrange
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto(1L, "Electronics", "Devices");
        PaginationResponseDto<CategoryResponseDto> paginationResponseDto = new PaginationResponseDto<>(
                List.of(categoryResponseDto),
                0,
                10,
                1,
                1,
                true,
                true
        );

        CustomPage<Category> customPage = new CustomPage<>();

        when(categoryServicePort.getPaginationCategoriesByAscAndDesc(parametersOfPaginationRequestMapper.toSortDirection(SortDirectionRequestDto.DESC)))
                .thenReturn(customPage);

        when(categoryResponseMapper.toCategoryPaginationResponseDto(customPage))
                .thenReturn(paginationResponseDto);

        // Act & Assert
        mockMvc.perform(get(CATEGORY_CONTROLLER_GET_PAGINATION_CATEGORY)
                        .param("sortDirection", "DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Electronics")))
                .andExpect(jsonPath("$.content[0].description", is("Devices")));
    }



    @Test
    void getPaginationCategories_NoSortDirection_ShouldUseDefaultValue() throws Exception {
        // Arrange
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto(1L, "Electronics", "Devices");
        PaginationResponseDto<CategoryResponseDto> paginationResponseDto = new PaginationResponseDto<>(
                List.of(categoryResponseDto),
                0,
                10,
                1,
                1,
                true,
                true
        );

        CustomPage<Category> customPage = new CustomPage<>();

        when(categoryServicePort.getPaginationCategoriesByAscAndDesc(parametersOfPaginationRequestMapper.toSortDirection(SortDirectionRequestDto.ASC)))
                .thenReturn(customPage);

        when(categoryResponseMapper.toCategoryPaginationResponseDto(customPage))
                .thenReturn(paginationResponseDto);

        // Act & Assert
        mockMvc.perform(get(CATEGORY_CONTROLLER_GET_PAGINATION_CATEGORY)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Electronics")))
                .andExpect(jsonPath("$.content[0].description", is("Devices")));
    }

}
