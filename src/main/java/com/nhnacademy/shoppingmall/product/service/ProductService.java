package com.nhnacademy.shoppingmall.product.service;

import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.ProductStatus;
import com.nhnacademy.shoppingmall.product.exception.OutOfStockException;

import java.util.List;

public interface ProductService {

    // 단일 제품 조회
    Product getProduct(String productId);

    // 제품 저장
    void saveProduct(Product product);

    // 제품 수정
    void updateProduct(Product product);

    // 제품 삭제
    void deleteProduct(String productId);

    // 모든 제품 조회
    List<Product> getAllProducts();

    // 카테고리별 제품 조회
    List<Product> getProductsByCategory(String categoryId);

    // 제품 상태 업데이트
    void updateProductStatus(String productId, ProductStatus status);

    // 제품 재고 업데이트
    void updateProductStock(String productId, int newStock);

    // 제품명 검색
    List<Product> searchProductsByName(String productName);

    // 가격 범위로 제품 검색
    List<Product> getProductsByPriceRange(int minPrice, int maxPrice);

    // 상태별 제품 조회
    List<Product> getProductsByStatus(ProductStatus status);

    // 제품 존재 여부 확인
    boolean productExists(String productId);

    // 전체 제품 수 조회
    long getProductCount();

    // 재고 확인
    boolean checkStock(String productId, int quantity);

    // 재고 감소
    void decreaseStock(String productId, int quantity) throws OutOfStockException;

    // 재고 증가
    void increaseStock(String productId, int quantity);

    // 재고 확인 및 예약
    void reserveStock(String productId, int quantity) throws OutOfStockException;
}
