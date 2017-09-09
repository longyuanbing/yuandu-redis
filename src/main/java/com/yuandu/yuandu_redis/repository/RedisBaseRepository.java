package com.yuandu.yuandu_redis.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisBaseRepository {

    /**
     * 默认到期时间为7天
     */
    protected Long DEFAULT_EXPTIME = 86400L;

    private Logger logger = LoggerFactory.getLogger(RedisHashRepository.class);

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * @param key
     * @return
     */
    public Boolean exists(String key) {
        return redisTemplate.opsForValue().getOperations().hasKey(key);
    }

    /**
     * @param key
     */
    public void removeAll(String key) {
        redisTemplate.delete(key);
    }

    /**
     * @param key
     * @return 返回有效期（秒）
     */
    public Long getExpired(String key) {
        if (this.exists(key)) {
            return redisTemplate.getExpire(key, TimeUnit.SECONDS);
        }
        return null;
    }

    /**
     *
     * @param expTime
     * @return
     */
    protected Long getExpiredTime(Long expTime) {
        if(expTime == null){
            return DEFAULT_EXPTIME;
        }
        return expTime;
    }
}
