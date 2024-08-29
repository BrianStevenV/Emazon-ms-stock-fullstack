package com.PowerUpFullStack.ms_stock.Product;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters.ProductMySqlAdapter;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.ProductEntity;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.mappers.IProductEntityMapper;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.repositories.IProductRepository;
import com.PowerUpFullStack.ms_stock.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-dev.yml")
@SpringBootTest
public class ProductMySqlAdapterTest {
    @MockBean
    private IProductRepository productRepository;
    @MockBean
    private IProductEntityMapper productEntityMapper;

    private ProductMySqlAdapter productMySqlAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productMySqlAdapter = new ProductMySqlAdapter(productRepository, productEntityMapper);
    }

    @Test
    void saveProduct_ValidProduct_ShouldSaveProductEntity() {
        // Arrange
        Product product = new Product(
                1L, "ProductName", "ProductDescription", 10, 100.0, 1L, List.of(1L)
        );
        ProductEntity productEntity = new ProductEntity(
                1L, "ProductName", "ProductDescription", 10, 100.0, null, null
        );

        when(productEntityMapper.toProductEntity(product)).thenReturn(productEntity);

        // Act
        productMySqlAdapter.saveProduct(product);

        // Assert
        verify(productEntityMapper).toProductEntity(product);
        verify(productRepository).save(productEntity);
    }

    @Test
    void saveProduct_EmptyProduct_ShouldSaveProductEntity() {
        // Arrange
        Product product = new Product();
        ProductEntity productEntity = new ProductEntity();

        when(productEntityMapper.toProductEntity(product)).thenReturn(productEntity);

        // Act
        productMySqlAdapter.saveProduct(product);

        // Assert
        verify(productEntityMapper).toProductEntity(product);
        verify(productRepository).save(productEntity);
    }

    @Test
    void saveProduct_ProductWithNullValues_ShouldSaveProductEntity() {
        // Arrange
        Product product = new Product(
                1L, null, null, null, null, null, List.of()
        );
        ProductEntity productEntity = new ProductEntity(
                1L, null, null, null, null, null, null
        );

        when(productEntityMapper.toProductEntity(product)).thenReturn(productEntity);

        // Act
        productMySqlAdapter.saveProduct(product);

        // Assert
        verify(productEntityMapper).toProductEntity(product);
        verify(productRepository).save(productEntity);
    }
}
