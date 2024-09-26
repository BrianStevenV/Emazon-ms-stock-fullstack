package com.PowerUpFullStack.ms_stock.adapters.driven.jpa.mysql.entities.utils;

public class EntityConstant {
    private EntityConstant() { throw new IllegalStateException("Utility class"); }

    public static final String BRAND_ENTITY_NAME = "brand";
    public static final String CATEGORY_ENTITY_NAME = "category";
    public static final String PRODUCT_ENTITY_NAME = "product";

    // Brand Columns
    public static final String BRAND_COLUMN_NAME = "name";
    public static final String BRAND_COLUMN_DESCRIPTION = "description";

    // Category Columns
    public static final String CATEGORY_COLUMN_NAME = "name";
    public static final String CATEGORY_COLUMN_DESCRIPTION = "description";

    public static final String CATEGORY_MANY_TO_MANY_MAPPED_BY = "categories";

    // Product Columns

    public static final String PRODUCT_COLUMN_NAME = "name";
    public static final String PRODUCT_COLUMN_DESCRIPTION = "description";
    public static final String PRODUCT_COLUMN_AMOUNT = "amount";
    public static final String PRODUCT_COLUMN_PRICE = "price";
    public static final String PRODUCT_COLUMN_BRAND_ID = "brand_id";

    public static final String PRODUCT_JOIN_TABLE_NAME = "product_category";
    public static final String PRODUCT_JOIN_COLUMN_PRODUCT_ID = "product_id";
    public static final String PRODUCT_JOIN_COLUMN_CATEGORY_ID = "category_id";
}
