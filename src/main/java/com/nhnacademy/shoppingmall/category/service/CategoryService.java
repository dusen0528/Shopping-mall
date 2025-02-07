package com.nhnacademy.shoppingmall.category.service;

import com.nhnacademy.shoppingmall.category.domain.Category;

import java.util.List;

public interface CategoryService {
    Category findCategory(String categoryId);
    List<Category> findAllCategories();
    void createCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(String categoryId);
    boolean exists(String categoryId);
}
