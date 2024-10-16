package com.PowerUpFullStack.ms_stock.configuration.OpenApiConfig;

public class OpenApiConstants {
    private OpenApiConstants() { throw new IllegalStateException("Utility class"); }

    public static final String CODE_201 = "201";
    public static final String CODE_409 = "409";
    public static final String CODE_200 = "200";
    public static final String CODE_404 = "404";

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
    public static final String DESCRIPTION_PAGINATION_PRODUCTS_200 = "Products Pagination successful";
    public static final String DESCRIPTION_PAGINATION_PRODUCTS_404 = "Products Pagination failed";

    public static final String SUMMARY_UPDATE_AMOUNT = "Update amount of Product";
    public static final String DESCRIPTION_UPDATE_AMOUNT_201 = "Amount updated";
    public static final String DESCRIPTION_UPDATE_AMOUNT_409 = "Amount not updated";

    public static final String SUMMARY_CANCEL_AMOUNT = "Cancel amount of Product";
    public static final String DESCRIPTION_CANCEL_AMOUNT_201 = "Amount canceled";
    public static final String DESCRIPTION_CANCEL_AMOUNT_409 = "Amount not canceled";

    public static final String SUMMARY_GET_PRODUCT_BY_ID = "Get Product by Id";
    public static final String DESCRIPTION_GET_PRODUCT_BY_ID_200 = "Product found";
    public static final String DESCRIPTION_GET_PRODUCT_BY_ID_404 = "Product not found";

    public static final String SUMMARY_GET_CATEGORIES_BY_PRODUCTS_IDS = "Get Categories by Products Ids";
    public static final String DESCRIPTION_GET_CATEGORIES_BY_PRODUCTS_IDS_200 = "Categories found";
    public static final String DESCRIPTION_GET_CATEGORIES_BY_PRODUCTS_IDS_404 = "Categories not found";

    public static final String SUMMARY_AMOUNT_STOCK_AVAILABLE = "Amount Stock Available";
    public static final String DESCRIPTION_AMOUNT_STOCK_AVAILABLE_200 = "Amount Stock Available";
    public static final String DESCRIPTION_AMOUNT_STOCK_AVAILABLE_404 = "Amount Stock not Available";

    public static final String SUMMARY_GET_PRODUCT_BY_PRODUCTS_IDS = "Get Products by Products Ids";
    public static final String DESCRIPTION_POST_PRODUCT_BY_PRODUCTS_IDS_200 = "Products found";
    public static final String DESCRIPTION_POST_PRODUCT_BY_PRODUCTS_IDS_404 = "Products not found";

    public static final String SUMMARY_REDUCE_QUANTITY = "Reduce Quantity";
    public static final String DESCRIPTION_REDUCE_QUANTITY_200 = "Quantity reduced";
    public static final String DESCRIPTION_REDUCE_QUANTITY_404 = "Quantity not reduced";
    // Content

    public static final String APPLICATION_JSON = "application/json";

    // Security

    public static final String SECURITY_REQUIREMENT = "jwt";

    // Schema

    public static final String SCHEMAS_MAP = "#/components/schemas/Map";
    public static final String SCHEMAS_ERROR = "#/components/schemas/Error";
}
