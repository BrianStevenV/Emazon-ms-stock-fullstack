package com.PowerUpFullStack.ms_stock.domain.model;

public class AmountStock {
    private Long productId;
    private Integer amount;

    public AmountStock(Long productId, Integer amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public AmountStock() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
