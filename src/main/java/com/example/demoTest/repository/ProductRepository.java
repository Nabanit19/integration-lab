package com.example.demoTest.repository;

import com.example.demoTest.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByName(String name);
}
