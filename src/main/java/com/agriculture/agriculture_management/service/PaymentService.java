package com.agriculture.agriculture_management.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.agriculture.agriculture_management.dto.PaymentResponse;
import com.agriculture.agriculture_management.dto.PaymentVerificationRequest;
import com.agriculture.agriculture_management.dto.RazorpayOrderResponse;
import com.agriculture.agriculture_management.entity.Order;
import com.agriculture.agriculture_management.entity.Payment;
import com.agriculture.agriculture_management.repository.OrderRepository;
import com.agriculture.agriculture_management.repository.PaymentRepository;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final RazorpayClient razorpayClient;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    public PaymentService(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository,
            RazorpayClient razorpayClient) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.razorpayClient = razorpayClient;
    }

    public PaymentResponse createPayment(Long orderId) {

        Optional<Payment> existingPayment =
                paymentRepository.findByOrderId(orderId);

        Payment payment;

        if (existingPayment.isPresent()) {

            payment = existingPayment.get();

        } else {

            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() ->
                            new RuntimeException("Order not found"));

            payment = new Payment();

            payment.setOrder(order);
            payment.setAmount(order.getTotalAmount());
            payment.setPaymentStatus("PENDING");
            payment.setCreatedAt(LocalDateTime.now());

            payment = paymentRepository.save(payment);
        }

        PaymentResponse response = new PaymentResponse();

        response.setPaymentId(payment.getId());
        response.setOrderId(payment.getOrder().getId());
        response.setAmount(payment.getAmount());
        response.setPaymentStatus(payment.getPaymentStatus());
        response.setCreatedAt(payment.getCreatedAt());

        return response;
    }

    public RazorpayOrderResponse createRazorpayOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        int amountInPaise =
                (int) (order.getTotalAmount() * 100);

        try {

            JSONObject orderRequest = new JSONObject();

            orderRequest.put("amount", amountInPaise);
            orderRequest.put("currency", "INR");
            orderRequest.put(
                    "receipt",
                    "order_" + order.getId()
            );

            com.razorpay.Order razorpayOrder =
                    razorpayClient.orders.create(orderRequest);

            Payment payment =
                    paymentRepository.findByOrderId(orderId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Payment record not found"));

            payment.setRazorpayOrderId(
                    razorpayOrder.get("id")
            );

            paymentRepository.save(payment);

            RazorpayOrderResponse response =
                    new RazorpayOrderResponse();

            response.setRazorpayOrderId(
                    razorpayOrder.get("id")
            );

            response.setAmount(
                    razorpayOrder.get("amount")
            );

            response.setCurrency(
                    razorpayOrder.get("currency")
            );

            return response;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to create Razorpay order: "
                    + e.getMessage()
            );
        }
    }

    public PaymentResponse verifyPayment(
            PaymentVerificationRequest request) {

        try {

            JSONObject attributes = new JSONObject();

            attributes.put(
                    "razorpay_order_id",
                    request.getRazorpayOrderId()
            );

            attributes.put(
                    "razorpay_payment_id",
                    request.getRazorpayPaymentId()
            );

            attributes.put(
                    "razorpay_signature",
                    request.getRazorpaySignature()
            );

            boolean verified =
                    Utils.verifyPaymentSignature(
                            attributes,
                            razorpayKeySecret
                    );

            if (!verified) {

                throw new RuntimeException(
                        "Payment verification failed"
                );
            }

            Payment payment =
                    paymentRepository
                            .findByRazorpayOrderId(
                                    request.getRazorpayOrderId()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Payment record not found"
                                    ));

            payment.setPaymentId(
                    request.getRazorpayPaymentId()
            );

            payment.setPaymentStatus("SUCCESS");

            paymentRepository.save(payment);

            PaymentResponse response =
                    new PaymentResponse();

            response.setPaymentId(payment.getId());
            response.setOrderId(payment.getOrder().getId());
            response.setAmount(payment.getAmount());
            response.setPaymentStatus(
                    payment.getPaymentStatus()
            );
            response.setCreatedAt(
                    payment.getCreatedAt()
            );

            return response;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Payment verification failed: "
                    + e.getMessage()
            );
        }
    }
}