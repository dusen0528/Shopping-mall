package com.nhnacademy.shoppingmall.order.service;

import com.nhnacademy.shoppingmall.order.domain.OrderDetail;
import java.util.List;

public interface OrderDetailService {
    OrderDetail getOrderDetail(String orderDetailId);
    List<OrderDetail> getOrderDetails(String orderId);
    void createOrderDetail(OrderDetail orderDetail);
    void deleteOrderDetail(String orderDetailId);
}