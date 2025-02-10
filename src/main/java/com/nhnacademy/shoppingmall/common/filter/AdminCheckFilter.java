package com.nhnacademy.shoppingmall.common.filter;

import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@WebFilter(urlPatterns = "/admin/*")
public class AdminCheckFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            log.warn("AdminCheckFilter - Unauthorized access: No session or user");
            res.sendRedirect("/login.do");
            return;
        }

        User user = (User) session.getAttribute("user");
        log.debug("AdminCheckFilter - userRole: {}", user.getUserAuth());

        if (user.getUserAuth() != User.Auth.ROLE_ADMIN) {
            log.warn("AdminCheckFilter - Unauthorized access attempt by user: {}", user.getUserId());
            res.sendRedirect("/index.do");
            return;
        }

        chain.doFilter(req, res);
    }
}