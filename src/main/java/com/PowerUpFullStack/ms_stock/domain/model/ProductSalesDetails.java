package com.PowerUpFullStack.ms_stock.domain.model;

public class ProductSalesDetails {
    private long productId;
    private int amount;
    private double price;

    public ProductSalesDetails(long productId, int amount, double price) {
        this.productId = productId;
        this.amount = amount;
        this.price = price;
    }

    public ProductSalesDetails() {
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
