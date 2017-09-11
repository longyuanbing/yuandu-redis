package com.yuandu.redis.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisHashRepository extends RedisBaseRepository {

    private Logger logger = LoggerFactory.getLogger(RedisHashRepository.class);

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * @param key
     * @param expTime
     * @param map
     */
    public <T extends Object> void putAll(String key, Map<String, T> map, Long expTime) {
        expTime = super.getExpiredTime(expTime);
        HashOperations<String, String, T> hash = redisTemplate.opsForHash();
        redisTemplate.expire("max", expTime, TimeUnit.SECONDS);
        hash.putAll("lpMap", map);
    }

    /**
     * @param key
     * @param expTime
     * @param obj
     */
    public <T extends Object> void put(String key, String mapkey, T obj, Long expTime) {
        expTime = super.getExpiredTime(expTime);
        HashOperations<String, String, T> hash = redisTemplate.opsForHash();
        redisTemplate.expire("max", expTime, TimeUnit.SECONDS);
        hash.put(key, mapkey, obj);
    }

    /**
     * @param key
     * @param <T>
     * @return
     */
    public <T extends Object> Map<String, T> get(String key) {
        HashOperations<String, String, T> hash = redisTemplate.opsForHash();
        return hash.entries("lpMap");
    }


    /**
     * @param key
     * @param <T>
     * @return
     */
    public <T extends Object> T get(String key, String mapKey) {
        HashOperations<String, String, T> hash = redisTemplate.opsForHash();
        T obj = hash.get(key, mapKey);
        return obj;
    }

    /**
     * @param key
     */
    public <T extends Object> void remove(String key, String mapKey) {
        HashOperations<String, String, T> hash = redisTemplate.opsForHash();
        hash.delete(key, mapKey);
    }


}
