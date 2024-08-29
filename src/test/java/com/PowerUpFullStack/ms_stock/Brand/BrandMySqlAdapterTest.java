package com.PowerUpFullStack.ms_stock.Brand;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters.BrandMySqlAdapter;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.BrandEntity;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.exceptions.ResourcesNotFoundException;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.mappers.IBrandEntityMapper;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.repositories.IBrandRepository;
import com.PowerUpFullStack.ms_stock.domain.model.Brand;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        brand.setName("Electronics");
        brand.setDescription("Devices");
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

    @Test
    @DisplayName("Return Custom Page")
    public void getPaginationBrands_ShouldReturnCustomPage() {
        // Arrange
        BrandEntity brandEntity = new BrandEntity();
        Brand brand = new Brand(1L, "Nike", "Sportswear");
        Pageable pageable = PageRequest.of(0, 10);
        Page<BrandEntity> page = new PageImpl<>(List.of(brandEntity), pageable, 1);

        when(brandRepository.findAll(pageable)).thenReturn(page);
        when(brandEntityMapper.toBrand(brandEntity)).thenReturn(brand);

        // Act
        CustomPage<Brand> result = brandMySqlAdapter.getPaginationBrand();

        // Assert
        assertEquals(0, page.getNumber());
        assertEquals(10, page.getSize());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertTrue(result.isFirst());
        assertTrue(result.isLast());
        assertEquals(1, result.getContent().size());
    }

    @Test
    @DisplayName("Get pagination brands no brands should throw exception")
    public void getPaginationBrands_NoBrands_ShouldThrowException() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<BrandEntity> page = new PageImpl<>(List.of(), pageable, 0);

        when(brandRepository.findAll(pageable)).thenReturn(page);

        // Act & Assert
        assertThrows(ResourcesNotFoundException.class, () -> {
            brandMySqlAdapter.getPaginationBrand();
        });
    }


}
