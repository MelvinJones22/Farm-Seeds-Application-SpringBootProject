package com.agriculture.agriculture_management.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.agriculture.agriculture_management.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.agriculture.agriculture_management.dto.OrderRequest;
import com.agriculture.agriculture_management.entity.Product;
import com.agriculture.agriculture_management.entity.Order;
import com.agriculture.agriculture_management.entity.OrderItem;
import java.util.List;
import org.springframework.security.core.Authentication;
import java.util.List;
import com.agriculture.agriculture_management.entity.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.agriculture.agriculture_management.dto.OrderStatusRequest;
import jakarta.validation.Valid;
import com.agriculture.agriculture_management.dto.OrderResponse;

@RestController
@RequestMapping("/orders")
public class OrderController {
	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
	    this.orderService = orderService;
	}
	
	@PostMapping
	public Order createOrder(
	        @Valid @RequestBody OrderRequest orderRequest,
	        Authentication authentication) {
		
		String customerEmail = authentication.getName();

	    Product product = orderService.findProductById(orderRequest.getProductId());
	    Order order = new Order();
	    OrderItem orderItem = orderService.createOrderItem(
	            order,
	            product,
	            orderRequest.getQuantity()
	    );
	    
	    List<OrderItem> orderItems = List.of(orderItem);
	    
	    Double total = orderService.calculateTotal(orderItems);
	    
	    order.setTotalAmount(total);
	    
	    order.setOrderItems(orderItems);
	    
	    Order savedOrder = orderService.createOrder(order, customerEmail);
	    
	    return savedOrder;
	}
	
	@GetMapping("/my-orders")
	public List<OrderResponse> getMyOrders(Authentication authentication) {

	    String customerEmail = authentication.getName();

	    return orderService.getOrdersByCustomerEmail(customerEmail);
	}
	
	@GetMapping
	public List<OrderResponse> getAllOrders() {
	    return orderService.getAllOrders();
	}
	
	@PutMapping("/{id}/status")
	public OrderResponse updateOrderStatus(
	        @PathVariable Long id,
	        @RequestBody OrderStatusRequest request) {

	    return orderService.updateOrderStatus(id, request.getStatus());
	}
	
	@GetMapping("/{id}")
	public OrderResponse getOrderById(
	        @PathVariable Long id,
	        Authentication authentication) {

	    String customerEmail = authentication.getName();

	    return orderService.getCustomerOrderById(id, customerEmail);
	}

}
