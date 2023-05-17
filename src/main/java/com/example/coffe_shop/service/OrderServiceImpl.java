package com.example.coffe_shop.service;

import com.example.coffe_shop.model.entity.*;
import com.example.coffe_shop.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService) {
        log.debug("OrderServiceImpl constructor called");
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Override
    public Order createOrder(Order order) {
        log.info("OrderServiceImpl createOrder called");
        List<LineItem> lineItems = order.getLineItems();
        double subtotal = 0.0;

        for (LineItem lineItem : lineItems) {
            String productId = lineItem.getProduct().getId();
            Optional<Product> productOptional = Optional.ofNullable(productService.getProductById(productId));
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                lineItem.setProduct(product);
                double lineItemTotal = product.getPrice() * lineItem.getQuantity();
                subtotal += lineItemTotal;
            } else {
                throw new RuntimeException("Product not found with ID: " + productId);
            }
        }

        double total = calculateOrderTotal(order);

        order.setSubtotal(subtotal);
        order.setTotal(total);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(String id) {
        log.info("OrderServiceImpl getOrderById called");
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<Order> getAllOrders() {
        log.info("OrderServiceImpl getAllOrders called");
        return orderRepository.findAll();
    }

    @Override
    public void updateOrder(String id, Order updatedOrder) {
        log.info("OrderServiceImpl updateOrder called");
        Order order = getOrderById(id);
        if (order != null) {
            order.setLineItems(updatedOrder.getLineItems());
            orderRepository.save(order);
        }
    }

    @Override
    public void deleteOrder(String id) {
        log.info("OrderServiceImpl deleteOrder called");
        orderRepository.deleteById(id);
    }

    @Override
    public double calculateOrderSubtotal(Order order) {
        log.info("OrderServiceImpl calculateOrderSubtotal called");
        double subtotal = 0;
        for (LineItem lineItem : order.getLineItems()) {
            subtotal += lineItem.getProduct().getPrice() * lineItem.getQuantity();
        }
        return subtotal;
    }

    @Override
    public double calculateOrderTotal(Order order) {
        log.info("OrderServiceImpl calculateOrderTotal called");
        double subtotal = calculateOrderSubtotal(order);

        double total = subtotal;

        if (isWeekend()) {
            total *= 0.98; // Apply 2% discount on weekends
        }

        if (total >= 20) {
            total *= 0.95; // Apply 5% discount if total is $20 or more
        }

        // Apply subtotal discount if applicable
        if (subtotal >= 20) {
            SubtotalDiscount subtotalDiscount = new SubtotalDiscount();
            double subtotalDiscountAmount = subtotal * subtotalDiscount.getPercentage();
            total -= subtotalDiscountAmount;
        }

        // Apply total discount if applicable
        TotalDiscount totalDiscount = new TotalDiscount();
        double totalDiscountAmount = total * totalDiscount.getPercentage();
        total -= totalDiscountAmount;

        return total;
    }

    private boolean isWeekend() {
        log.info("OrderServiceImpl isWeekend called");
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
