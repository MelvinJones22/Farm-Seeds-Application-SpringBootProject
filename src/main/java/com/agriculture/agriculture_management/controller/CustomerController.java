package com.agriculture.agriculture_management.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.agriculture.agriculture_management.service.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.agriculture.agriculture_management.dto.LoginRequest;
import jakarta.validation.Valid;
import com.agriculture.agriculture_management.entity.Customer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.agriculture.agriculture_management.security.JwtService;
import com.agriculture.agriculture_management.repository.CustomerRepository;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService,
			AuthenticationManager authenticationManager,
			JwtService jwtService,
			 CustomerRepository customerRepository) {

		this.customerService = customerService;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.customerRepository = customerRepository;
	}

	@PostMapping
	public Customer registerCustomer(@Valid @RequestBody Customer customer) {
		return customerService.saveCustomer(customer);
	}

	@PostMapping("/login")
	public String login(@RequestBody LoginRequest loginRequest) {

	    Authentication authentication =
	            authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(
	                            loginRequest.getEmail(),
	                            loginRequest.getPassword()
	                    )
	            );

	    Customer customer = customerRepository
	            .findByEmail(authentication.getName())
	            .orElseThrow(() -> new RuntimeException("Customer not found"));

	    return jwtService.generateToken(
	            authentication.getName(),
	            customer.getRole()
	    );
	}

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final CustomerRepository customerRepository;
}
