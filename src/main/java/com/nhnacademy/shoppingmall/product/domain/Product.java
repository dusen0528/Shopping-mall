package com.nhnacademy.shoppingmall.product.domain;

import com.nhnacademy.shoppingmall.product.ProductStatus;

import java.time.LocalDateTime;

public class Product {
    private String productId;
    private String categoryId;
    private String productName;
    private String productImage;
    private int productPrice;
    private int productStock;
    private ProductStatus productStatus;


    public Product(String productId, String categoryId, String productName, String productImage,
                   int productPrice, int productStock, ProductStatus productStatus) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productStatus = productStatus;

    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

}

