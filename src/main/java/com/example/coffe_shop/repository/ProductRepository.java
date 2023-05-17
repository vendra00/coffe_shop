package com.example.coffe_shop.repository;

import com.example.coffe_shop.model.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    // Additional custom query methods can be defined here if needed
}
