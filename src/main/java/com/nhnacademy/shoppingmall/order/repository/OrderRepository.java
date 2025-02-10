package com.nhnacademy.shoppingmall.order.repository;

import com.nhnacademy.shoppingmall.order.domain.Order;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(String orderId);
    List<Order> findByUserId(String userId);
    int save(Order order);
    int updateStatus(String orderId, Order.OrderStatus status);
    int deleteById(String orderId);
    List<Order> findAll();

}