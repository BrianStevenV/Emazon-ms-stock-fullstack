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

import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.EntityConstant.CATEGORY_COLUMN_DESCRIPTION;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.EntityConstant.CATEGORY_COLUMN_NAME;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.EntityConstant.CATEGORY_ENTITY_NAME;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.EntityConstant.CATEGORY_MANY_TO_MANY_MAPPED_BY;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.UtilsConstants.CATEGORY_COLUMN_DESCRIPTION_SIZE_MAX;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.UtilsConstants.CATEGORY_COLUMN_DESCRIPTION_SIZE_MESSAGE;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.UtilsConstants.CATEGORY_COLUMN_NAME_SIZE_MAX;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.UtilsConstants.CATEGORY_COLUMN_NAME_SIZE_MESSAGE;

@Entity
@Table(name = CATEGORY_ENTITY_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = CATEGORY_COLUMN_NAME_SIZE_MAX, message = CATEGORY_COLUMN_NAME_SIZE_MESSAGE)
    @Column(name = CATEGORY_COLUMN_NAME, unique = true, nullable = false)
    private String name;

    @Size(max = CATEGORY_COLUMN_DESCRIPTION_SIZE_MAX, message = CATEGORY_COLUMN_DESCRIPTION_SIZE_MESSAGE)
    @Column(name = CATEGORY_COLUMN_DESCRIPTION, nullable = false)
    private String description;

    @ManyToMany(mappedBy = CATEGORY_MANY_TO_MANY_MAPPED_BY)
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
