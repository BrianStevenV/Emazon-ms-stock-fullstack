package com.PowerUpFullStack.ms_stock.configuration;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters.BrandMySqlAdapter;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters.CategoryMySqlAdapter;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.mappers.IBrandEntityMapper;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.mappers.ICategoryEntityMapper;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.repositories.IBrandRepository;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.PowerUpFullStack.ms_stock.domain.api.IBrandServicePort;
import com.PowerUpFullStack.ms_stock.domain.api.ICategoryServicePort;
import com.PowerUpFullStack.ms_stock.domain.spi.IBrandPersistencePort;
import com.PowerUpFullStack.ms_stock.domain.spi.ICategoryPersistencePort;
import com.PowerUpFullStack.ms_stock.domain.usecase.BrandUseCase;
import com.PowerUpFullStack.ms_stock.domain.usecase.CategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryMySqlAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort(){
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IBrandPersistencePort brandPersistencePort() {
        return new BrandMySqlAdapter(brandRepository, brandEntityMapper);
    }
    @Bean
    public IBrandServicePort brandServicePort(){
        return new BrandUseCase(brandPersistencePort());
    }
}
