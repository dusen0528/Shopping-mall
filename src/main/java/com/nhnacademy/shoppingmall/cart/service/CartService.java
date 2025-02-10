package com.nhnacademy.shoppingmall.cart.service;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import java.util.List;

public interface CartService {
    // 장바구니 조회
    List<Cart> getUserCart(String userId);

    // 장바구니에 상품 추가
    void addToCart(String userId, String productId, int quantity);

    // 장바구니 상품 수량 변경
    void updateQuantity(String cartId, int quantity);

    // 장바구니 상품 삭제
    void removeFromCart(String cartId);

    // 장바구니 비우기
    void clearCart(String userId);

    // 장바구니에 이미 있는 상품인지 확인
    boolean isProductInCart(String userId, String productId);
}