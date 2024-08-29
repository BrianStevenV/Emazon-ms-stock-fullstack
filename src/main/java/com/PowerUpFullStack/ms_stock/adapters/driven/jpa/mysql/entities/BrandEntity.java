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

@Entity
@Table(name = "brand")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    @Size(max = 50, message = "Name must be less than 50 characters")
    @NotNull(message = "Name is required")
    private String name;

    @Column(name = "description", nullable = false)
    @Size(max = 120, message = "Description must be less than 120 characters")
    @NotNull(message = "Description is required")
    private String description;
}
