package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/orders.do")
public class AdminOrderListController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("userRole") != User.Auth.ROLE_ADMIN) {
            return "redirect:/login.do";
        }
        OrderService orderService = (OrderService) req.getServletContext().getAttribute("orderService");
        req.setAttribute("orders", orderService.getAllOrders());
        return "shop/admin/order_list";
    }
}