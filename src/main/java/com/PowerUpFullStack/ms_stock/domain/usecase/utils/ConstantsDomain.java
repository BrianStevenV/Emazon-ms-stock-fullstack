package com.PowerUpFullStack.ms_stock.domain.usecase.utils;

public class ConstantsDomain {
    private ConstantsDomain() { throw new IllegalStateException("Utility class"); }

    public static final String PAGINATION_ASC = "ASC";
    public static final String PAGINATION_DESC = "DESC";

    public static final int MAX_LENGTH_NAME = 50;
    public static final int MAX_LENGTH_BRAND_DESCRIPTION = 120;
    public static final int MAX_LENGTH_CATEGORY_DESCRIPTION = 90;

    public static int COMPARISON_RESULT_INIT = 0;

    public static final int CATEGORY_SIZE_LIMIT = 3;
}
