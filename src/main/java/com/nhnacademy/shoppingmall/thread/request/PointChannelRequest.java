package com.nhnacademy.shoppingmall.thread.request;

import com.nhnacademy.shoppingmall.point.service.PointService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PointChannelRequest implements ChannelRequest {
    private final String orderId;
    private final PointService pointService;

    public PointChannelRequest(String orderId, PointService pointService) {
        this.orderId = orderId;
        this.pointService = pointService;
    }

    @Override
    public void execute() {
        try {
            log.debug("Processing points for order: {}", orderId);
            pointService.processOrderPoints(orderId);
        } catch (Exception e) {
            log.error("Failed to process points for order: {}", orderId, e);
        }
    }
}