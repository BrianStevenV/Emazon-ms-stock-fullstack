package com.PowerUpFullStack.ms_stock.Brand;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters.BrandMySqlAdapter;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.BrandEntity;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.mappers.IBrandEntityMapper;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.repositories.IBrandRepository;
import com.PowerUpFullStack.ms_stock.domain.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-dev.yml")
@SpringBootTest
public class BrandMySqlAdapterTest {
    @Mock
    private IBrandRepository brandRepository;
    @Mock
    private IBrandEntityMapper brandEntityMapper;

    private BrandMySqlAdapter brandMySqlAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        brandMySqlAdapter = new BrandMySqlAdapter(brandRepository, brandEntityMapper);
    }

    @Test
    @DisplayName("Save brand should save brand")
    public void saveBrand_ShouldSaveBrand() {
        // Arrange
        Brand brand = new Brand();
        brand.setName("Mail");
        brand.setDescription("Platform for sending emails");
        BrandEntity brandEntity = new BrandEntity();
        when(brandEntityMapper.toBrandEntity(brand)).thenReturn(brandEntity);

        // Act
        brandMySqlAdapter.saveBrand(brand);

        // Assert
        verify(brandRepository, times(1)).save(brandEntity);
    }

    @Test
    @DisplayName("Find by name brand exists should return brand")
    public void findByName_BrandExists_ShouldReturnBrand() {
        // Arrange
        String brandName = "Mail";
        Brand brand = new Brand();
        brand.setName(brandName);
        brand.setDescription("Platform for sending emails");
        BrandEntity brandEntity = new BrandEntity();
        when(brandRepository.findByName(brandName)).thenReturn(Optional.of(brandEntity));
        when(brandEntityMapper.toBrand(brandEntity)).thenReturn(brand);

        // Act
        Brand result = brandMySqlAdapter.findByName(brandName);

        // Assert
        assertEquals(brand, result);
    }

    @Test
    @DisplayName("Find by name brand does not exist should return null")
    public void findByName_BrandDoesNotExist_ShouldReturnNull() {
        // Arrange
        String brandName = "NonExistingBrand";
        when(brandRepository.findByName(brandName)).thenReturn(Optional.empty());

        // Act
        Brand result = brandMySqlAdapter.findByName(brandName);

        // Assert
        assertEquals(null, result);
    }

    @Test
    @DisplayName("Save brand should handle exception")
    public void saveBrand_ShouldHandleException() {
        // Arrange
        Brand brand = new Brand();
        brand.setName("Mail");
        brand.setDescription("Platform for sending emails");
        BrandEntity brandEntity = new BrandEntity();
        when(brandEntityMapper.toBrandEntity(brand)).thenReturn(brandEntity);
        doThrow(new RuntimeException("Database error")).when(brandRepository).save(any(BrandEntity.class));

        // Act & Assert
        try {
            brandMySqlAdapter.saveBrand(brand);
        } catch (Exception e) {
            assertEquals("Database error", e.getMessage());
        }
    }

}
