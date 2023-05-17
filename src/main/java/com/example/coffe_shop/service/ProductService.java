package com.example.coffe_shop.service;

import com.example.coffe_shop.model.entity.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product getProductById(String id);
    List<Product> getAllProducts();
    void updateProduct(String id, Product updatedProduct);
    void deleteProduct(String id);
}
