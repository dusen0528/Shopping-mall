package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.ProductStatus;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public void save(Product product) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO products (product_id, category_id, product_name, product_image," +
                " product_price, product_stock, product_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, product.getProductId());
            preparedStatement.setString(2, product.getCategoryId());
            preparedStatement.setString(3, product.getProductName());
            preparedStatement.setString(4, product.getProductImage());
            preparedStatement.setInt(5, product.getProductPrice());
            preparedStatement.setInt(6, product.getProductStock());
            preparedStatement.setString(7, String.valueOf(product.getProductStatus()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<Product> findById(String productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = """
                    SELECT * 
                    FROM products 
                    WHERE product_id=?
                    """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, productId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(new Product(
                        rs.getString("product_id"),
                        rs.getString("category_id"),
                        rs.getString("product_name"),
                        rs.getString("product_image"),
                        rs.getInt("product_price"),
                        rs.getInt("product_stock"),
                        ProductStatus.valueOf(rs.getString("product_status"))
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }



    @Override
    public List<Product> findAll() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM products";

        List<Product> products = new ArrayList<>();
        try(
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ){
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                  products.add(new Product(
                        rs.getString("product_id"),
                        rs.getString("category_id"),
                        rs.getString("product_name"),
                        rs.getString("product_image"),
                        rs.getInt("product_price"),
                        rs.getInt("product_stock"),
                        ProductStatus.valueOf(rs.getString("product_status"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return products;
    }

    @Override
    public List<Product> findByCategory(String categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM products WHERE category_id = ?";
        List<Product> products = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, categoryId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(
                            rs.getString("product_id"),
                            rs.getString("category_id"),
                            rs.getString("product_name"),
                            rs.getString("product_image"),
                            rs.getInt("product_price"),
                            rs.getInt("product_stock"),
                            ProductStatus.valueOf(rs.getString("product_status"))));
                }
            }
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("Error finding products by category", e);
        }

        return products;
    }


    @Override
    public void update(Product product) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE products SET category_id = ?, product_name = ?, product_image = ?, " +
                "product_price = ?, product_stock = ?, product_status = ? WHERE product_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getCategoryId());
            preparedStatement.setString(2, product.getProductName());
            preparedStatement.setString(3, product.getProductImage());
            preparedStatement.setInt(4, product.getProductPrice());
            preparedStatement.setInt(5, product.getProductStock());
            preparedStatement.setString(6, product.getProductStatus().name());
            preparedStatement.setString(7, product.getProductId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("Error updating product", e);
        }

    }

    @Override
    public void deleteById(String productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM products WHERE product_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("Error deleting product", e);
        }
    }

    @Override
    public void updateStatus(String productId, ProductStatus status) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE products SET product_status = ? WHERE product_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setString(2, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("Error updating product status", e);
        }
    }

    @Override
    public void updateStock(String productId, int newStock) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE products SET product_stock = ? WHERE product_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, newStock);
            preparedStatement.setString(2, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("Error updating product stock", e);
        }
    }

    @Override
    public List<Product> searchByName(String productName) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM products WHERE product_name LIKE ?";
        List<Product> products = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + productName + "%");
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(
                            rs.getString("product_id"),
                            rs.getString("category_id"),
                            rs.getString("product_name"),
                            rs.getString("product_image"),
                            rs.getInt("product_price"),
                            rs.getInt("product_stock"),
                            ProductStatus.valueOf(rs.getString("product_status"))));
                }
            }
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("Error searching products by name", e);
        }

        return products;

    }

    @Override
    public List<Product> findByPriceRange(int minPrice, int maxPrice) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM products WHERE product_price BETWEEN ? AND ?";
        List<Product> products = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, minPrice);
            preparedStatement.setInt(2, maxPrice);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(
                            rs.getString("product_id"),
                            rs.getString("category_id"),
                            rs.getString("product_name"),
                            rs.getString("product_image"),
                            rs.getInt("product_price"),
                            rs.getInt("product_stock"),
                            ProductStatus.valueOf(rs.getString("product_status"))
                    ));
                }
            }
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("Error finding products by price range", e);
        }
        return products;
    }


    @Override
    public List<Product> findByStatus(ProductStatus status) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM products WHERE product_status = ?";
        List<Product> products = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status.name());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(
                            rs.getString("product_id"),
                            rs.getString("category_id"),
                            rs.getString("product_name"),
                            rs.getString("product_image"),
                            rs.getInt("product_price"),
                            rs.getInt("product_stock"),
                            ProductStatus.valueOf(rs.getString("product_status"))
                    ));
                }
            }
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("Error finding products by status", e);
        }
        return products;    }

    @Override
    public boolean existsById(String productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT 1 FROM products WHERE product_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, productId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return rs.next(); // 존재하면 true, 없으면 false
            }
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("Error checking product existence", e);
        }
    }

    @Override
    public long count() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT COUNT(*) FROM products";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("Error counting products", e);
        }
    }
}
