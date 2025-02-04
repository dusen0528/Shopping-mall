package com.nhnacademy.shoppingmall.common.filter;

import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j

@WebFilter(urlPatterns = {"/mypage/*"})
public class LoginCheckFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //todo#10 /mypage/ 하위경로의 접근은 로그인한 사용자만 접근할 수 있습니다.
        String path = req.getServletPath();

        if(path.contains("/mypage/")){
            if(req.getSession(false) == null || req.getSession(false).getAttribute("user")==null){
                res.sendRedirect("/login.do");
                return;
            }
        }
        chain.doFilter(req, res);
    }

}