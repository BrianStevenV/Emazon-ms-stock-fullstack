package com.PowerUpFullStack.ms_stock.configuration.ControllerAdvisor;

import com.PowerUpFullStack.ms_stock.domain.exception.ProductCategoryRepeatedException;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductMustHaveAtLeastOneCategoryException;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductCannotHaveMoreThanThreeCategoriesException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.PowerUpFullStack.ms_stock.configuration.Constants.PRODUCT_CANT_MORE_THREE_CATEGORIES_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_stock.configuration.Constants.PRODUCT_CATEGORY_REPEATED_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_stock.configuration.Constants.PRODUCT_MUST_HAVE_ALMOST_ONCE_CATEGORY_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_stock.configuration.Constants.RESPONSE_ERROR_MESSAGE;

@ControllerAdvice
public class ProductControllerAdvisor {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            } else {
                errorMessages.add(error.getDefaultMessage());
            }
        }
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(propertyPath, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ProductCategoryRepeatedException.class)
    public ResponseEntity<Map<String, String>> handleProductCategoryRepeatedException(ProductCategoryRepeatedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, PRODUCT_CATEGORY_REPEATED_MESSAGE_EXCEPTION));
    }
    @ExceptionHandler(ProductMustHaveAtLeastOneCategoryException.class)
    public ResponseEntity<Map<String, String>> handleProductMustHaveAlmostOnceCategoryException(ProductMustHaveAtLeastOneCategoryException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, PRODUCT_MUST_HAVE_ALMOST_ONCE_CATEGORY_MESSAGE_EXCEPTION));
    }
    @ExceptionHandler(ProductCannotHaveMoreThanThreeCategoriesException.class)
    public ResponseEntity<Map<String, String>> handleProductNotCanMoreThreeCategoriesException(ProductCannotHaveMoreThanThreeCategoriesException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, PRODUCT_CANT_MORE_THREE_CATEGORIES_MESSAGE_EXCEPTION));
    }
}
