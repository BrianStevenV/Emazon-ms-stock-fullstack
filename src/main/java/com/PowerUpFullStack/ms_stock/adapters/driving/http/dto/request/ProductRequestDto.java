package com.PowerUpFullStack.ms_stock.adapters.driving.http.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

public record ProductRequestDto(

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only letters and spaces")
        String name,

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Description must contain only letters and spaces")
        String description,

        @NotNull(message = "Amount is required")
        Integer amount,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        @Digits(integer = 6,
                fraction = 2,
                message = "Price must be a valid number with up to 6 integer digits and 2 fractional digits")
        Double price,

        @NotNull(message = "Brand ID is required")
        @Positive(message = "Brand ID must be a positive number")
        Long brandId,

        @NotEmpty(message = "Categories list must not be empty")
        @Size(min = 1, max = 3, message = "The categories must contain between 1 and 3 items")
        @UniqueElements(message = "The categories list must not contain repeated items")
        List<@NotNull(message = "Category ID cannot be null")
            @Positive(message = "Category ID must be a positive number")
                Long
                >
                categoryId
) {
}
