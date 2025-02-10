package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.category.service.impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/product/add.do")
public class ProductAddFormController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("userRole") != User.Auth.ROLE_ADMIN) {
            return "redirect:/login.do";
        }

        CategoryService categoryService = (CategoryService) req.getServletContext().getAttribute("categoryService");

        try {
            req.setAttribute("categories", categoryService.findAllCategories());
            return "shop/admin/product_add";
        } catch (Exception e) {
            log.error("Error loading form", e);
            return "redirect:/error";
        }
    }
}