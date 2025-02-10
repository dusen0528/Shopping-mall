package com.nhnacademy.shoppingmall.order.service;

import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.domain.OrderDetail;

import java.util.List;

public interface OrderService {
    Order getOrder(String orderId);
    List<Order> getUserOrders(String userId);
    public void createOrder(Order order, List<OrderDetail> orderDetails);
    void updateOrderStatus(String orderId, Order.OrderStatus status);
    void deleteOrder(String orderId);
    void createOrderFromCart(String userId, String addressId);
    List<Order> getAllOrders();

}