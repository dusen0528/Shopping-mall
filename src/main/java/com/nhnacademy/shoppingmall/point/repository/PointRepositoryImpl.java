package com.nhnacademy.shoppingmall.point.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.point.domain.Point;
import com.nhnacademy.shoppingmall.point.repository.PointRepository;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class PointRepositoryImpl implements PointRepository {

    @Override
    public Optional<Point> findById(String pointId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM points WHERE point_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pointId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(mapToPoint(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Point> findByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM points WHERE user_id = ? ORDER BY created_at DESC";
        List<Point> points = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                points.add(mapToPoint(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return points;
    }

    @Override
    public List<Point> findByOrderId(String orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM points WHERE order_id = ?";
        List<Point> points = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                points.add(mapToPoint(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return points;
    }

    @Override
    public int save(Point point) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO points (point_id, user_id, point_amount, point_type, order_id, point_status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, point.getPointId());
            ps.setString(2, point.getUserId());
            ps.setBigDecimal(3, point.getPointAmount());
            ps.setString(4, point.getPointType().name());
            ps.setString(5, point.getOrderId());
            ps.setString(6, point.getPointStatus().name());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal getUserTotalPoints(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT COALESCE(SUM(CASE WHEN point_type = 'EARN' THEN point_amount " +
                "WHEN point_type = 'USE' THEN -point_amount END), 0) as total_points " +
                "FROM points WHERE user_id = ? AND point_status = 'SUCCESS'";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal("total_points");
            }
            return BigDecimal.ZERO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Point> findByUserIdAndType(String userId, Point.PointType type) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM points WHERE user_id = ? AND point_type = ? ORDER BY created_at DESC";
        List<Point> points = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setString(2, type.name());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                points.add(mapToPoint(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return points;
    }

    private Point mapToPoint(ResultSet rs) throws SQLException {
        return new Point(
                rs.getString("point_id"),
                rs.getString("user_id"),
                rs.getBigDecimal("point_amount"),
                Point.PointType.valueOf(rs.getString("point_type")),
                rs.getString("order_id"),
                Point.PointStatus.valueOf(rs.getString("point_status")),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}