
package com.agriculture.agriculture_management.dto;

import jakarta.validation.constraints.Min;

public class OrderRequest {

    private Long productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public OrderRequest() {
    }
}
