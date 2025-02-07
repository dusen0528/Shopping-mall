package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/product/add.do")
public class ProductAddController implements BaseController {

    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session == null || !"ROLE_ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/login.do";
        }

        String productId = req.getParameter("productId");
        String categoryId = req.getParameter("categoryId");
        String productName = req.getParameter("productName");
        String productImage = req.getParameter("productImage");
        int productPrice = Integer.parseInt(req.getParameter("productPrice"));
        int productStock = Integer.parseInt(req.getParameter("productStock"));

        Product product = new Product(productId, categoryId, productName, productImage, productPrice, productStock, null);
        productService.saveProduct(product);

        return "redirect:/admin/products.do";
    }
}
