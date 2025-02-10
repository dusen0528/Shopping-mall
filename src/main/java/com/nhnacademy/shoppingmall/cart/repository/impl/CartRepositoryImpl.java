package com.nhnacademy.shoppingmall.cart.repository.impl;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.cart.repository.CartRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartRepositoryImpl implements CartRepository {

    @Override
    public Optional<Cart> findById(String cartId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM carts WHERE cart_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cartId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(
                        new Cart(
                                rs.getString("cart_id"),
                                rs.getString("user_id"),
                                rs.getString("product_id"),
                                rs.getInt("quantity"),
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
    public List<Cart> findByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM carts WHERE user_id = ?";
        List<Cart> carts = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                carts.add(new Cart(
                        rs.getString("cart_id"),
                        rs.getString("user_id"),
                        rs.getString("product_id"),
                        rs.getInt("quantity"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return carts;
    }

    @Override
    public Optional<Cart> findByUserIdAndProductId(String userId, String productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM carts WHERE user_id = ? AND product_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setString(2, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new Cart(
                        rs.getString("cart_id"),
                        rs.getString("user_id"),
                        rs.getString("product_id"),
                        rs.getInt("quantity"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int save(Cart cart) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO carts (cart_id, user_id, product_id, quantity) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cart.getCartId());
            ps.setString(2, cart.getUserId());
            ps.setString(3, cart.getProductId());
            ps.setInt(4, cart.getQuantity());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateQuantity(String cartId, int quantity) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE carts SET quantity = ? WHERE cart_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setString(2, cartId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(String cartId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM carts WHERE cart_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cartId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM carts WHERE user_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByUserIdAndProductId(String userId, String productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT 1 FROM carts WHERE user_id = ? AND product_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setString(2, productId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}