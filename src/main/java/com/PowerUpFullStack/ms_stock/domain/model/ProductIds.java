package com.PowerUpFullStack.ms_stock.domain.model;

import java.util.List;

public class ProductIds {
    List<Long> productIds;

    public ProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public ProductIds(){};

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
