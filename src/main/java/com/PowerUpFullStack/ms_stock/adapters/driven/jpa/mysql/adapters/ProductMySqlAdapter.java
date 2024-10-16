package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.ProductEntity;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.exceptions.ResourcesNotFoundException;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.mappers.IProductEntityMapper;
import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.repositories.IProductRepository;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.model.Product;
import com.PowerUpFullStack.ms_stock.domain.model.ProductIds;
import com.PowerUpFullStack.ms_stock.domain.spi.IProductPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class ProductMySqlAdapter implements IProductPersistencePort{
    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;

    @Override
    public void saveProduct(Product product) {
        productRepository.save(productEntityMapper.toProductEntity(product));

    }

    @Override
    public void saveProducts(List<Product> products) {
        productRepository.saveAll(productEntityMapper.toListProductEntity(products));
    }

    @Override
    public CustomPage<Product> getPaginationProduct() {
        Pageable pageable = PageRequest.of(0, 10);


        Page<ProductEntity> productEntityPage = productRepository.findAllWithCategories(pageable);

        if(productEntityPage.isEmpty()) {
            throw new ResourcesNotFoundException();
        }

        List<Product> productContent = productEntityPage.getContent()
                .stream()
                .map(productEntityMapper::toProduct)
                .collect(toList());

        CustomPage<Product> customPage = new CustomPage<>(
                productContent,
                productEntityPage.getNumber(),
                productEntityPage.getSize(),
                productEntityPage.getTotalElements(),
                productEntityPage.getTotalPages(),
                productEntityPage.isFirst(),
                productEntityPage.isLast()
        );

        return customPage;
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id)
                .map(productEntityMapper::toProduct); // Mapea solo si el Optional tiene valor
    }

    @Override
    public List<Product> getProductsByProductIds(ProductIds productIds) {
        List<ProductEntity> productEntityList = productRepository.findAllById(productIds.getProductIds());
        return productEntityList.stream()
                .map(productEntityMapper::toProduct)
                .collect(toList());

    }

}
