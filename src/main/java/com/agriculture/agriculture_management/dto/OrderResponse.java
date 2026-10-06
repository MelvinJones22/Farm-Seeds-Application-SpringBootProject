package com.agriculture.agriculture_management.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

	private Long id;
	private LocalDateTime orderDate;
	private String status;
	private Double totalAmount;
	private List<OrderItemResponse> orderItems;

	public OrderResponse() {
	}

	public OrderResponse(Long id, LocalDateTime orderDate,
			String status, Double totalAmount,
			List<OrderItemResponse> orderItems) {

		this.id = id;
		this.orderDate = orderDate;
		this.status = status;
		this.totalAmount = totalAmount;
		this.orderItems = orderItems;
	}

	public List<OrderItemResponse> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemResponse> orderItems) {
		this.orderItems = orderItems;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
}