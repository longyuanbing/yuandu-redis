package com.yuandu.redis.repository;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisListRepository extends RedisBaseRepository {

    private Logger logger = LogManager.getLogger(RedisListRepository.class);

    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    /**
     * @param key
     * @param expTime
     * @param objs
     * @param <T>
     */
    public <T extends Object> void add(String key, List<T> objs, Long expTime) {
        expTime = super.getExpiredTime(expTime);
        redisTemplate.expire(key, expTime, TimeUnit.SECONDS);
        redisTemplate.opsForList().rightPushAll(key, objs);
    }

    /**
     * @param key
     * @param expTime second
     * @param obj
     * @param <T>
     */
    public <T extends Object> void add(String key, T obj, Long expTime) {
        expTime = super.getExpiredTime(expTime);
        redisTemplate.expire(key, expTime, TimeUnit.SECONDS);
        redisTemplate.opsForList().rightPush(key, obj);
    }

    /**
     * @param key
     * @param obj
     * @param <T>
     */
    public <T extends Object> void remove(String key, Long expTime, T... obj) {
        redisTemplate.expire(key, expTime, TimeUnit.SECONDS);
        redisTemplate.opsForList().rightPushAll(key, obj);
    }


    /**
     * 删除知道缓存中指定value
     *
     * @param key
     * @param value
     * @param <T>
     */
    public <T extends Object> void remove(String key, T value) {
        /**
         * count> 0：删除等于从头到尾移动的值的元素。
         * count <0：删除等于从尾到头移动的值的元素。
         * count = 0：删除等于value的所有元素。
         */
        int count = 0;
        redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * @param key
     * @param <T>
     * @return
     */
    public <T extends Object> List<T> get(String key) {
        List<T> list = (List<T>) redisTemplate.opsForList().range(key, 0, -1);
        return list;
    }
}
