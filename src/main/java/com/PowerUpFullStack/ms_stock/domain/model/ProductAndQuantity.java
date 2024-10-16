package com.PowerUpFullStack.ms_stock.domain.model;

public class ProductAndQuantity {
    private long productId;
    private int quantity;

    public ProductAndQuantity(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public ProductAndQuantity() {
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
