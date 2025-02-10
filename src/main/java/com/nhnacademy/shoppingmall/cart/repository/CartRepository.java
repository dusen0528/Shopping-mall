package com.nhnacademy.shoppingmall.cart.repository;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import java.util.List;
import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findById(String cartId);
    List<Cart> findByUserId(String userId);
    Optional<Cart> findByUserIdAndProductId(String userId, String productId);
    int save(Cart cart);
    int updateQuantity(String cartId, int quantity);
    int deleteById(String cartId);
    int deleteByUserId(String userId);
    boolean existsByUserIdAndProductId(String userId, String productId);
}