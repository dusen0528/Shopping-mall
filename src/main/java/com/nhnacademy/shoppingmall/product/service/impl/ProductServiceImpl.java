package com.nhnacademy.shoppingmall.product.service.impl;

import com.nhnacademy.shoppingmall.product.ProductStatus;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.OutOfStockException;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product getProduct(String productId) {
        return productRepository.findById(productId).orElseThrow(()-> new RuntimeException("Product not found"));

    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.update(product);
    }

    @Override
    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String categoryId) {
        return productRepository.findByCategory(categoryId);
    }

    @Override
    public void updateProductStatus(String productId, ProductStatus status) {
        Product product = getProduct(productId);
        if (product != null) {
            product.setProductStatus(status);
            productRepository.update(product);
        } else {
            throw new RuntimeException("Product not found with id: " + productId);
        }    }

    @Override
    public void updateProductStock(String productId, int newStock) {
        productRepository.updateStock(productId, newStock);
    }

    @Override
    public List<Product> searchProductsByName(String productName) {
        return productRepository.searchByName(productName);
    }

    @Override
    public List<Product> getProductsByPriceRange(int minPrice, int maxPrice) {
        return productRepository.findByPriceRange(minPrice ,maxPrice);
    }

    @Override
    public List<Product> getProductsByStatus(ProductStatus status) {
        return productRepository.findByStatus(status);
    }

    @Override
    public boolean productExists(String productId) {
        return productRepository.existsById(productId);
    }

    @Override
    public long getProductCount() {
        return productRepository.count();
    }
    @Override
    public boolean checkStock(String productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return product.getProductStock() >= quantity;
    }

    @Override
    public synchronized void decreaseStock(String productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getProductStock() < quantity) {
            throw new OutOfStockException(productId, quantity, product.getProductStock());
        }

        int newStock = product.getProductStock() - quantity;
        product.setProductStock(newStock);
        productRepository.updateStock(productId, newStock);
    }

    @Override
    public synchronized void increaseStock(String productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        int newStock = product.getProductStock() + quantity;
        product.setProductStock(newStock);
        productRepository.updateStock(productId, newStock);
    }

    @Override
    public synchronized void reserveStock(String productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getProductStock() < quantity) {
            throw new OutOfStockException(productId, quantity, product.getProductStock());
        }

        // 재고 예약 (실제 감소는 주문 완료 시 수행)
        int newStock = product.getProductStock() - quantity;
        product.setProductStock(newStock);
        productRepository.updateStock(productId, newStock);
    }


}
