package com.nhnacademy.shoppingmall.category.repository;

import com.nhnacademy.shoppingmall.category.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Optional<Category> findById(String categoryId);
    List<Category> findAll();
    int save(Category category);
    int update(Category category);
    int delete(String categoryId);
    int countById(String categoryId);
}
