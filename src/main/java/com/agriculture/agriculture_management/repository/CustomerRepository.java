package com.agriculture.agriculture_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.agriculture.agriculture_management.entity.Customer;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByEmail(String email);
}


