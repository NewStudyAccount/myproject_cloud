package com.project.cloud.common.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    public Long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    public void setHash(String key, Object hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getHash(String key, Object hashKey) {
        return (T) redisTemplate.opsForHash().get(key, hashKey);
    }

    public void deleteHash(String key, Object hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }
}
