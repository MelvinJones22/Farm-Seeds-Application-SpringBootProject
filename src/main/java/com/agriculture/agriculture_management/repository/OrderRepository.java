package com.agriculture.agriculture_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.agriculture.agriculture_management.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	


}
