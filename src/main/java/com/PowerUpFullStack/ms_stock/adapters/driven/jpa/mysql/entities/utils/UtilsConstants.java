package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils;

public class UtilsConstants {
    private UtilsConstants() { throw new IllegalStateException("Utility class"); }


    // Brands
    public static final int BRAND_COLUMN_NAME_SIZE_MAX = 50;
    public static final String BRAND_COLUMN_NAME_SIZE_MESSAGE = "Name must be less than 50 characters";
    public static final String BRAND_COLUMN_NAME_NOT_NULL_MESSAGE = "Name is required";

    public static final int BRAND_COLUMN_DESCRIPTION_SIZE_MAX = 120;
    public static final String BRAND_COLUMN_DESCRIPTION_SIZE_MESSAGE = "Description must be less than 120 characters";
    public static final String BRAND_COLUMN_DESCRIPTION_NOT_NULL_MESSAGE = "Description is required";


    // Categories
    public static final int CATEGORY_COLUMN_NAME_SIZE_MAX = 50;
    public static final String CATEGORY_COLUMN_NAME_SIZE_MESSAGE = "Name must be less than 50 characters";
    public static final String CATEGORY_COLUMN_NAME_NOT_NULL_MESSAGE = "Name is required";

    public static final int CATEGORY_COLUMN_DESCRIPTION_SIZE_MAX = 120;
    public static final String CATEGORY_COLUMN_DESCRIPTION_SIZE_MESSAGE = "Description must be less than 90 characters";
    public static final String CATEGORY_COLUMN_DESCRIPTION_NOT_NULL_MESSAGE = "Description is required";


    // Products
    public static final int PRODUCT_COLUMN_NAME_SIZE_MAX = 150;
    public static final String PRODUCT_COLUMN_NAME_SIZE_MESSAGE = "Name must be less than 150 characters";


}
