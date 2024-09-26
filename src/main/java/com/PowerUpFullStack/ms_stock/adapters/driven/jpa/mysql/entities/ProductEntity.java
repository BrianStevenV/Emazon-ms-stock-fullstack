package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.EntityConstant.PRODUCT_COLUMN_AMOUNT;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.EntityConstant.PRODUCT_COLUMN_BRAND_ID;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.EntityConstant.PRODUCT_COLUMN_DESCRIPTION;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.EntityConstant.PRODUCT_COLUMN_NAME;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.EntityConstant.PRODUCT_COLUMN_PRICE;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.EntityConstant.PRODUCT_ENTITY_NAME;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.EntityConstant.PRODUCT_JOIN_COLUMN_CATEGORY_ID;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.EntityConstant.PRODUCT_JOIN_COLUMN_PRODUCT_ID;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.EntityConstant.PRODUCT_JOIN_TABLE_NAME;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.UtilsConstants.PRODUCT_COLUMN_NAME_SIZE_MAX;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.UtilsConstants.PRODUCT_COLUMN_NAME_SIZE_MESSAGE;

@Entity
@Table(name = PRODUCT_ENTITY_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = PRODUCT_COLUMN_NAME_SIZE_MAX, message = PRODUCT_COLUMN_NAME_SIZE_MESSAGE)
    @Column(name = PRODUCT_COLUMN_NAME, nullable = false)
    private String name;

    @Column(name = PRODUCT_COLUMN_DESCRIPTION, nullable = false)
    private String description;

    @Column(name = PRODUCT_COLUMN_AMOUNT, nullable = false)
    private Integer amount;

    @Column(name = PRODUCT_COLUMN_PRICE, nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = PRODUCT_COLUMN_BRAND_ID, nullable = false)
    private BrandEntity brand;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = PRODUCT_JOIN_TABLE_NAME,
            joinColumns = @JoinColumn(name = PRODUCT_JOIN_COLUMN_PRODUCT_ID),
            inverseJoinColumns = @JoinColumn(name = PRODUCT_JOIN_COLUMN_CATEGORY_ID)
    )
    private Set<CategoryEntity> categories;
    //JPA no carga automaticamente categorias asociadas en tabla intermedia porque JPA utiliza lazy loading para el performance (por defecto es LAZY
    //El comentario de arriba es para mi! :D

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
