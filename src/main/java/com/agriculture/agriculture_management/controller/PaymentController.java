package com.agriculture.agriculture_management.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.agriculture.agriculture_management.dto.PaymentResponse;
import com.agriculture.agriculture_management.dto.PaymentVerificationRequest;
import com.agriculture.agriculture_management.dto.RazorpayOrderResponse;
import com.agriculture.agriculture_management.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{orderId}")
    public PaymentResponse createPayment(
            @PathVariable Long orderId) {

        return paymentService.createPayment(orderId);
    }

    @PostMapping("/razorpay/{orderId}")
    public RazorpayOrderResponse createRazorpayOrder(
            @PathVariable Long orderId) {

        return paymentService.createRazorpayOrder(orderId);
    }

    @PostMapping("/verify")
    public PaymentResponse verifyPayment(
            @RequestBody PaymentVerificationRequest request) {

        return paymentService.verifyPayment(request);
    }
}