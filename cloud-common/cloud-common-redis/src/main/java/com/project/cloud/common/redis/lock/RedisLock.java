package com.project.cloud.common.redis.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * Redis 分布式锁
 */
@Slf4j
public class RedisLock {

    private final RedissonClient redissonClient;

    public RedisLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 加锁
     */
    public boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit) {
        try {
            RLock lock = redissonClient.getLock(lockKey);
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("获取分布式锁失败: {}", lockKey, e);
            return false;
        }
    }

    /**
     * 加锁（默认等待3秒，锁定30秒）
     */
    public boolean tryLock(String lockKey) {
        return tryLock(lockKey, 3, 30, TimeUnit.SECONDS);
    }

    /**
     * 释放锁
     */
    public void unlock(String lockKey) {
        try {
            RLock lock = redissonClient.getLock(lockKey);
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        } catch (Exception e) {
            log.error("释放分布式锁失败: {}", lockKey, e);
        }
    }

    /**
     * 判断是否被锁定
     */
    public boolean isLocked(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        return lock.isLocked();
    }

    /**
     * 判断当前线程是否持有锁
     */
    public boolean isHeldByCurrentThread(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        return lock.isHeldByCurrentThread();
    }
}
