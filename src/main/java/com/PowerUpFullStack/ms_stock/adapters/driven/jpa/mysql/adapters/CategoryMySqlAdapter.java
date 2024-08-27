package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.mappers.ICategoryEntityMapper;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.PowerUpFullStack.ms_stock.domain.model.Category;
import com.PowerUpFullStack.ms_stock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;

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
}
