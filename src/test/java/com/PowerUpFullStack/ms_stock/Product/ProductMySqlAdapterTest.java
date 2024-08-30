package com.PowerUpFullStack.ms_stock.Product;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters.ProductMySqlAdapter;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.ProductEntity;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.exceptions.ResourcesNotFoundException;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.mappers.IProductEntityMapper;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.repositories.IProductRepository;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Test
    void getPaginationProduct_ValidProducts_ShouldReturnCustomPage() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        ProductEntity productEntity = new ProductEntity(
                1L, "ProductName", "ProductDescription", 10, 100.0, null, null
        );
        Page<ProductEntity> productEntityPage = new PageImpl<>(List.of(productEntity), pageable, 1);

        Product product = new Product(
                1L, "ProductName", "ProductDescription", 10, 100.0, 1L, List.of(1L)
        );

        when(productRepository.findAllWithCategories(pageable)).thenReturn(productEntityPage);
        when(productEntityMapper.toProduct(productEntity)).thenReturn(product);

        // Act
        CustomPage<Product> result = productMySqlAdapter.getPaginationProduct();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("ProductName", result.getContent().get(0).getName());
        verify(productRepository).findAllWithCategories(pageable);
    }

    @Test
    void getPaginationProduct_EmptyProducts_ShouldThrowResourcesNotFoundException() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductEntity> emptyProductEntityPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(productRepository.findAllWithCategories(pageable)).thenReturn(emptyProductEntityPage);

        // Act & Assert
        assertThrows(ResourcesNotFoundException.class, () -> productMySqlAdapter.getPaginationProduct());
        verify(productRepository).findAllWithCategories(pageable);
    }

    @Test
    void getPaginationProduct_PageHasMultipleProducts_ShouldReturnCorrectCustomPage() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        ProductEntity productEntity1 = new ProductEntity(
                1L, "ProductName1", "ProductDescription1", 10, 100.0, null, null
        );
        ProductEntity productEntity2 = new ProductEntity(
                2L, "ProductName2", "ProductDescription2", 20, 200.0, null, null
        );
        Page<ProductEntity> productEntityPage = new PageImpl<>(List.of(productEntity1, productEntity2), pageable, 2);

        Product product1 = new Product(
                1L, "ProductName1", "ProductDescription1", 10, 100.0, 1L, List.of(1L)
        );
        Product product2 = new Product(
                2L, "ProductName2", "ProductDescription2", 20, 200.0, 2L, List.of(2L)
        );

        when(productRepository.findAllWithCategories(pageable)).thenReturn(productEntityPage);
        when(productEntityMapper.toProduct(productEntity1)).thenReturn(product1);
        when(productEntityMapper.toProduct(productEntity2)).thenReturn(product2);

        // Act
        CustomPage<Product> result = productMySqlAdapter.getPaginationProduct();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("ProductName1", result.getContent().get(0).getName());
        assertEquals("ProductName2", result.getContent().get(1).getName());
        assertEquals(0, result.getPageNumber());
        assertEquals(10, result.getPageSize());
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertTrue(result.isFirst());
        assertTrue(result.isLast());
        verify(productRepository).findAllWithCategories(pageable);
    }

    @Test
    void getPaginationProduct_PageIsLastPage_ShouldReturnIsLastTrue() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        ProductEntity productEntity = new ProductEntity(
                1L, "ProductName", "ProductDescription", 10, 100.0, null, null
        );
        Page<ProductEntity> productEntityPage = new PageImpl<>(List.of(productEntity), pageable, 1);

        Product product = new Product(
                1L, "ProductName", "ProductDescription", 10, 100.0, 1L, List.of(1L)
        );

        when(productRepository.findAllWithCategories(pageable)).thenReturn(productEntityPage);
        when(productEntityMapper.toProduct(productEntity)).thenReturn(product);

        // Act
        CustomPage<Product> result = productMySqlAdapter.getPaginationProduct();

        // Assert
        assertTrue(result.isLast());
        verify(productRepository).findAllWithCategories(pageable);
    }

    @Test
    void getPaginationProduct_PageIsFirstPage_ShouldReturnIsFirstTrue() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        ProductEntity productEntity = new ProductEntity(
                1L, "ProductName", "ProductDescription", 10, 100.0, null, null
        );
        Page<ProductEntity> productEntityPage = new PageImpl<>(List.of(productEntity), pageable, 1);

        Product product = new Product(
                1L, "ProductName", "ProductDescription", 10, 100.0, 1L, List.of(1L)
        );

        when(productRepository.findAllWithCategories(pageable)).thenReturn(productEntityPage);
        when(productEntityMapper.toProduct(productEntity)).thenReturn(product);

        // Act
        CustomPage<Product> result = productMySqlAdapter.getPaginationProduct();

        // Assert
        assertTrue(result.isFirst());
        verify(productRepository).findAllWithCategories(pageable);
    }
}
