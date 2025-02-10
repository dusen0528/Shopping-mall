package com.nhnacademy.shoppingmall.point.service.impl;

import com.nhnacademy.shoppingmall.point.domain.Point;
import com.nhnacademy.shoppingmall.point.repository.PointRepository;
import com.nhnacademy.shoppingmall.point.service.PointService;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Slf4j
public class PointServiceImpl implements PointService {
    private final PointRepository pointRepository;
    private static final BigDecimal POINT_RATE = new BigDecimal("0.10"); // 10% 적립률

    public PointServiceImpl(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    @Override
    public void earnPoints(String userId, String orderId, BigDecimal amount) {
        Point point = new Point(
                UUID.randomUUID().toString(),
                userId,
                amount,
                Point.PointType.EARN,
                orderId,
                Point.PointStatus.SUCCESS,
                LocalDateTime.now()
        );

        try {
            pointRepository.save(point);
        } catch (Exception e) {
            // 포인트 적립 실패 시 상태를 FAILED로 변경하고 로그 기록
            point.setPointStatus(Point.PointStatus.FAILED);
            pointRepository.save(point);
            // 로그 기록
            throw new RuntimeException("Failed to earn points", e);
        }
    }

    @Override
    public void usePoints(String userId, String orderId, BigDecimal amount) {
        // 현재 보유 포인트 확인
        BigDecimal currentPoints = getUserPoints(userId);
        if (currentPoints.compareTo(amount) < 0) {
            throw new RuntimeException("Not enough points");
        }

        Point point = new Point(
                UUID.randomUUID().toString(),
                userId,
                amount,
                Point.PointType.USE,
                orderId,
                Point.PointStatus.SUCCESS,
                LocalDateTime.now()
        );

        pointRepository.save(point);
    }

    @Override
    public BigDecimal getUserPoints(String userId) {
        return pointRepository.getUserTotalPoints(userId);
    }

    @Override
    public List<Point> getPointHistory(String userId) {
        return pointRepository.findByUserId(userId);
    }

    @Override
    public void processOrderPoints(String orderId) {
        // 주문 금액의 10% 포인트 적립
        // 실제 구현에서는 Order 정보를 가져와서 처리
        // 예시: Order order = orderService.getOrder(orderId);
        // BigDecimal pointAmount = order.getTotalPrice().multiply(POINT_RATE);
        // earnPoints(order.getUserId(), orderId, pointAmount);

        // 이 메서드는 OrderService에서 비동기로 호출되어야 함
        try {
            // 포인트 적립 로직
            // ...
        } catch (Exception e) {
            // 에러 로그만 기록하고 주문 처리는 계속 진행
            log.error("Failed to process points for order: {}", orderId, e);
        }
    }
}