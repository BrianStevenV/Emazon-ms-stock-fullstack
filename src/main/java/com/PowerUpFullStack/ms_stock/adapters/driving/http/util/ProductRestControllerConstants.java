package com.PowerUpFullStack.ms_stock.adapters.driving.http.util;

public class ProductRestControllerConstants {
    private ProductRestControllerConstants() { throw new IllegalStateException("Utility class"); }

    public static final String PRODUCT_REST_CONTROLLER_BASE_PATH = "/product";

    public static final String PRODUCT_REST_CONTROLLER_POST_CREATE_PRODUCT = "/";
    public static final String PRODUCT_REST_CONTROLLER_GET_PAGINATION_PRODUCT = "/pagination/";

    public static final String PRODUCT_REST_CONTROLLER_PATCH_UPDATE_AMOUNT = "/amount/";
    public static final String PRODUCT_REST_CONTROLLER_PATCH_CANCEL_AMOUNT = "/amount/revert";

}
