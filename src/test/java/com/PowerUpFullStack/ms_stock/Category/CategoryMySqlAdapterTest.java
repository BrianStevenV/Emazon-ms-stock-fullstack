package com.PowerUpFullStack.ms_stock.Category;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters.CategoryMySqlAdapter;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.CategoryEntity;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.mappers.ICategoryEntityMapper;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.PowerUpFullStack.ms_stock.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestPropertySource(locations = "classpath:application-dev.yml")
@SpringBootTest
public class CategoryMySqlAdapterTest {
    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private ICategoryEntityMapper categoryEntityMapper;

    private CategoryMySqlAdapter categoryMySqlAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryMySqlAdapter = new CategoryMySqlAdapter(categoryRepository, categoryEntityMapper);
    }

    @Test
    public void saveCategory_ShouldSaveCategory() {
        // Arrange
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("Devices");
        CategoryEntity categoryEntity = new CategoryEntity();
        when(categoryEntityMapper.toCategoryEntity(category)).thenReturn(categoryEntity);

        // Act
        categoryMySqlAdapter.saveCategory(category);

        // Assert
        verify(categoryRepository, times(1)).save(categoryEntity);
    }

    @Test
    public void findByName_CategoryExists_ShouldReturnCategory() {
        // Arrange
        String categoryName = "Electronics";
        Category category = new Category();
        category.setName(categoryName);
        category.setDescription("Devices");
        CategoryEntity categoryEntity = new CategoryEntity();
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(categoryEntity));
        when(categoryEntityMapper.toCategory(categoryEntity)).thenReturn(category);

        // Act
        Category result = categoryMySqlAdapter.findByName(categoryName);

        // Assert
        assertEquals(category, result);
    }

    @Test
    public void findByName_CategoryDoesNotExist_ShouldReturnNull() {
        // Arrange
        String categoryName = "NonExistingCategory";
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.empty());

        // Act
        Category result = categoryMySqlAdapter.findByName(categoryName);

        // Assert
        assertEquals(null, result);
    }
    @Test
    public void saveCategory_ShouldHandleException() {
        // Arrange
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("Devices");
        CategoryEntity categoryEntity = new CategoryEntity();
        when(categoryEntityMapper.toCategoryEntity(category)).thenReturn(categoryEntity);
        doThrow(new RuntimeException("Database error")).when(categoryRepository).save(any(CategoryEntity.class));

        // Act & Assert
        try {
            categoryMySqlAdapter.saveCategory(category);
        } catch (Exception e) {
            assertEquals("Database error", e.getMessage());
        }
    }

}
