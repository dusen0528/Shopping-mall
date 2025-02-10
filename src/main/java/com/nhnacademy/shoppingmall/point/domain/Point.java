package com.nhnacademy.shoppingmall.point.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Point {
    public enum PointType {
        EARN, USE
    }

    public enum PointStatus {
        SUCCESS, FAILED
    }

    private String pointId;
    private String userId;
    private BigDecimal pointAmount;
    private PointType pointType;
    private String orderId;
    private PointStatus pointStatus;
    private LocalDateTime createdAt;

    public Point(String pointId, String userId, BigDecimal pointAmount,
                 PointType pointType, String orderId, PointStatus pointStatus,
                 LocalDateTime createdAt) {
        this.pointId = pointId;
        this.userId = userId;
        this.pointAmount = pointAmount;
        this.pointType = pointType;
        this.orderId = orderId;
        this.pointStatus = pointStatus;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getPointId() { return pointId; }
    public void setPointId(String pointId) { this.pointId = pointId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public BigDecimal getPointAmount() { return pointAmount; }
    public void setPointAmount(BigDecimal pointAmount) { this.pointAmount = pointAmount; }
    public PointType getPointType() { return pointType; }
    public void setPointType(PointType pointType) { this.pointType = pointType; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public PointStatus getPointStatus() { return pointStatus; }
    public void setPointStatus(PointStatus pointStatus) { this.pointStatus = pointStatus; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}