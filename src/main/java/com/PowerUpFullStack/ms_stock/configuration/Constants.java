package com.PowerUpFullStack.ms_stock.configuration;

public class Constants {
    private Constants() { throw new IllegalStateException("Utility class"); }

    public static final String PRODUCT_AMOUNT_ERROR_EXCEPTION = "Product amount cannot be null";
    public static final String BRAND_NOT_FOUND_MESSAGE_EXCEPTION = "Brand not found";
    public static final String PRODUCT_NOT_FOUND_MESSAGE_EXCEPTION = "Product not found";
    public static final String WRONG_CREDENTIALS_MESSAGE = "Wrong credentials or role not allowed";
    public static final String PRODUCT_CANT_MORE_THREE_CATEGORIES_MESSAGE_EXCEPTION= "Product can't have more than three categories";
    public static final String PRODUCT_MUST_HAVE_ALMOST_ONCE_CATEGORY_MESSAGE_EXCEPTION = "Product must have at least one category";
    public static final String PRODUCT_CATEGORY_REPEATED_MESSAGE_EXCEPTION = "The selected categories are repeated";
    public static final String BRAND_NAME_ALREADY_EXISTS_MESSAGE_EXCEPTION = "Brand name already exists";
    public static final String BRAND_DESCRIPTION_IS_REQUIRED_MESSAGE_EXCEPTION = "Description is mandatory";
    public static final String BRAND_DESCRIPTION_IS_TOO_LONG_MESSAGE_EXCEPTION = "Description must be less than 120 characters";
    public static final String BRAND_NAME_IS_REQUIRED_MESSAGE_EXCEPTION = "Name is mandatory";
    public static final String BRAND_NAME_IS_TOO_LONG_MESSAGE_EXCEPTION = "Name must be less than 50 characters";
    public static final String RESOURCES_NOT_FOUND_MESSAGE_EXCEPTION = "No resources found in the database";
    public static final String INVALID_SORT_DIRECTION_MESSAGE_EXCEPTION = "Invalid sort direction";
    public static final String CATEGORY_NAME_IS_TOO_LONG_MESSAGE_EXCEPTION = "Name must be less than 50 characters";
    public static final String CATEGORY_NAME_ALREADY_EXISTS_MESSAGE_EXCEPTION = "Category name already exists";
    public static final String CATEGORY_NAME_IS_REQUIRED_MESSAGE_EXCEPTION = "Name is mandatory";
    public static final String CATEGORY_DESCRIPTION_IS_TOO_LONG_MESSAGE_EXCEPTION = "Description must be less than 90 characters";
    public static final String CATEGORY_DESCRIPTION_IS_REQUIRED_MESSAGE_EXCEPTION = "Description is mandatory";
    public static final String RESPONSE_ERROR_MESSAGE = "Error:";
    public static final String SWAGGER_TITLE_MESSAGE = "Stock API Pragma Power Up Full Stack";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "Stock microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";
}
