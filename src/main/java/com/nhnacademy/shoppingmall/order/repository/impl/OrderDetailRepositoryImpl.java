package com.nhnacademy.shoppingmall.order.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.order.domain.OrderDetail;
import com.nhnacademy.shoppingmall.order.repository.OrderDetailRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class OrderDetailRepositoryImpl implements OrderDetailRepository {

    @Override
    public Optional<OrderDetail> findById(String orderDetailId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM order_details WHERE order_detail_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, orderDetailId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(
                        new OrderDetail(
                                rs.getString("order_detail_id"),
                                rs.getString("order_id"),
                                rs.getString("product_id"),
                                rs.getInt("quantity"),
                                rs.getBigDecimal("price_at_time")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<OrderDetail> findByOrderId(String orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM order_details WHERE order_id = ?";
        List<OrderDetail> orderDetails = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                orderDetails.add(
                        new OrderDetail(
                                rs.getString("order_detail_id"),
                                rs.getString("order_id"),
                                rs.getString("product_id"),
                                rs.getInt("quantity"),
                                rs.getBigDecimal("price_at_time")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderDetails;
    }

    @Override
    public int save(OrderDetail orderDetail) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO order_details (order_detail_id, order_id, product_id, quantity, price_at_time) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, orderDetail.getOrderDetailId());
            ps.setString(2, orderDetail.getOrderId());
            ps.setString(3, orderDetail.getProductId());
            ps.setInt(4, orderDetail.getQuantity());
            ps.setBigDecimal(5, orderDetail.getPriceAtTime());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(String orderDetailId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM order_details WHERE order_detail_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, orderDetailId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}