package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters.utils.MySqlAdapterMethodsUtils;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.BrandEntity;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.exceptions.ResourcesNotFoundException;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.mappers.IBrandEntityMapper;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.repositories.IBrandRepository;
import com.PowerUpFullStack.ms_stock.domain.model.Brand;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.spi.IBrandPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters.utils.ConstantsMySqlAdapter.PAGE_NUMBER;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters.utils.ConstantsMySqlAdapter.PAGE_SIZE;

@RequiredArgsConstructor
public class BrandMySqlAdapter implements IBrandPersistencePort {
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    @Override
    public void saveBrand(Brand brand) {
        brandRepository.save(brandEntityMapper.toBrandEntity(brand));
    }

    @Override
    public Brand findByName(String nameBrand) {
        return brandRepository.findByName(nameBrand)
                .map(brandEntityMapper::toBrand)
                .orElse(null);
    }

    @Override
    public CustomPage<Brand> getPaginationBrand() {
        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        Page<BrandEntity> brandPage = brandRepository.findAll(pageable);

        if (brandPage.isEmpty()) {
            throw new ResourcesNotFoundException();
        }

        return MySqlAdapterMethodsUtils.createCustomPage(brandPage,
                brandEntityMapper::toBrand);
    }

    @Override
    public Optional<Brand> findById(Long brandId) {
        return brandRepository.findById(brandId).map(brandEntityMapper::toBrand);
    }
}
