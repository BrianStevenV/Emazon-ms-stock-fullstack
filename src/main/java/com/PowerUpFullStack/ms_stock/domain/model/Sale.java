package com.PowerUpFullStack.ms_stock.domain.model;

import java.util.List;

public class Sale {
    private List<ProductSalesDetails> productSalesDetailsList;
    private Double subtotal;

    public Sale(List<ProductSalesDetails> productSalesDetailsList, Double subtotal) {
        this.productSalesDetailsList = productSalesDetailsList;
        this.subtotal = subtotal;
    }

    public Sale() {
    }

    public List<ProductSalesDetails> getProductSalesDetailsList() {
        return productSalesDetailsList;
    }

    public void setProductSalesDetailsList(List<ProductSalesDetails> productSalesDetailsList) {
        this.productSalesDetailsList = productSalesDetailsList;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}
