package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping(method = RequestMapping.Method.GET, value = "/order/detail.do")
public class OrderDetailController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("user") == null) {
            return "redirect:/login.do";
        }

        String orderId = req.getParameter("orderId");
        OrderService orderService = (OrderService) req.getServletContext().getAttribute("orderService");

        req.setAttribute("order", orderService.getOrder(orderId));
        return "shop/order/detail";
    }
}