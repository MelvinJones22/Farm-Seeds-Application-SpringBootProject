package com.agriculture.agriculture_management.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agriculture.agriculture_management.dto.LoginRequest;
import com.agriculture.agriculture_management.entity.Customer;
import com.agriculture.agriculture_management.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository,
                           PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer saveCustomer(Customer customer) {

        customer.setPassword(
                passwordEncoder.encode(customer.getPassword())
        );

        customer.setRole("CUSTOMER");

        return customerRepository.save(customer);
    }

    public boolean login(LoginRequest loginRequest) {

        Optional<Customer> customer = customerRepository
                .findByEmail(loginRequest.getEmail());

        if (customer.isPresent()) {

            return passwordEncoder.matches(
                    loginRequest.getPassword(),
                    customer.get().getPassword()
            );
        }

        return false;
    }
}