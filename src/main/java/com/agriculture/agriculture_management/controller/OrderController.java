package com.agriculture.agriculture_management.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.agriculture.agriculture_management.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.agriculture.agriculture_management.dto.OrderRequest;
import com.agriculture.agriculture_management.entity.Product;

@RestController
@RequestMapping("/orders")
public class OrderController {
	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
	    this.orderService = orderService;
	}
	
	@PostMapping
	public String createOrder(@RequestBody OrderRequest orderRequest) {
		Product product = orderService.findProductById(orderRequest.getProductId());
		
		return "Product ID: " + orderRequest.getProductId()
        + ", Quantity: " + orderRequest.getQuantity();
	}

}
