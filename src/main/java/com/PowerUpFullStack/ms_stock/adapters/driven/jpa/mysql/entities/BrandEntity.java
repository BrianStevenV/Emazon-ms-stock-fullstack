package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.EntityConstant.BRAND_COLUMN_DESCRIPTION;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.EntityConstant.BRAND_COLUMN_NAME;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.EntityConstant.BRAND_ENTITY_NAME;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.UtilsConstants.BRAND_COLUMN_DESCRIPTION_NOT_NULL_MESSAGE;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.UtilsConstants.BRAND_COLUMN_DESCRIPTION_SIZE_MAX;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.UtilsConstants.BRAND_COLUMN_DESCRIPTION_SIZE_MESSAGE;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.UtilsConstants.BRAND_COLUMN_NAME_NOT_NULL_MESSAGE;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.UtilsConstants.BRAND_COLUMN_NAME_SIZE_MAX;
import static com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils.UtilsConstants.BRAND_COLUMN_NAME_SIZE_MESSAGE;

@Entity
@Table(name = BRAND_ENTITY_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = BRAND_COLUMN_NAME, unique = true, nullable = false)
    @Size(max = BRAND_COLUMN_NAME_SIZE_MAX, message = BRAND_COLUMN_NAME_SIZE_MESSAGE)
    @NotNull(message = BRAND_COLUMN_NAME_NOT_NULL_MESSAGE)
    private String name;

    @Column(name = BRAND_COLUMN_DESCRIPTION, nullable = false)
    @Size(max = BRAND_COLUMN_DESCRIPTION_SIZE_MAX, message = BRAND_COLUMN_DESCRIPTION_SIZE_MESSAGE)
    @NotNull(message = BRAND_COLUMN_DESCRIPTION_NOT_NULL_MESSAGE)
    private String description;
}
