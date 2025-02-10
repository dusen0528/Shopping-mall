package com.nhnacademy.shoppingmall.common.listener;

import com.nhnacademy.shoppingmall.cart.repository.impl.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.cart.service.impl.CartServiceImpl;
import com.nhnacademy.shoppingmall.point.repository.impl.PointRepositoryImpl;
import com.nhnacademy.shoppingmall.point.service.impl.PointServiceImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.service.impl.OrderServiceImpl;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderDetailRepositoryImpl;
import com.nhnacademy.shoppingmall.cart.service.CartService;
import com.nhnacademy.shoppingmall.point.service.PointService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        // 기본 서비스들 초기화
        ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
        UserService userService = new UserServiceImpl(new UserRepositoryImpl());
        CartService cartService = new CartServiceImpl(new CartRepositoryImpl());
        PointService pointService = new PointServiceImpl(new PointRepositoryImpl());

        // PointChannel 가져오기 (PointThreadInitializer에서 초기화됨)
        RequsetChannel pointChannel = (RequsetChannel) context.getAttribute("pointChannel");

        // OrderService 초기화 (모든 의존성 주입)
        OrderService orderService = new OrderServiceImpl(
                new OrderRepositoryImpl(),
                new OrderDetailRepositoryImpl(),
                productService,
                pointService,
                cartService,
                pointChannel
        );

        // ServletContext에 서비스 등록
        context.setAttribute("productService", productService);
        context.setAttribute("userService", userService);
        context.setAttribute("orderService", orderService);
        context.setAttribute("cartService", cartService);
        context.setAttribute("pointService", pointService);
    }
}
