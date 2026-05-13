package com.project.cloud.common.redis.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisLockService {

    private final RedissonClient redissonClient;

    public void lockExecute(String lockKey, Runnable action) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock(30, TimeUnit.SECONDS);
            action.run();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public <T> T lockExecute(String lockKey, Supplier<T> action) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock(30, TimeUnit.SECONDS);
            return action.get();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public boolean tryLockExecute(String lockKey, long waitTime, long leaseTime, TimeUnit unit, Runnable action) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if (lock.tryLock(waitTime, leaseTime, unit)) {
                action.run();
                return true;
            }
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("获取锁被中断: {}", lockKey, e);
            return false;
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
