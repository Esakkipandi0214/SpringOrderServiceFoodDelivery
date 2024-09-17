package com.example.orderService.repository;

import com.example.orderService.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // You can define custom queries here if needed
}