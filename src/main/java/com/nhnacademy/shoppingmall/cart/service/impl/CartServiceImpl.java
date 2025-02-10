package com.nhnacademy.shoppingmall.cart.service.impl;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.cart.repository.CartRepository;
import com.nhnacademy.shoppingmall.cart.service.CartService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public List<Cart> getUserCart(String userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public void addToCart(String userId, String productId, int quantity) {
        // 이미 장바구니에 있는 상품인지 확인
        if (isProductInCart(userId, productId)) {
            Cart existingCart = cartRepository.findByUserIdAndProductId(userId, productId)
                    .orElseThrow(() -> new RuntimeException("Cart not found"));

            // 수량 업데이트
            cartRepository.updateQuantity(existingCart.getCartId(),
                    existingCart.getQuantity() + quantity);
        } else {
            // 새로운 장바구니 아이템 추가
            Cart cart = new Cart(
                    UUID.randomUUID().toString(),
                    userId,
                    productId,
                    quantity,
                    LocalDateTime.now()
            );
            cartRepository.save(cart);
        }
    }

    @Override
    public void updateQuantity(String cartId, int quantity) {
        if (quantity <= 0) {
            // 수량이 0 이하면 삭제
            removeFromCart(cartId);
        } else {
            int result = cartRepository.updateQuantity(cartId, quantity);
            if (result != 1) {
                throw new RuntimeException("Failed to update cart quantity");
            }
        }
    }

    @Override
    public void removeFromCart(String cartId) {
        int result = cartRepository.deleteById(cartId);
        if (result != 1) {
            throw new RuntimeException("Failed to remove item from cart");
        }
    }

    @Override
    public void clearCart(String userId) {
        cartRepository.deleteByUserId(userId);
    }

    @Override
    public boolean isProductInCart(String userId, String productId) {
        return cartRepository.existsByUserIdAndProductId(userId, productId);
    }
}