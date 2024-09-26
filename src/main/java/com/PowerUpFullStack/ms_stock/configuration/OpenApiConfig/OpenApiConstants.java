package com.PowerUpFullStack.ms_stock.configuration.OpenApiConfig;

public class OpenApiConstants {
    private OpenApiConstants() { throw new IllegalStateException("Utility class"); }

    public static final String CODE_201 = "201";
    public static final String CODE_409 = "409";

    // Category Rest Controller

    public static final String SUMMARY_CREATE_CATEGORY = "Add a new Category";
    public static final String DESCRIPTION_CREATE_CATEGORY_201 = "Category created";
    public static final String DESCRIPTION_CREATE_CATEGORY_409 = "Category exists";

    public static final String SUMMARY_PAGINATION_CATEGORIES = "Category Pagination";
    public static final String DESCRIPTION_PAGINATION_CATEGORIES_201 = "Category Pagination successful";
    public static final String DESCRIPTION_PAGINATION_CATEGORIES_409 = "Category Pagination failed";

    // Brand Rest Controller

    public static final String SUMMARY_CREATE_BRAND = "Add a new Brand";
    public static final String DESCRIPTION_CREATE_BRAND_201 = "Brand created";
    public static final String DESCRIPTION_CREATE_BRAND_409 = "Brand exists";

    public static final String SUMMARY_PAGINATION_BRANDS = "Brand Pagination";
    public static final String DESCRIPTION_PAGINATION_BRANDS_201 = "Brand Pagination successful";
    public static final String DESCRIPTION_PAGINATION_BRANDS_409 = "Brand Pagination failed";

    // Product Rest Controller

    public static final String SUMMARY_CREATE_PRODUCT = "Add a new Product";
    public static final String DESCRIPTION_CREATE_PRODUCT_201 = "Product created";
    public static final String DESCRIPTION_CREATE_PRODUCT_409 = "Product exists";

    public static final String SUMMARY_PAGINATION_PRODUCTS = "Products Pagination";
    public static final String DESCRIPTION_PAGINATION_PRODUCTS_201 = "Products Pagination successful";
    public static final String DESCRIPTION_PAGINATION_PRODUCTS_409 = "Products Pagination failed";

    public static final String SUMMARY_UPDATE_AMOUNT = "Update amount of Product";
    public static final String DESCRIPTION_UPDATE_AMOUNT_201 = "Amount updated";
    public static final String DESCRIPTION_UPDATE_AMOUNT_409 = "Amount not updated";

    public static final String SUMMARY_CANCEL_AMOUNT = "Cancel amount of Product";
    public static final String DESCRIPTION_CANCEL_AMOUNT_201 = "Amount canceled";
    public static final String DESCRIPTION_CANCEL_AMOUNT_409 = "Amount not canceled";
    // Content

    public static final String APPLICATION_JSON = "application/json";

    // Security

    public static final String SECURITY_REQUIREMENT = "jwt";

    // Schema

    public static final String SCHEMAS_MAP = "#/components/schemas/Map";
    public static final String SCHEMAS_ERROR = "#/components/schemas/Error";
}
