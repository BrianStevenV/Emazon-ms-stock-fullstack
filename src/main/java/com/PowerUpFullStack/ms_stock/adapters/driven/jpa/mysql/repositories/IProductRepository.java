package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.repositories;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
}
