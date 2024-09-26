package com.PowerUpFullStack.ms_stock.Product;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.exceptions.ResourcesNotFoundException;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductCannotHaveMoreThanThreeCategoriesException;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductCategoryRepeatedException;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductMustHaveAtLeastOneCategoryException;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductNotFoundException;
import com.PowerUpFullStack.ms_stock.domain.model.Brand;
import com.PowerUpFullStack.ms_stock.domain.model.Category;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.model.FilterBy;
import com.PowerUpFullStack.ms_stock.domain.model.Product;
import com.PowerUpFullStack.ms_stock.domain.model.ProductAmount;
import com.PowerUpFullStack.ms_stock.domain.model.SortDirection;
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
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
        when(brandPersistencePort.findById(1L)).thenReturn(Optional.of(brand));

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

    //Test write here

    @Test
    void getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories_SortByProductNameAsc_ShouldReturnSortedProducts() {
        // Arrange
        Product product1 = new Product(1L, "Apple", "Description1", 10, 100.0, 1L, List.of(1L));
        Product product2 = new Product(2L, "Banana", "Description2", 20, 200.0, 2L, List.of(2L));

        CustomPage<Product> customPage = new CustomPage<>(List.of(product2, product1), 0, 10, 2, 1, true, true);
        when(productPersistencePort.getPaginationProduct()).thenReturn(customPage);

        // Act
        CustomPage<Product> result = productUseCase.getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(SortDirection.ASC, FilterBy.PRODUCT);

        // Assert
        assertEquals("Apple", result.getContent().get(0).getName());
        assertEquals("Banana", result.getContent().get(1).getName());
    }

    @Test
    void getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories_SortByProductNameDesc_ShouldReturnSortedProducts() {
        // Arrange
        Product product1 = new Product(1L, "Apple", "Description1", 10, 100.0, 1L, List.of(1L));
        Product product2 = new Product(2L, "Banana", "Description2", 20, 200.0, 2L, List.of(2L));

        CustomPage<Product> customPage = new CustomPage<>(List.of(product1, product2), 0, 10, 2, 1, true, true);
        when(productPersistencePort.getPaginationProduct()).thenReturn(customPage);

        // Act
        CustomPage<Product> result = productUseCase.getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(SortDirection.DESC, FilterBy.PRODUCT);

        // Assert
        assertEquals("Banana", result.getContent().get(0).getName());
        assertEquals("Apple", result.getContent().get(1).getName());
    }

    @Test
    void getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories_SortByBrandNameAsc_ShouldReturnSortedProducts() {
        // Arrange
        Brand brand1 = new Brand(1L, "ZBrand", "Description1");
        Brand brand2 = new Brand(2L, "ABrand", "Description2");

        Product product1 = new Product(1L, "Product1", "Description1", 10, 100.0, 1L, List.of(1L));
        Product product2 = new Product(2L, "Product2", "Description2", 20, 200.0, 2L, List.of(2L));

        product1.setBrand(brand1);
        product2.setBrand(brand2);

        CustomPage<Product> customPage = new CustomPage<>(List.of(product1, product2), 0, 10, 2, 1, true, true);
        when(productPersistencePort.getPaginationProduct()).thenReturn(customPage);

        // Act
        CustomPage<Product> result = productUseCase.getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(SortDirection.ASC, FilterBy.BRAND);

        // Assert
        assertEquals("ABrand", result.getContent().get(0).getBrand().getName());
        assertEquals("ZBrand", result.getContent().get(1).getBrand().getName());
    }

    @Test
    void getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories_SortByCategoryAsc_ShouldReturnSortedProducts() {
        // Arrange
        Category category1 = new Category(1L, "CategoryB", "Description1");
        Category category2 = new Category(2L, "CategoryA", "Description2");

        Product product1 = new Product(1L, "Product1", "Description1", 10, 100.0, 1L, List.of(1L));
        Product product2 = new Product(2L, "Product2", "Description2", 20, 200.0, 2L, List.of(2L));

        product1.setCategories(Set.of(category1));
        product2.setCategories(Set.of(category2));

        CustomPage<Product> customPage = new CustomPage<>(List.of(product1, product2), 0, 10, 2, 1, true, true);
        when(productPersistencePort.getPaginationProduct()).thenReturn(customPage);

        // Act
        CustomPage<Product> result = productUseCase.getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(SortDirection.ASC, FilterBy.CATEGORY);

        // Assert
        assertEquals("CategoryA", result.getContent().get(1).getCategories().iterator().next().getName());
    }

    @Test
    void createProduct_WithoutBrand_ShouldThrowException() {
        // Arrange
        Product product = new Product();
        product.setBrandId(null);

        // Act & Assert
        assertThrows(ProductMustHaveAtLeastOneCategoryException.class, () -> productUseCase.createProduct(product));
    }

    @Test
    void createProduct_WithNegativePrice_ShouldThrowException() {
        // Arrange
        Product product = new Product();
        product.setPrice(-100.0);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productUseCase.createProduct(product));
    }

    @Test
    void createProduct_WithNegativeStock_ShouldThrowException() {
        // Arrange
        Product product = new Product();
        product.setPrice(-10.0);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productUseCase.createProduct(product));
    }

    @Test
    void createProduct_WithEmptyName_ShouldThrowException() {
        // Arrange
        Product product = new Product();
        product.setName("");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productUseCase.createProduct(product));
    }

    @Test
    void createProduct_WithEmptyDescription_ShouldThrowException() {
        // Arrange
        Product product = new Product();
        product.setDescription("");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productUseCase.createProduct(product));
    }



    @Test
    void createProduct_WithSomeValidAndSomeDuplicateCategories_ShouldThrowException() {
        // Arrange
        Product product = new Product();
        product.setCategoryId(List.of(1L, 1L, 2L));

        when(categoryPersistencePort.findById(1L)).thenReturn(new Category(1L, "Category1", "Description1"));
        when(categoryPersistencePort.findById(2L)).thenReturn(new Category(2L, "Category2", "Description2"));

        // Act & Assert
        assertThrows(ProductCategoryRepeatedException.class, () -> productUseCase.createProduct(product));
    }

    @Test
    void getPaginationProductsByAscAndDescByNameBrand_SortByBrandNameDesc_ShouldReturnSortedProducts() {
        // Arrange
        Brand brand1 = new Brand(1L, "ZBrand", "Description1");
        Brand brand2 = new Brand(2L, "ABrand", "Description2");

        Product product1 = new Product(1L, "Product1", "Description1", 10, 100.0, 1L, List.of(1L));
        Product product2 = new Product(2L, "Product2", "Description2", 20, 200.0, 2L, List.of(2L));

        product1.setBrand(brand1);
        product2.setBrand(brand2);

        CustomPage<Product> customPage = new CustomPage<>(List.of(product1, product2), 0, 10, 2, 1, true, true);
        when(productPersistencePort.getPaginationProduct()).thenReturn(customPage);

        // Act
        CustomPage<Product> result = productUseCase.getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(SortDirection.DESC, FilterBy.BRAND);

        // Assert
        assertEquals("ZBrand", result.getContent().get(0).getBrand().getName());
    }

    @Test
    void getPaginationProductsByAscAndDescByNameCategory_SortByCategoryDesc_ShouldReturnSortedProducts() {
        // Arrange
        Category category1 = new Category(1L, "CategoryB", "Description1");
        Category category2 = new Category(2L, "CategoryA", "Description2");

        Product product1 = new Product(1L, "Product1", "Description1", 10, 100.0, 1L, List.of(1L));
        Product product2 = new Product(2L, "Product2", "Description2", 20, 200.0, 2L, List.of(2L));

        product1.setCategories(Set.of(category1));
        product2.setCategories(Set.of(category2));

        CustomPage<Product> customPage = new CustomPage<>(List.of(product2, product1), 0, 10, 2, 1, true, true);
        when(productPersistencePort.getPaginationProduct()).thenReturn(customPage);

        // Act
        CustomPage<Product> result = productUseCase.getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(SortDirection.DESC, FilterBy.CATEGORY);

        // Assert
        assertEquals("CategoryA", result.getContent().get(0).getCategories().iterator().next().getName());
    }

    @Test
    void handlePersistencePortFailure_ShouldThrowException() {
        // Arrange
        Product product = new Product(1L, "Product1", "Description1", 10, 100.0, 1L, List.of(1L));
        doThrow(RuntimeException.class).when(productPersistencePort).saveProduct(any(Product.class));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productUseCase.createProduct(product));
    }

    @Test
    void handleCategoryPersistencePortFailure_ShouldThrowException() {
        // Arrange
        Product product = new Product(1L, "Product1", "Description1", 10, 100.0, 1L, List.of(1L));
        when(categoryPersistencePort.findById(1L)).thenThrow(RuntimeException.class);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productUseCase.createProduct(product));
    }

    @Test
    void handleBrandPersistencePortFailure_ShouldThrowException() {
        // Arrange
        Product product = new Product(1L, "Product1", "Description1", 10, 100.0, 1L, List.of(1L));
        when(brandPersistencePort.findById(1L)).thenThrow(RuntimeException.class);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productUseCase.createProduct(product));
    }

    @Test
    void updateProductAmountFromSupplies_ValidAmount_ShouldUpdateProductAmount() {
        Product product = new Product(1L, "Product1", "Description1", 10, 100.0, 1L, List.of(1L));
        ProductAmount productAmount = new ProductAmount(1L, 5);

        when(productPersistencePort.getProductById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productPersistencePort).saveProduct(any(Product.class));

        productUseCase.updateProductAmountFromSupplies(productAmount);

        assertEquals(15, product.getAmount());
        verify(productPersistencePort, times(1)).saveProduct(product);
    }

    @Test
    void updateProductAmountFromSupplies_NegativeAmount_ShouldUpdateProductAmount() {
        Product product = new Product(1L, "Product1", "Description1", 10, 100.0, 1L, List.of(1L));
        ProductAmount productAmount = new ProductAmount(1L, -5);

        when(productPersistencePort.getProductById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productPersistencePort).saveProduct(any(Product.class));

        productUseCase.updateProductAmountFromSupplies(productAmount);

        assertEquals(5, product.getAmount());
        verify(productPersistencePort, times(1)).saveProduct(product);
    }

    @Test
    void updateProductAmountFromSupplies_ProductNotFound_ShouldThrowException() {
        ProductAmount productAmount = new ProductAmount(1L, 5);

        when(productPersistencePort.getProductById(1L)).thenThrow(ResourcesNotFoundException.class);

        assertThrows(ResourcesNotFoundException.class, () -> productUseCase.updateProductAmountFromSupplies(productAmount));
        verify(productPersistencePort, times(0)).saveProduct(any(Product.class));
    }


    @Test
    void updateProductAmntFromSupplies_ValidAmount_ShouldUpdateProductAmount() {
        Product product = new Product(1L, "Product1", "Description1", 10, 100.0, 1L, List.of(1L));
        ProductAmount productAmount = new ProductAmount(1L, 5);

        when(productPersistencePort.getProductById(1L)).thenReturn(java.util.Optional.of(product));
        doNothing().when(productPersistencePort).saveProduct(any(Product.class));

        productUseCase.updateProductAmountFromSupplies(productAmount);

        assertEquals(15, product.getAmount());
        verify(productPersistencePort, times(1)).saveProduct(product);
    }

    @Test
    void updateProductAmntFromSupplies_NegativeAmount_ShouldUpdateProductAmount() {
        Product product = new Product(1L, "Product1", "Description1", 10, 100.0, 1L, List.of(1L));
        ProductAmount productAmount = new ProductAmount(1L, -5);

        when(productPersistencePort.getProductById(1L)).thenReturn(java.util.Optional.of(product));
        doNothing().when(productPersistencePort).saveProduct(any(Product.class));

        productUseCase.updateProductAmountFromSupplies(productAmount);

        assertEquals(5, product.getAmount());
        verify(productPersistencePort, times(1)).saveProduct(product);
    }

    @Test
    void updateProductAmntFromSupplies_ProductNotFound_ShouldThrowException() {
        ProductAmount productAmount = new ProductAmount(1L, 5);

        when(productPersistencePort.getProductById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productUseCase.updateProductAmountFromSupplies(productAmount));
        verify(productPersistencePort, times(0)).saveProduct(any(Product.class));
    }

    @Test
    void revertProductAmountFromSupplies_ValidAmount_ShouldUpdateProductAmount() {
        Product product = new Product(1L, "Product1", "Description1", 10, 100.0, 1L, List.of(1L));
        ProductAmount productAmount = new ProductAmount(1L, 5);

        when(productPersistencePort.getProductById(1L)).thenReturn(java.util.Optional.of(product));
        doNothing().when(productPersistencePort).saveProduct(any(Product.class));

        productUseCase.revertProductAmountFromSupplies(productAmount);

        assertEquals(5, product.getAmount());
        verify(productPersistencePort, times(1)).saveProduct(product);
    }

    @Test
    void revertProductAmountFromSupplies_NegativeAmount_ShouldUpdateProductAmount() {
        Product product = new Product(1L, "Product1", "Description1", 10, 100.0, 1L, List.of(1L));
        ProductAmount productAmount = new ProductAmount(1L, -5);

        when(productPersistencePort.getProductById(1L)).thenReturn(java.util.Optional.of(product));
        doNothing().when(productPersistencePort).saveProduct(any(Product.class));

        productUseCase.revertProductAmountFromSupplies(productAmount);

        assertEquals(15, product.getAmount());
        verify(productPersistencePort, times(1)).saveProduct(product);
    }

    @Test
    void revertProductAmountFromSupplies_ProductNotFound_ShouldThrowException() {
        ProductAmount productAmount = new ProductAmount(1L, 5);

        when(productPersistencePort.getProductById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productUseCase.revertProductAmountFromSupplies(productAmount));
        verify(productPersistencePort, times(0)).saveProduct(any(Product.class));
    }

}
