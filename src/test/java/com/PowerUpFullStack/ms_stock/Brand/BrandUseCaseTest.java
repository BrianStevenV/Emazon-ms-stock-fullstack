package com.PowerUpFullStack.ms_stock.Brand;

import com.PowerUpFullStack.ms_stock.domain.exception.BrandDescriptionIsRequiredException;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandDescriptionIsTooLongException;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandNameAlreadyExistsException;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandNameIsRequiredException;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandNameIsTooLongException;
import com.PowerUpFullStack.ms_stock.domain.model.Brand;
import com.PowerUpFullStack.ms_stock.domain.spi.IBrandPersistencePort;
import com.PowerUpFullStack.ms_stock.domain.usecase.BrandUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-dev.yml")
@SpringBootTest
public class BrandUseCaseTest {
    @Mock
    private IBrandPersistencePort brandPersistencePort;
    private BrandUseCase brandUseCase;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        brandUseCase = new BrandUseCase(brandPersistencePort);
    }

    @Test
    @DisplayName("Create brand with success")
    void testCreateBrandSuccess() {
        // Arrange
        Brand brand = new Brand();
        brand.setName("Valid Name");
        brand.setDescription("Valid Description");
        when(brandPersistencePort.findByName(brand.getName())).thenReturn(null);

        // Act & Assert
        assertDoesNotThrow(() -> brandUseCase.createBrand(brand));
        verify(brandPersistencePort, times(1)).saveBrand(brand);
    }

    @Test
    @DisplayName("Create brand with Null Name")
    void testCreateBrandWithNullName() {
        // Arrange
        Brand brand = new Brand();
        brand.setName(null);
        brand.setDescription("Valid Description");

        // Act & Assert
        assertThrows(BrandNameIsRequiredException.class, () -> brandUseCase.createBrand(brand));
    }

    @Test
    @DisplayName("Create brand with Empty Name")
    void testCreateBrandWithEmptyName() {
        // Arrange
        Brand brand = new Brand();
        brand.setName("");
        brand.setDescription("Valid Description");

        // Act & Assert
        assertThrows(BrandNameIsRequiredException.class, () -> brandUseCase.createBrand(brand));
    }

    @Test
    @DisplayName("Create brand with Name too long")
    void testCreateBrandWithNameTooLong() {
        // Arrange
        String longName = "a".repeat(51);
        Brand brand = new Brand();
        brand.setName(longName);
        brand.setDescription("Valid Description");

        // Act & Assert
        assertThrows(BrandNameIsTooLongException.class, () -> brandUseCase.createBrand(brand));
    }

    @Test
    @DisplayName("Create brand with Null Description")
    void testCreateBrandWithNullDescription() {
        // Arrange
        Brand brand = new Brand();
        brand.setName("Valid Name");
        brand.setDescription(null);

        // Act & Assert
        assertThrows(BrandDescriptionIsRequiredException.class, () -> brandUseCase.createBrand(brand));
    }

    @Test
    @DisplayName("Create brand with Empty Description")
    void testCreateBrandWithEmptyDescription() {
        // Arrange
        Brand brand = new Brand();
        brand.setName("Valid Name");
        brand.setDescription("");

        // Act & Assert
        assertThrows(BrandDescriptionIsRequiredException.class, () -> brandUseCase.createBrand(brand));
    }

    @Test
    @DisplayName("Create brand with Description too long")
    void testCreateBrandWithDescriptionTooLong() {
        // Arrange
        String longDescription = "a".repeat(121);
        Brand brand = new Brand();
        brand.setName("Valid Name");
        brand.setDescription(longDescription);

        // Act & Assert
        assertThrows(BrandDescriptionIsTooLongException.class, () -> brandUseCase.createBrand(brand));
    }

    @Test
    @DisplayName("Create brand with existing name")
    void testCreateBrandWithExistingName() {
        // Arrange
        Brand existingBrand = new Brand();
        existingBrand.setName("Existing Name");
        existingBrand.setDescription("Existing Description");
        when(brandPersistencePort.findByName(existingBrand.getName())).thenReturn(existingBrand);

        // Act & Assert
        assertThrows(BrandNameAlreadyExistsException.class, () -> brandUseCase.createBrand(existingBrand));
    }

}
