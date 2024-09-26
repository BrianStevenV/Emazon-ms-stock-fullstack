package com.PowerUpFullStack.ms_stock.Category;

import com.PowerUpFullStack.ms_stock.domain.exception.CategoryDescriptionIsRequiredException;
import com.PowerUpFullStack.ms_stock.domain.exception.CategoryDescriptionIsTooLongException;
import com.PowerUpFullStack.ms_stock.domain.exception.CategoryNameAlreadyExistsException;
import com.PowerUpFullStack.ms_stock.domain.exception.CategoryNameIsRequiredException;
import com.PowerUpFullStack.ms_stock.domain.exception.CategoryNameIsTooLongException;
import com.PowerUpFullStack.ms_stock.domain.model.Category;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.model.SortDirection;
import com.PowerUpFullStack.ms_stock.domain.spi.ICategoryPersistencePort;
import com.PowerUpFullStack.ms_stock.domain.usecase.CategoryUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        assertThrows(CategoryNameIsRequiredException.class, () -> categoryUseCase.createCategory(category));
    }

    @Test
    void testCreateCategoryWithEmptyName() {
        // Arrange
        Category category = new Category();
        category.setName("");
        category.setDescription("Valid Description");

        // Act & Assert
        assertThrows(CategoryNameIsRequiredException.class, () -> categoryUseCase.createCategory(category));
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

    // Pagination

    @Test
    void testGetPaginationCategoriesByAscSuccess() {
        // Arrange
        Category cat1 = new Category();
        cat1.setName("Alpha");
        Category cat2 = new Category();
        cat2.setName("Beta");
        Category cat3 = new Category();
        cat3.setName("Gamma");

        List<Category> categories = Arrays.asList(cat3, cat1, cat2);
        CustomPage<Category> customPage = new CustomPage<>();
        customPage.setContent(categories);

        when(categoryPersistencePort.getPaginationCategories()).thenReturn(customPage);

        // Act
        CustomPage<Category> result = categoryUseCase.getPaginationCategoriesByAscAndDesc(SortDirection.ASC);

        // Assert
        List<Category> sortedCategories = result.getContent();
        assertEquals(3, sortedCategories.size());
        assertEquals("Alpha", sortedCategories.get(0).getName());
        assertEquals("Beta", sortedCategories.get(1).getName());
        assertEquals("Gamma", sortedCategories.get(2).getName());
    }

    @Test
    void testGetPaginationCategoriesByDescSuccess() {
        // Arrange
        Category cat1 = new Category();
        cat1.setName("Alpha");
        Category cat2 = new Category();
        cat2.setName("Beta");
        Category cat3 = new Category();
        cat3.setName("Gamma");

        List<Category> categories = Arrays.asList(cat3, cat1, cat2);
        CustomPage<Category> customPage = new CustomPage<>();
        customPage.setContent(categories);

        when(categoryPersistencePort.getPaginationCategories()).thenReturn(customPage);

        // Act
        CustomPage<Category> result = categoryUseCase.getPaginationCategoriesByAscAndDesc(SortDirection.DESC);

        // Assert
        List<Category> sortedCategories = result.getContent();
        assertEquals(3, sortedCategories.size());
        assertEquals("Gamma", sortedCategories.get(0).getName());
        assertEquals("Beta", sortedCategories.get(1).getName());
        assertEquals("Alpha", sortedCategories.get(2).getName());
    }

    @Test
    void testGetPaginationCategoriesWithInvalidSortDirection() {
        // Arrange
        CustomPage<Category> customPage = new CustomPage<>();
        customPage.setContent(Arrays.asList(new Category()));

        when(categoryPersistencePort.getPaginationCategories()).thenReturn(customPage);

        // Act & Assert
        // Test with valid SortDirection values
        assertDoesNotThrow(() -> categoryUseCase.getPaginationCategoriesByAscAndDesc(SortDirection.ASC));
        assertDoesNotThrow(() -> categoryUseCase.getPaginationCategoriesByAscAndDesc(SortDirection.DESC));

    }

}
