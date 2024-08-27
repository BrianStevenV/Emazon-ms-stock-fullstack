package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.repositories;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
    @Query(value = "SELECT * FROM category WHERE name = :nameCategory", nativeQuery = true)
    Optional<CategoryEntity> findByName(@Param("nameCategory") String nameCategory);
}
