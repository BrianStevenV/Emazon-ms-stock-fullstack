package com.PowerUpFullStack.ms_stock.Product;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.exceptions.ResourcesNotFoundException;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductCannotHaveMoreThanThreeCategoriesException;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductCategoryRepeatedException;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductMustHaveAtLeastOneCategoryException;
import com.PowerUpFullStack.ms_stock.domain.model.Brand;
import com.PowerUpFullStack.ms_stock.domain.model.Category;
import com.PowerUpFullStack.ms_stock.domain.model.Product;
import com.PowerUpFullStack.ms_stock.domain.spi.IBrandPersistencePort;
import com.PowerUpFullStack.ms_stock.domain.spi.ICategoryPersistencePort;
import com.PowerUpFullStack.ms_stock.domain.spi.IProductPersistencePort;
import com.PowerUpFullStack.ms_stock.domain.usecase.ProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-dev.yml")
@SpringBootTest
public class ProductUseCaseTest {
    @Mock
    private IProductPersistencePort productPersistencePort;
    @Mock
    private ICategoryPersistencePort categoryPersistencePort;
    @Mock
    private IBrandPersistencePort brandPersistencePort;

    private ProductUseCase productUseCase;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        productUseCase = new ProductUseCase(productPersistencePort, categoryPersistencePort, brandPersistencePort);
    }

    @Test
    void createProduct_ValidInput_ShouldSaveProduct() {
        // Arrange
        Category category = new Category();
        Brand brand = new Brand();
        Product product = new Product(
                1L,
                "ProductName",
                "ProductDescription",
                10,
                100.0,
                1L,
                List.of(1L)
        );

        when(categoryPersistencePort.findById(1L)).thenReturn(category);
        when(brandPersistencePort.findById(1L)).thenReturn(brand);

        doNothing().when(productPersistencePort).saveProduct(any(Product.class));

        // Act
        productUseCase.createProduct(product);

        // Assert
        verify(productPersistencePort, times(1)).saveProduct(product);
    }


    @Test
    void createProduct_NoCategories_ShouldThrowException() {
        // Arrange
        Product product = new Product();
        product.setCategoryId(List.of());

        // Act & Assert
        assertThrows(ProductMustHaveAtLeastOneCategoryException.class, () -> productUseCase.createProduct(product));
    }

    @Test
    void createProduct_MoreThanThreeCategories_ShouldThrowException() {
        // Arrange
        Product product = new Product();
        product.setCategoryId(List.of(1L, 2L, 3L, 4L)); // More than three categories

        // Act & Assert
        assertThrows(ProductCannotHaveMoreThanThreeCategoriesException.class, () -> productUseCase.createProduct(product));
    }

    @Test
    void createProduct_DuplicateCategories_ShouldThrowException() {
        // Arrange
        Product product = new Product();
        product.setCategoryId(List.of(1L, 1L, 2L)); // Duplicate category IDs

        // Act & Assert
        assertThrows(ProductCategoryRepeatedException.class, () -> productUseCase.createProduct(product));
    }

    @Test
    void createProduct_InvalidCategory_ShouldThrowException() {
        // Arrange
        Product product = new Product();
        product.setCategoryId(List.of(1L));

        when(categoryPersistencePort.findById(1L)).thenThrow(ResourcesNotFoundException.class);

        // Act & Assert
        assertThrows(ResourcesNotFoundException.class, () -> productUseCase.createProduct(product));
    }

    @Test
    void createProduct_InvalidBrand_ShouldThrowException() {
        // Arrange
        Product product = new Product();
        product.setBrandId(1L);

        when(brandPersistencePort.findById(1L)).thenThrow(ProductMustHaveAtLeastOneCategoryException.class);

        // Act & Assert
        assertThrows(ProductMustHaveAtLeastOneCategoryException.class, () -> productUseCase.createProduct(product));
    }
}
