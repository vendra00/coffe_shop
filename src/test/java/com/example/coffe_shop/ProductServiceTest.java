package com.example.coffe_shop;

import com.example.coffe_shop.model.entity.Product;
import com.example.coffe_shop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testAddProductToDatabase() {
        // Create a product
        Product product = new Product();
        product.setName("Espresso");
        product.setPrice(3.25);

        // Save the product to the database
        Product savedProduct = productRepository.save(product);

        // Verify that the product is saved with an ID
        assertNotNull(savedProduct.getId());

        // Retrieve the product from the database
        Product retrievedProduct = productRepository.findById(savedProduct.getId()).orElse(null);

        // Assert that the retrieved product is not null
        assertNotNull(retrievedProduct);

        // Assert the properties of the retrieved product
        assertEquals(product.getName(), retrievedProduct.getName());
        assertEquals(product.getPrice(), retrievedProduct.getPrice(), 0.01);
    }
}
