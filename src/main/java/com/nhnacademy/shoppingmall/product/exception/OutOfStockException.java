package com.nhnacademy.shoppingmall.product.exception;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String productId, int requestedQuantity, int availableStock) {
        super(String.format("Product %s is out of stock. Requested: %d, Available: %d",
                productId, requestedQuantity, availableStock));
    }
}