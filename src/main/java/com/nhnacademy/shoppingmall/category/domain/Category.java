package com.nhnacademy.shoppingmall.category.domain;

public class Category {
    private String categoryId;
    private String categoryName;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(String categoryId, String categoryName){
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }



}
