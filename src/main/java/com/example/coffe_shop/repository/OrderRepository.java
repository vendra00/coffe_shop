package com.example.coffe_shop.repository;

import com.example.coffe_shop.model.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    // Additional custom query methods can be defined here if needed
}
