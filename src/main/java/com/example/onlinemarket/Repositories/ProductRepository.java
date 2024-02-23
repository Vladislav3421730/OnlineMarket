package com.example.onlinemarket.Repositories;

import com.example.onlinemarket.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface ProductRepository extends JpaRepository<Product,Long> {
}
