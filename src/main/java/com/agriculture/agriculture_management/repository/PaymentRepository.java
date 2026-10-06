package com.agriculture.agriculture_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agriculture.agriculture_management.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrderId(Long orderId);

    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);
}