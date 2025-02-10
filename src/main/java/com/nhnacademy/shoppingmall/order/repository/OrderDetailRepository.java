package com.nhnacademy.shoppingmall.order.repository;

import com.nhnacademy.shoppingmall.order.domain.OrderDetail;
import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository {
    Optional<OrderDetail> findById(String orderDetailId);
    List<OrderDetail> findByOrderId(String orderId);
    int save(OrderDetail orderDetail);
    int deleteById(String orderDetailId);
}