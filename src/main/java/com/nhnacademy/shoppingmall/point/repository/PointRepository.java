package com.nhnacademy.shoppingmall.point.repository;

import com.nhnacademy.shoppingmall.point.domain.Point;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PointRepository {
    Optional<Point> findById(String pointId);
    List<Point> findByUserId(String userId);
    List<Point> findByOrderId(String orderId);
    int save(Point point);
    BigDecimal getUserTotalPoints(String userId);
    List<Point> findByUserIdAndType(String userId, Point.PointType type);
}