package com.PowerUpFullStack.ms_stock.Category;

import com.PowerUpFullStack.ms_stock.domain.exception.*;
import com.PowerUpFullStack.ms_stock.domain.model.Category;
import com.PowerUpFullStack.ms_stock.domain.spi.ICategoryPersistencePort;
import com.PowerUpFullStack.ms_stock.domain.usecase.CategoryUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@TestPropertySource(locations = "classpath:application-dev.yml")
@SpringBootTest
public class CategoryUseCaseTest {
    @Mock
    private ICategoryPersistencePort categoryPersistencePort;
    private CategoryUseCase categoryUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryUseCase = new CategoryUseCase(categoryPersistencePort);
    }

    @Test
    void testCreateCategorySuccess() {
        // Arrange
        Category category = new Category();
        category.setName("Valid Name");
        category.setDescription("Valid Description");
        when(categoryPersistencePort.findByName(category.getName())).thenReturn(null);

        // Act & Assert
        assertDoesNotThrow(() -> categoryUseCase.createCategory(category));
        verify(categoryPersistencePort, times(1)).saveCategory(category);
    }

    @Test
    void testCreateCategoryWithNullName() {
        // Arrange
        Category category = new Category();

        category.setName(null);
        category.setDescription("Valid Description");
        // Act & Assert
        assertThrows(CategoryNameIsRequired.class, () -> categoryUseCase.createCategory(category));
    }

    @Test
    void testCreateCategoryWithEmptyName() {
        // Arrange
        Category category = new Category();
        category.setName("");
        category.setDescription("Valid Description");

        // Act & Assert
        assertThrows(CategoryNameIsRequired.class, () -> categoryUseCase.createCategory(category));
    }
    @Test
    void testCreateCategoryWithNameTooLong() {
        // Arrange
        String longName = "a".repeat(51);
        Category category = new Category();
        category.setName(longName);
        category.setDescription("Valid Description");

        // Act & Assert
        assertThrows(CategoryNameIsTooLongException.class, () -> categoryUseCase.createCategory(category));
    }

    @Test
    void testCreateCategoryWithNullDescription() {
        // Arrange
        Category category = new Category();

        category.setName("Valid Name");
        category.setDescription(null);
        // Act & Assert
        assertThrows(CategoryDescriptionIsRequiredException.class, () -> categoryUseCase.createCategory(category));
    }

    @Test
    void testCreateCategoryWithEmptyDescription() {
        // Arrange
        Category category = new Category();
        category.setName("Valid Name");
        category.setDescription("");

        // Act & Assert
        assertThrows(CategoryDescriptionIsRequiredException.class, () -> categoryUseCase.createCategory(category));
    }

    @Test
    void testCreateCategoryWithDescriptionTooLong() {
        // Arrange
        String longDescription = "a".repeat(91);
        Category category = new Category();
        category.setName("Valid Name");
        category.setDescription(longDescription);

        // Act & Assert
        assertThrows(CategoryDescriptionIsTooLongException.class, () -> categoryUseCase.createCategory(category));
    }

    @Test
    void testCreateCategoryWithExistingName() {
        // Arrange
        Category existingCategory = new Category();
        existingCategory.setName("Existing Name");
        existingCategory.setDescription("existing Description");
        when(categoryPersistencePort.findByName(existingCategory.getName())).thenReturn(existingCategory);

        // Act & Assert
        assertThrows(CategoryNameAlreadyExistsException.class, () -> categoryUseCase.createCategory(existingCategory));
    }





}
