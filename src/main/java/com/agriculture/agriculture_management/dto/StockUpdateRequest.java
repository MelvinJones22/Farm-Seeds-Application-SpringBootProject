package com.agriculture.agriculture_management.dto;

import jakarta.validation.constraints.Min;

public class StockUpdateRequest {

    @Min(value = 1, message = "Stock quantity must be at least 1")
    private Integer quantity;

    public StockUpdateRequest() {
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}