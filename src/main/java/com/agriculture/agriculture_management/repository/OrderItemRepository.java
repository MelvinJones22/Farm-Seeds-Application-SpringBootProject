package com.agriculture.agriculture_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agriculture.agriculture_management.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}	