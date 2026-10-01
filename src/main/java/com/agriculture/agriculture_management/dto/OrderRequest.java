package com.agriculture.agriculture_management.dto;




public class OrderRequest {
	private Long productId;
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
