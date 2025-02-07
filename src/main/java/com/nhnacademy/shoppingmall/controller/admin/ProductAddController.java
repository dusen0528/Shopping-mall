package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.ProductStatus;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Slf4j
@Transactional
@RequestMapping(
        method = RequestMapping.Method.POST,
        value = "/admin/product/add.do"
)
public class ProductAddController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // 파라미터 검증 로직 추가
            String productId = req.getParameter("productId");
            if(productId == null || productId.isEmpty()) {
                throw new IllegalArgumentException("Product ID is required");
            }

            Product product = new Product(
                    productId,
                    req.getParameter("categoryId"),
                    req.getParameter("productName"),
                    req.getParameter("productImage"),
                    Integer.parseInt(req.getParameter("productPrice")),
                    Integer.parseInt(req.getParameter("productStock")),
                    ProductStatus.ON_SALE
            );

            productService.saveProduct(product);
            return "redirect:/admin/products.do";

        } catch (NumberFormatException e) {
            log.error("Number format exception", e);
            return "redirect:/admin/product/add.do?error=invalid_input";
        }
    }
}
