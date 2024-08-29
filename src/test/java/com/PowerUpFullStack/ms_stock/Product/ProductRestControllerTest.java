package com.PowerUpFullStack.ms_stock.Product;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.controller.ProductRestController;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IProductRequestMapper;
import com.PowerUpFullStack.ms_stock.domain.api.IProductServicePort;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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


}
