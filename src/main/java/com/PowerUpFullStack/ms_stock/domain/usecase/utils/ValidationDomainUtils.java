package com.PowerUpFullStack.ms_stock.domain.usecase.utils;

import com.PowerUpFullStack.ms_stock.domain.exception.InvalidSortDirectionException;
import com.PowerUpFullStack.ms_stock.domain.model.Category;
import com.PowerUpFullStack.ms_stock.domain.model.SortDirection;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static com.PowerUpFullStack.ms_stock.domain.usecase.utils.ConstantsDomain.PAGINATION_ASC;
import static com.PowerUpFullStack.ms_stock.domain.usecase.utils.ConstantsDomain.PAGINATION_DESC;

public class ValidationDomainUtils {

    public static void validateName(String name,
                                    int maxLength,
                                    Supplier<Object> entityFinder,
                                    RuntimeException nameIsRequiredException,
                                    RuntimeException nameIsTooLongException,
                                    RuntimeException nameAlreadyExistsException) {
        if (name == null || name.isEmpty()) {
            throw nameIsRequiredException;
        }
        if (name.length() > maxLength) {
            throw nameIsTooLongException;
        }
        Object entityFromDatabase = entityFinder.get();
        if (entityFromDatabase != null) {
            throw nameAlreadyExistsException;
        }
    }

    public static void validateDescription(String description,
                                           int maxLength,
                                           RuntimeException descriptionIsRequiredException,
                                           RuntimeException descriptionIsTooLongException) {
        if (description == null || description.isEmpty()) {
            throw descriptionIsRequiredException;
        }
        if (description.length() > maxLength) {
            throw descriptionIsTooLongException;
        }
    }

    public static <T> List<T> sortList(List<T> list, SortDirection sortDirection, Comparator<T> comparator) {
        if (PAGINATION_ASC.equalsIgnoreCase(sortDirection.name())) {
            return list.stream().sorted(comparator).toList();
        } else if (PAGINATION_DESC.equalsIgnoreCase(sortDirection.name())) {
            return list.stream().sorted(comparator.reversed()).toList();
        } else {
            throw new InvalidSortDirectionException();
        }
    }

    public static int compareCategories(Set<Category> categories1, Set<Category> categories2) {
        return Integer.compare(categories1.size(), categories2.size());
    }
}
