package com.agriculture.agriculture_management.controller;

import org.springframework.web.bind.annotation.RestController;
import com.agriculture.agriculture_management.service.ProductService;



import java.util.List;

import com.agriculture.agriculture_management.dto.StockUpdateRequest;
import com.agriculture.agriculture_management.entity.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    
    @PostMapping
    public Product addProduct( @Valid @RequestBody Product product) {
        return productService.saveProduct(product);
    }
    
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {

        return productService.updateProduct(id, product);
    }
    
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
    
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
    
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category);
    }
    
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productService.searchProductsByName(name);
    }
    
    @PutMapping("/{id}/stock")
    public Product addStock(
            @PathVariable Long id,
            @Valid @RequestBody StockUpdateRequest request) {

        return productService.addStock(id, request.getQuantity());
    }
}
