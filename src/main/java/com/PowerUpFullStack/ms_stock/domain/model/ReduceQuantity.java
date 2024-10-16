package com.PowerUpFullStack.ms_stock.domain.model;

import java.util.List;

public class ReduceQuantity {
    List<ProductAndQuantity> productAndQuantityList;

    public ReduceQuantity(List<ProductAndQuantity> productAndQuantityList) {
        this.productAndQuantityList = productAndQuantityList;
    }

    public ReduceQuantity() {
    }

    public List<ProductAndQuantity> getProductAndQuantityList() {
        return productAndQuantityList;
    }

    public void setProductAndQuantityList(List<ProductAndQuantity> productAndQuantityList) {
        this.productAndQuantityList = productAndQuantityList;
    }
}
