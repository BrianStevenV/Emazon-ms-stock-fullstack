package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.repositories;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBrandRepository extends JpaRepository<BrandEntity, Long> {
    @Query(value = "SELECT * FROM brand WHERE name = :nameBrand", nativeQuery = true)
    Optional<BrandEntity> findByName(@Param("nameBrand") String nameBrand);
}
