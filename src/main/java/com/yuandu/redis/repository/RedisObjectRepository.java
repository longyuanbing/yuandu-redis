package com.yuandu.redis.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisObjectRepository extends RedisBaseRepository {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * @param key
     * @param expTime
     * @param obj
     * @param <T>
     */
    public <T extends Object> void add(String key, T obj, Long expTime) {
        expTime = super.getExpiredTime(expTime);
        redisTemplate.opsForValue().set(key, obj, expTime, TimeUnit.
                MINUTES);
    }

    /**
     * @param key
     * @param <T>
     */
    public <T extends Object> Object get(String key) {
        Object obj = redisTemplate.opsForValue().get(key);
        return obj;
    }

    /**
     * @param key
     */
    public void remove(String key) {
        redisTemplate.opsForValue().getOperations().delete(key);
    }


    /**
     * @param key
     * @param step
     * @return
     */
    public Long increase(String key, Long step) {
        return redisTemplate.opsForValue().increment(key, step);
    }

    /**
     * @param key
     * @param step
     * @return
     */
    public Double increase(String key, Double step) {
        return redisTemplate.opsForValue().increment(key, step);
    }


}
