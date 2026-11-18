package com.example.demoTest.service;

import com.example.demoTest.entity.Product;
import com.example.demoTest.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;

    ProductService(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()->
                        new IllegalArgumentException("Product not found: "+id));
    }

    public Product createProduct(Product product) {
        boolean alreadyExists =
                productRepository.existsByName(product.getName());

        if(alreadyExists){
            throw new RuntimeException("Product already exists");
        }

        return productRepository.save(product);
    }
}
