package com.agriculture.agriculture_management.service;

import org.springframework.stereotype.Service;
import com.agriculture.agriculture_management.repository.OrderRepository;
import com.agriculture.agriculture_management.entity.Order;
import com.agriculture.agriculture_management.repository.CustomerRepository;
import com.agriculture.agriculture_management.repository.ProductRepository;
import com.agriculture.agriculture_management.entity.Customer;
import com.agriculture.agriculture_management.entity.Product;
import com.agriculture.agriculture_management.entity.OrderItem;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.time.LocalDateTime;
import com.agriculture.agriculture_management.entity.OrderStatus;
import com.agriculture.agriculture_management.repository.OrderItemRepository;



@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final CustomerRepository customerRepository;
	private final ProductRepository productRepository;
	private final OrderItemRepository orderItemRepository;

	public OrderService(OrderRepository orderRepository,
			CustomerRepository customerRepository,
			ProductRepository productRepository,
			OrderItemRepository orderItemRepository) {

		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
		this.orderItemRepository = orderItemRepository;
	}

	@Transactional
	public Order createOrder(Order order, String customerEmail) {

	    Customer customer = findCustomerByEmail(customerEmail);
	    order.setCustomer(customer);

	    order.setOrderDate(LocalDateTime.now());
	    order.setStatus(OrderStatus.PENDING);

	    Double total = calculateTotal(order.getOrderItems());
	    order.setTotalAmount(total);

	    Order savedOrder = orderRepository.save(order);

	    for (OrderItem item : order.getOrderItems()) {
	        item.setOrder(savedOrder);
	    }

	    orderItemRepository.saveAll(order.getOrderItems());

	    return savedOrder;
	}

	public Customer findCustomerByEmail(String email) {

		return customerRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Customer not found"));
	}

	public Product findProductById(Long productId) {

		return productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));
	}

	public OrderItem createOrderItem(Order order, Product product, Integer quantity) {

		OrderItem orderItem = new OrderItem();

		orderItem.setOrder(order);
		orderItem.setProduct(product);
		orderItem.setQuantity(quantity);
		orderItem.setPrice(product.getPrice());
		orderItem.setSubtotal(product.getPrice() * quantity);

		return orderItem;
	}

	public Double calculateTotal(List<OrderItem> orderItems) {

		double total = 0;

		for (OrderItem item : orderItems) {
			total = total + item.getSubtotal();
		}

		return total;
	}



}
