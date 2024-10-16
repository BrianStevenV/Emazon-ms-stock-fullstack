package com.PowerUpFullStack.ms_stock.domain.model;

public class AmountStockAvailable {
    Boolean available;

    public AmountStockAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
