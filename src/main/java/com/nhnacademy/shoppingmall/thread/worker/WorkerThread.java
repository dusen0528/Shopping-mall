package com.nhnacademy.shoppingmall.thread.worker;

import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WorkerThread extends Thread {
    private final RequestChannel channel;
    private volatile boolean running = true;

    public WorkerThread(RequestChannel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        while (running && !Thread.currentThread().isInterrupted()) {
            try {
                ChannelRequest request = channel.getRequest();
                request.execute();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                log.error("Error processing request", e);
            }
        }
    }

    public void stopWorker() {
        running = false;
        interrupt();
    }
}