package com.PowerUpFullStack.ms_stock.configuration.ControllerAdvisor;

import com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.exceptions.ResourcesNotFoundException;
import com.PowerUpFullStack.ms_stock.domain.exception.CategoryDescriptionIsRequiredException;
import com.PowerUpFullStack.ms_stock.domain.exception.CategoryDescriptionIsTooLongException;
import com.PowerUpFullStack.ms_stock.domain.exception.CategoryNameAlreadyExistsException;
import com.PowerUpFullStack.ms_stock.domain.exception.CategoryNameIsRequiredException;
import com.PowerUpFullStack.ms_stock.domain.exception.CategoryNameIsTooLongException;
import com.PowerUpFullStack.ms_stock.domain.exception.InvalidSortDirectionException;
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

import static com.PowerUpFullStack.ms_stock.configuration.Constants.CATEGORY_DESCRIPTION_IS_REQUIRED_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_stock.configuration.Constants.CATEGORY_DESCRIPTION_IS_TOO_LONG_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_stock.configuration.Constants.CATEGORY_NAME_ALREADY_EXISTS_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_stock.configuration.Constants.CATEGORY_NAME_IS_REQUIRED_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_stock.configuration.Constants.CATEGORY_NAME_IS_TOO_LONG_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_stock.configuration.Constants.INVALID_SORT_DIRECTION_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_stock.configuration.Constants.RESOURCES_NOT_FOUND_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_stock.configuration.Constants.RESPONSE_ERROR_MESSAGE;


@ControllerAdvice
public class CategoryControllerAdvisor {
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

    @ExceptionHandler(CategoryDescriptionIsRequiredException.class)
    public ResponseEntity<Map<String, String>> handleCategoryDescriptionIsRequiredException(CategoryDescriptionIsRequiredException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, CATEGORY_DESCRIPTION_IS_REQUIRED_MESSAGE_EXCEPTION));
    }

    @ExceptionHandler(CategoryDescriptionIsTooLongException.class)
    public ResponseEntity<Map<String,String>> handleCategoryDescriptionIsTooLongException(CategoryDescriptionIsTooLongException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, CATEGORY_DESCRIPTION_IS_TOO_LONG_MESSAGE_EXCEPTION));
    }

    @ExceptionHandler(CategoryNameIsRequiredException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNameIsRequired(CategoryNameIsRequiredException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, CATEGORY_NAME_IS_REQUIRED_MESSAGE_EXCEPTION));
    }

    @ExceptionHandler(CategoryNameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNameAlreadyExists(CategoryNameAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, CATEGORY_NAME_ALREADY_EXISTS_MESSAGE_EXCEPTION));
    }

    @ExceptionHandler(CategoryNameIsTooLongException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNameIsTooLong(CategoryNameIsTooLongException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, CATEGORY_NAME_IS_TOO_LONG_MESSAGE_EXCEPTION));
    }

    @ExceptionHandler(InvalidSortDirectionException.class)
    public ResponseEntity<Map<String, String>> handleInvalidSortDirectionException(InvalidSortDirectionException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, INVALID_SORT_DIRECTION_MESSAGE_EXCEPTION));
    }
    @ExceptionHandler(ResourcesNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCategoriesResourcesNotFoundException(ResourcesNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, RESOURCES_NOT_FOUND_MESSAGE_EXCEPTION));
    }
}
