package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.repositories;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT DISTINCT p FROM ProductEntity p " +
            "LEFT JOIN FETCH p.categories c")
    Page<ProductEntity> findAllWithCategories(Pageable pageable);
}
