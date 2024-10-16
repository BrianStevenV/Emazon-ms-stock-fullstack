package com.PowerUpFullStack.ms_stock.configuration.Security.utils;

public class ConstantsSecurity {
    private ConstantsSecurity() { throw new IllegalStateException("Utility class"); }

    // Roles
    public static final String ADMINISTRATOR_ROLE = "ADMINISTRATOR_ROLE";
    public static final String WAREHOUSE_ASSISTANT_ROLE = "WAREHOUSE_ASSISTANT_ROLE";
    public static final String CUSTOMER_ROLE = "CUSTOMER_ROLE";

    // Category Controller

    public static final String CATEGORY_CONTROLLER_POST_CREATE_CATEGORY = "/category/create";
    public static final String CATEGORY_CONTROLLER_GET_PAGINATION_CATEGORY = "/category/pagination/";

    // Brand Controller

    public static final String BRAND_CONTROLLER_POST_CREATE_BRAND = "/brand/create";
    public static final String BRAND_CONTROLLER_GET_PAGINATION_BRAND = "/brand/pagination/";
    //Product Controller

    public static final String PRODUCT_CONTROLLER_POST_CREATE_PRODUCT = "/product/";
    public static final String PRODUCT_CONTROLLER_GET_PAGINATION_PRODUCT = "/product/pagination/";

    public static final String PRODUCT_CONTROLLER_PATCH_UPDATE_AMOUNT = "/product/amount/";
    public static final String PRODUCT_CONTROLLER_PATCH_CANCEL_AMOUNT = "/product/amount/revert";

    public static final String PRODUCT_CONTROLLER_GET_PRODUCT_BY_ID = "/product/{productId}";
    public static final String PRODUCT_CONTROLLER_POST_CATEGORIES_BY_PRODUCTS_IDS = "/product/categories";
    public static final String PRODUCT_CONTROLLER_POST_AMOUNT_STOCK_AVAILABLE = "/product/stock/available";

    public static final String PRODUCT_CONTROLLER_POST_PRODUCT_BY_PRODUCTS_IDS = "/product/products";

    public static final String PRODUCT_CONTROLLER_POST_REDUCE_QUANTITY = "/product/reduce-quantity";
    // Actuator
    public static final String ACTUATOR_HEALTH = "/actuator/health";

    // Swagger
    public static final String SWAGGER_UI_HTML = "/swagger-ui.html";
    public static final String SWAGGER_UI = "/swagger-ui/**";
    public static final String V3_API_DOCS = "/v3/api-docs/**";

    // Get Token
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_TOKEN = "Bearer ";
    public static final int AUTHORIZATION_HEADER_SUBSTRING = 7;

    public static final int PREFIX_RECURSIVE = 0;
    public static final int PREFIX_RECURSIVE_NEXT = 1;

    public static final String ROLES_CLAIM_JWT = "roles";
    public static final String ID_CLAIM_JWT = "id";
}
