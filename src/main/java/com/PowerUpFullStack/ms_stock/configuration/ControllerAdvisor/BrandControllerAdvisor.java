package com.PowerUpFullStack.ms_stock.configuration.ControllerAdvisor;

import com.PowerUpFullStack.ms_stock.domain.exception.BrandDescriptionIsRequiredException;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandDescriptionIsTooLongException;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandNameAlreadyExistsException;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandNameIsRequiredException;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandNameIsTooLongException;
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

import static com.PowerUpFullStack.ms_stock.configuration.Constants.BRAND_DESCRIPTION_IS_REQUIRED_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_stock.configuration.Constants.BRAND_DESCRIPTION_IS_TOO_LONG_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_stock.configuration.Constants.BRAND_NAME_ALREADY_EXISTS_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_stock.configuration.Constants.BRAND_NAME_IS_REQUIRED_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_stock.configuration.Constants.BRAND_NAME_IS_TOO_LONG_MESSAGE_EXCEPTION;
import static com.PowerUpFullStack.ms_stock.configuration.Constants.RESPONSE_ERROR_MESSAGE;

@ControllerAdvice
public class BrandControllerAdvisor {
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

    @ExceptionHandler(BrandDescriptionIsRequiredException.class)
    public ResponseEntity<Map<String, String>> handleBrandDescriptionIsRequiredException(BrandDescriptionIsRequiredException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, BRAND_DESCRIPTION_IS_REQUIRED_MESSAGE_EXCEPTION));
    }
    @ExceptionHandler(BrandNameIsRequiredException.class)
    public ResponseEntity<Map<String, String>> handleBrandNameIsRequiredException(BrandNameIsRequiredException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, BRAND_NAME_IS_REQUIRED_MESSAGE_EXCEPTION));
    }
    @ExceptionHandler(BrandNameIsTooLongException.class)
    public ResponseEntity<Map<String, String>> handleBrandNameIsTooLongException(BrandNameIsTooLongException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, BRAND_NAME_IS_TOO_LONG_MESSAGE_EXCEPTION));
    }
    @ExceptionHandler(BrandDescriptionIsTooLongException.class)
    public ResponseEntity<Map<String, String>> handleBrandDescriptionIsTooLongException(BrandDescriptionIsTooLongException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, BRAND_DESCRIPTION_IS_TOO_LONG_MESSAGE_EXCEPTION));
    }
    @ExceptionHandler(BrandNameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleBrandNameAlreadyExistsException(BrandNameAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, BRAND_NAME_ALREADY_EXISTS_MESSAGE_EXCEPTION));
    }
}
