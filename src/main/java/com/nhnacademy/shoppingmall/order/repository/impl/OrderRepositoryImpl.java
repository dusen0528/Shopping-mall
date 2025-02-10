package com.nhnacademy.shoppingmall.order.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

    @Override
    public Optional<Order> findById(String orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM orders WHERE order_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(
                        new Order(
                                rs.getString("order_id"),
                                rs.getString("user_id"),
                                rs.getString("address_id"),
                                rs.getBigDecimal("total_price"),
                                Order.OrderStatus.valueOf(rs.getString("order_status")),
                                rs.getTimestamp("created_at").toLocalDateTime()
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Order> findByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                orders.add(new Order(
                        rs.getString("order_id"),
                        rs.getString("user_id"),
                        rs.getString("address_id"),
                        rs.getBigDecimal("total_price"),
                        Order.OrderStatus.valueOf(rs.getString("order_status")),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public int save(Order order) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO orders (order_id, user_id, address_id, total_price, order_status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, order.getOrderId());
            ps.setString(2, order.getUserId());
            ps.setString(3, order.getAddressId());
            ps.setBigDecimal(4, order.getTotalPrice());
            ps.setString(5, order.getOrderStatus().name());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateStatus(String orderId, Order.OrderStatus status) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE orders SET order_status = ? WHERE order_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setString(2, orderId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(String orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM orders WHERE order_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, orderId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // OrderRepositoryImpl
    @Override
    public List<Order> findAll() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM orders";
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                orders.add(new Order(
                        rs.getString("order_id"),
                        rs.getString("user_id"),
                        rs.getString("address_id"),
                        rs.getBigDecimal("total_price"),
                        Order.OrderStatus.valueOf(rs.getString("order_status")),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }
}