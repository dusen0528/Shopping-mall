package com.nhnacademy.shoppingmall.point.service;

import com.nhnacademy.shoppingmall.point.domain.Point;
import java.math.BigDecimal;
import java.util.List;

public interface PointService {
    void earnPoints(String userId, String orderId, BigDecimal amount); // 포인트 적립
    void usePoints(String userId, String orderId, BigDecimal amount); // 포인트 사용
    BigDecimal getUserPoints(String userId); // 사용자의 총 포인트 조회
    List<Point> getPointHistory(String userId); // 포인트 이력 조회
    void processOrderPoints(String orderId); // 주문에 대한 포인트 처리
}