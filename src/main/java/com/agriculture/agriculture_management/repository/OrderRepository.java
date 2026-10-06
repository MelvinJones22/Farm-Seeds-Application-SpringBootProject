package com.agriculture.agriculture_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.agriculture.agriculture_management.entity.Order;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByCustomerEmail(String email);


}
