package com.nhnacademy.shoppingmall.order.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {
    public enum OrderStatus {
        PENDING, PAID, SHIPPING, DELIVERED
    }

    private String orderId;
    private String userId;
    private String addressId;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;

    public Order(String orderId, String userId, String addressId, BigDecimal totalPrice,
                 OrderStatus orderStatus, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.addressId = addressId;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getAddressId() { return addressId; }
    public void setAddressId(String addressId) { this.addressId = addressId; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public OrderStatus getOrderStatus() { return orderStatus; }
    public void setOrderStatus(OrderStatus orderStatus) { this.orderStatus = orderStatus; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}