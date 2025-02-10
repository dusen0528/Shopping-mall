package com.nhnacademy.shoppingmall.order.service.impl;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.cart.service.CartService;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.domain.OrderDetail;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.repository.OrderDetailRepository;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.point.service.PointService;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.exception.OutOfStockException;
import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.thread.request.impl.PointChannelRequest;
import lombok.extern.slf4j.Slf4j;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductService productService;
    private final PointService pointService;
    private final CartService cartService;
    private final RequsetChannel pointChannel;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderDetailRepository orderDetailRepository,
                            ProductService productService,
                            PointService pointService,
                            CartService cartService,
                            RequsetChannel pointChannel) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productService = productService;
        this.pointService = pointService;
        this.cartService = cartService;
        this.pointChannel = pointChannel;
    }


    @Override
    public Order getOrder(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    @Override
    public List<Order> getUserOrders(String userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public void createOrder(Order order, List<OrderDetail> orderDetails) {
        // 재고 체크
        for (OrderDetail detail : orderDetails) {
            if (!productService.checkStock(detail.getProductId(), detail.getQuantity())) {
                throw new OutOfStockException(detail.getProductId(),
                        detail.getQuantity(),
                        productService.getProduct(detail.getProductId()).getProductStock());
            }
        }

        try {
            // 주문 저장
            int result = orderRepository.save(order);
            if (result != 1) {
                throw new RuntimeException("Failed to create order");
            }

            // 주문 상세 정보 저장 및 재고 감소
            for (OrderDetail detail : orderDetails) {
                int detailResult = orderDetailRepository.save(detail);
                if (detailResult != 1) {
                    throw new RuntimeException("Failed to create order detail");
                }
                productService.decreaseStock(detail.getProductId(), detail.getQuantity());
            }

            // 포인트 적립 요청 (비동기)
            try {
                pointChannel.addRequest(new PointChannelRequest(order.getOrderId(), pointService));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Failed to add point request", e);
                // 포인트 적립 실패는 주문 처리에 영향을 주지 않음
            }

        } catch (Exception e) {
            // 실패 시 재고 원복
            for (OrderDetail detail : orderDetails) {
                try {
                    productService.increaseStock(detail.getProductId(), detail.getQuantity());
                } catch (Exception rollbackException) {
                    log.error("Failed to rollback stock for product: {}",
                            detail.getProductId(), rollbackException);
                }
            }
            throw new RuntimeException("Failed to create order", e);
        }
    }

    @Override
    public void updateOrderStatus(String orderId, Order.OrderStatus status) {
        int result = orderRepository.updateStatus(orderId, status);
        if (result != 1) {
            throw new RuntimeException("Failed to update order status");
        }
    }

    @Override
    public void deleteOrder(String orderId) {
        // 주문 상세 정보 조회
        List<OrderDetail> details = orderDetailRepository.findByOrderId(orderId);

        // 주문 삭제
        int result = orderRepository.deleteById(orderId);
        if (result != 1) {
            throw new RuntimeException("Failed to delete order");
        }

        // 재고 복구
        for (OrderDetail detail : details) {
            try {
                productService.increaseStock(detail.getProductId(), detail.getQuantity());
            } catch (Exception e) {
                log.error("Failed to restore stock for product: {}",
                        detail.getProductId(), e);
            }
        }
    }

    @Override
    public void createOrderFromCart(String userId, String addressId) {
        // 1. 장바구니 조회
        List<Cart> cartItems = cartService.getUserCart(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 2. 재고 확인
        for (Cart cartItem : cartItems) {
            if (!productService.checkStock(cartItem.getProductId(), cartItem.getQuantity())) {
                throw new OutOfStockException(cartItem.getProductId(),
                        cartItem.getQuantity(),
                        productService.getProduct(cartItem.getProductId()).getProductStock());
            }
        }

        // 3. 주문 총액 계산 및 주문 생성
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (Cart cartItem : cartItems) {
            BigDecimal price = BigDecimal.valueOf(productService.getProduct(cartItem.getProductId()).getProductPrice());
            totalPrice = totalPrice.add(price.multiply(BigDecimal.valueOf(cartItem.getQuantity())));

            OrderDetail detail = new OrderDetail(
                    UUID.randomUUID().toString(),
                    "", // orderId는 아래에서 설정
                    cartItem.getProductId(),
                    cartItem.getQuantity(),
                    price
            );
            orderDetails.add(detail);
        }

        // 4. Order 객체 생성
        Order order = new Order(
                UUID.randomUUID().toString(),
                userId,
                addressId,
                totalPrice,
                Order.OrderStatus.PENDING,
                LocalDateTime.now()
        );

        try {
            // 5. 주문 저장
            int result = orderRepository.save(order);
            if (result != 1) {
                throw new RuntimeException("Failed to create order");
            }

            // 6. 주문 상세 정보 저장 및 재고 감소
            for (OrderDetail detail : orderDetails) {
                detail.setOrderId(order.getOrderId());
                int detailResult = orderDetailRepository.save(detail);
                if (detailResult != 1) {
                    throw new RuntimeException("Failed to create order detail");
                }
                productService.decreaseStock(detail.getProductId(), detail.getQuantity());
            }

            // 7. 장바구니 비우기
            cartService.clearCart(userId);

            // 8. 포인트 적립 요청 (비동기)
            try {
                pointChannel.addRequest(new PointChannelRequest(order.getOrderId(), pointService));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Failed to add point request", e);
            }

        } catch (Exception e) {
            // 실패 시 재고 원복
            for (OrderDetail detail : orderDetails) {
                try {
                    productService.increaseStock(detail.getProductId(), detail.getQuantity());
                } catch (Exception rollbackException) {
                    log.error("Failed to rollback stock for product: {}",
                            detail.getProductId(), rollbackException);
                }
            }
            throw new RuntimeException("Failed to create order from cart", e);
        }
    }
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}