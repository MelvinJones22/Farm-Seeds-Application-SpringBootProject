package com.agriculture.agriculture_management.dto;


import com.agriculture.agriculture_management.entity.OrderStatus;

public class OrderStatusRequest {

    private OrderStatus status;

    public OrderStatusRequest() {
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
