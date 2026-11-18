package com.example.demoTest.controller;

import com.example.demoTest.entity.Product;
import com.example.demoTest.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;

    ProductController(ProductService productService){
        this.productService=productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id){
        Product product=productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product savedProduct=productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("fun")
    public String myfun(){
        return "Hello there!";
    }

    @GetMapping("fun2")
    public String myfun2(){
        return "Hello there! Welcome to fun2!";
    }

}
