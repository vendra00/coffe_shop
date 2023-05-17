package com.example.coffe_shop.model.entity;

import lombok.Data;

@Data
public class LineItem {
    private Product product;
    private int quantity;
}
