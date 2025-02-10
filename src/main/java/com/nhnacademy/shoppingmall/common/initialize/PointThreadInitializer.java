package com.nhnacademy.shoppingmall.common.initialize;

import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.thread.worker.WorkerThread;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PointThreadInitializer implements ServletContextListener {
    private static final int CHANNEL_BOUND = 100;
    private static final int WORKER_COUNT = 5;
    private RequestChannel channel;
    private WorkerThread[] workers;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        channel = new RequestChannel(CHANNEL_BOUND);
        context.setAttribute("pointChannel", channel);

        workers = new WorkerThread[WORKER_COUNT];
        for (int i = 0; i < WORKER_COUNT; i++) {
            workers[i] = new WorkerThread(channel);
            workers[i].start();
        }
        log.info("Point processing worker threads initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (workers != null) {
            for (WorkerThread worker : workers) {
                if (worker != null) {
                    worker.stopWorker();
                }
            }
        }
        log.info("Point processing worker threads stopped");
    }
}