package com.example.coffe_shop.model.entity;

import lombok.Data;

@Data
public class SubtotalDiscount extends Discount {
    public SubtotalDiscount() {
        setName("Subtotal Discount");
        setPercentage(0.1); // 10% discount
    }
}

