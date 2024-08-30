package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50, message = "Name must be less than 50 characters")
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Size(max = 90, message = "Description must be less than 90 characters")
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<ProductEntity> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity that = (CategoryEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
