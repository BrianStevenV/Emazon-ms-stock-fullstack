package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters.utils.MySqlAdapterMethodsUtils;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.CategoryEntity;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.exceptions.ResourcesNotFoundException;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.mappers.ICategoryEntityMapper;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.PowerUpFullStack.ms_stock.domain.model.Category;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters.utils.ConstantsMySqlAdapter.PAGE_NUMBER;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters.utils.ConstantsMySqlAdapter.PAGE_SIZE;

@RequiredArgsConstructor
public class CategoryMySqlAdapter implements ICategoryPersistencePort {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(categoryEntityMapper.toCategoryEntity(category));
    }

    @Override
    public Category findByName(String nameCategory) {
        return categoryRepository.findByName(nameCategory)
                .map(categoryEntityMapper::toCategory)
                .orElse(null);
    }

    @Override
    public CustomPage<Category> getPaginationCategories() {
        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        Page<CategoryEntity> categoriesPage = categoryRepository.findAll(pageable);

        if (categoriesPage.isEmpty()) {
            throw new ResourcesNotFoundException();
        }

        return MySqlAdapterMethodsUtils.createCustomPage(categoriesPage,
                categoryEntityMapper::toCategory);
    }

    @Override
    public Category findById(Long categoryId) {
        return categoryEntityMapper.toCategory(categoryRepository
                .findById(categoryId)
                .orElseThrow(ResourcesNotFoundException::new));
    }

}
