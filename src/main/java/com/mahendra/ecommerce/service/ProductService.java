package com.mahendra.ecommerce.service;

import com.mahendra.ecommerce.model.Product;
import com.mahendra.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public String deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            return "Product not found!";
        }
        productRepository.deleteById(id);
        return "Product deleted successfully!";
    }
}