package com.example.onlinemarket.Repositories;

import com.example.onlinemarket.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product,Long> {
}
