package com.example.coffe_shop.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private List<LineItem> lineItems = new ArrayList<>();
    private double subtotal;
    private double total;
}
