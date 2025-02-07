package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.ProductStatus;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    // 새 제품 추가
    void save(Product product);

    // 제품 ID로 제품 조회
    Optional<Product> findById(String productId);

    // 모든 제품 조회
    List<Product> findAll();

    // 카테고리별 제품 조회
    List<Product> findByCategory(String categoryId);

    // 제품 정보 업데이트
    void update(Product product);

    // 제품 삭제
    void deleteById(String productId);

    // 제품 상태 변경
    void updateStatus(String productId, ProductStatus status);

    // 재고 업데이트
    void updateStock(String productId, int newStock);

    // 제품명으로 검색
    List<Product> searchByName(String productName);

    // 가격 범위로 제품 검색
    List<Product> findByPriceRange(int minPrice, int maxPrice);

    // 특정 상태의 제품 조회
    List<Product> findByStatus(ProductStatus status);

    // 제품 존재 여부 확인
    boolean existsById(String productId);

    // 총 제품 수 조회
    long count();
}
