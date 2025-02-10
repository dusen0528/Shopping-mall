package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping(method = RequestMapping.Method.POST, value = "/order/create.do")
public class CreateOrderController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("user") == null) {
            return "redirect:/login.do";
        }

        String userId = (String) session.getAttribute("userId");
        String addressId = req.getParameter("addressId");

        try {
            OrderService orderService = (OrderService) req.getServletContext().getAttribute("orderService");
            orderService.createOrderFromCart(userId, addressId);
            return "redirect:/order/success.do";
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            return "shop/order/error";
        }
    }
}