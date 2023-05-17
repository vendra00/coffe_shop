package com.example.coffe_shop.model.entity;

import lombok.Data;

@Data
public class TotalDiscount extends Discount {
    public TotalDiscount() {
        setName("Total Discount");
        setPercentage(0.05); // 5% discount
    }
}
