package com.agriculture.agriculture_management.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.agriculture.agriculture_management.entity.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{
	List<Product> findByCategory(String category);
	
	List<Product> findByNameContaining(String name);

}
