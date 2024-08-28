package com.PowerUpFullStack.ms_stock.Brand;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.controller.BrandRestController;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IBrandRequestMapper;
import com.PowerUpFullStack.ms_stock.domain.api.IBrandServicePort;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
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
    void createBrand_ValidRequest_ShouldReturnCreated() throws Exception {
        // Arrange
        String brandRequestJson = "{\"name\":\"Electronics\",\"description\":\"Devices\"}";

        // Act & Assert
        mockMvc.perform(post("/api/v1/brand/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandRequestJson))
                .andExpect(status().isCreated());
    }

}
