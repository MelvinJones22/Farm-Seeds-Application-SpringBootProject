package com.agriculture.agriculture_management.service;
import org.springframework.stereotype.Service;

import com.agriculture.agriculture_management.entity.Product;
import com.agriculture.agriculture_management.repository.ProductRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository=productRepository;
	}

	private final ProductRepository productRepository;
	
	public List<Product> getAllProducts() {
	    return productRepository.findAll();
	}
	
	public Product saveProduct(Product product) {
	    return productRepository.save(product);
	}
	
	public Product updateProduct(Long id, Product updatedProduct) {

	    Product existingProduct = productRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	    existingProduct.setName(updatedProduct.getName());
	    existingProduct.setPrice(updatedProduct.getPrice());
	    existingProduct.setQuantity(updatedProduct.getQuantity());
	    existingProduct.setCategory(updatedProduct.getCategory());

	    return productRepository.save(existingProduct);
	}
	
	public void deleteProduct(Long id) {
	    productRepository.deleteById(id);
	}
	
	public Product getProductById(Long id) {
	    return productRepository.findById(id)
	    		.orElseThrow(() -> new ResponseStatusException(
	    		        HttpStatus.NOT_FOUND, "Product not found"));
	}
	
	public List<Product> getProductsByCategory(String category) {
	    return productRepository.findByCategory(category);
	}
	
	public List<Product> searchProductsByName(String name) {
	    return productRepository.findByNameContaining(name);
	}
}
