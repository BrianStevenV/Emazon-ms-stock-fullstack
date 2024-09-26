package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.adapters.utils;

import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public class MySqlAdapterMethodsUtils {
    public static <T, E> CustomPage<T> createCustomPage(Page<E> page, Function<E, T> mapEntityToDomain) {
        List<T> content = page.getContent()
                .stream()
                .map(mapEntityToDomain)
                .toList();

        return new CustomPage<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}
