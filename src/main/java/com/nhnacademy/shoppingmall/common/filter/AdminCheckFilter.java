package com.nhnacademy.shoppingmall.common.filter;

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
        String path = req.getServletPath();

        if (path.startsWith("/admin/")) {
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                log.warn("AdminCheckFilter - Unauthorized access: No session or user");
                res.sendRedirect("/login.do");
                return;
            }

            String userRole = (String) session.getAttribute("userRole");
            log.debug("AdminCheckFilter - userRole: " + userRole); // 로깅 추가

            if (!"ROLE_ADMIN".equals(userRole)) {
                log.warn("AdminCheckFilter - Unauthorized access: User attempted to access admin resource");
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Admin access only");
                return;
            }
        }

        chain.doFilter(req, res);
    }
}

