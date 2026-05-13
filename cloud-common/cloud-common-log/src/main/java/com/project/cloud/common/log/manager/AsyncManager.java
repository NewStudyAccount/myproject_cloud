package com.project.cloud.common.log.manager;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AsyncManager {

    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int MAX_POOL_SIZE = CORE_POOL_SIZE * 2;
    private static final int QUEUE_CAPACITY = 1024;

    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(QUEUE_CAPACITY),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    private static final AsyncManager INSTANCE = new AsyncManager();

    public static AsyncManager getInstance() {
        return INSTANCE;
    }

    public void execute(Runnable task) {
        EXECUTOR.execute(task);
    }

    public void execute(Runnable task, long delay, TimeUnit unit) {
        EXECUTOR.execute(() -> {
            try {
                unit.sleep(delay);
                task.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("异步任务被中断", e);
            }
        });
    }

    public void shutdown() {
        EXECUTOR.shutdown();
        try {
            if (!EXECUTOR.awaitTermination(60, TimeUnit.SECONDS)) {
                EXECUTOR.shutdownNow();
            }
        } catch (InterruptedException e) {
            EXECUTOR.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private AsyncManager() {
    }
}