package com.nhnacademy.shoppingmall.category.repository.impl;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

public class CategoryRepositoryImpl implements CategoryRepository {
    @Override
    public Optional<Category> findById(String categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT category_id, category_name FROM categories WHERE category_id=?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, categoryId);
            try(ResultSet rs = preparedStatement.executeQuery()){
                if(rs.next()){
                    return Optional.of(new Category(
                       rs.getString("category_id"),
                       rs.getString("category_name")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return Optional.empty();
    }

    @Override
    public List<Category> findAll() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT category_id, category_name FROM categories";
        List<Category> categories = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            try(ResultSet rs = preparedStatement.executeQuery()){
                categories.add(new Category(
                   rs.getString("category_id"),
                   rs.getString("category_name")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categories;
    }

    @Override
    public int save(Category category) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO categories (category_id, category_name) VALUES (?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, category.getCategoryId());
            preparedStatement.setString(2, category.getCategoryName());
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Category category) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE categories SET category_name=? WHERE category_id=?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setString(2, category.getCategoryId());
            return  preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int delete(String categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM categories WHERE category_id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, categoryId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countById(String categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT COUNT(*) FROM categories WHERE category_id = ?";
        try(
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ) {
            preparedStatement.setString(1, categoryId);
            try(
                    ResultSet rs = preparedStatement.executeQuery()
                    ){
                return rs.next() ? rs.getInt(1):0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
