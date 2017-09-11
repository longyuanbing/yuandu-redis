package com.yuandu.redis.config;

import com.yuandu.redis.serializer.RedisObjectSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.*;

import java.lang.reflect.Method;

@Configuration
//@PropertySource(value = "classpath:/redis.properties")
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.timeout}")
    private int timeout;

    @Value("${redis.pool.maxIdle}")
    private int maxIdle;
    @Value("${redis.pool.minIdle}")
    private int minIdle;

    @Value("${redis.pool.maxTotal}")
    private int maxTotal;

    @Value("${redis.pool.maxWaitMillis}")
    private int maxWaitMillis;

    @Value("${redis.pool.testOnBorrow}")
    private Boolean testOnBorrow;

    @Value("${redis.pool.testOnReturn}")
    private Boolean testOnReturn;

    @Value("${redis.pool.testWhileIdle}")
    private Boolean testWhileIdle;

	/*
     * @Value("${spring.redis.password}") private String password;
	 */


    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    // Jedis poolconfig
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        logger.info("jedisPoolConfig注入成功！！");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMinIdle(minIdle);//设置最小空闲数
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestOnReturn(testOnReturn);
        //Idle时进行连接扫描
        jedisPoolConfig.setTestWhileIdle(testWhileIdle);
        return jedisPoolConfig;
    }

    // Jedis连接池
    @Bean
    public JedisPool redisPoolFactory(JedisPoolConfig jedisPoolConfig) {
        logger.info("JedisPool注入成功！！");
        logger.info("redis地址：" + host + ":" + port);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);

        return jedisPool;
    }


    @Bean
    public JedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        System.out.println("JedisConnectionFactory host = " + host);
        System.out.println("JedisConnectionFactory port = " + port);
        logger.info("JedisConnectionFactory host = " + host);
        logger.info("JedisConnectionFactory port = " + port);
        factory.setHostName(host);
        factory.setPort(port);
        factory.setPoolConfig(jedisPoolConfig);
        factory.setTimeout(timeout); // 设置连接超时时间
        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisByteTemplate(JedisConnectionFactory factory) {
        logger.info("======");
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        setByteSerializer(template); // 设置序列化工具，这样ReportBean不需要实现Serializable接口
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 设置序列化方式
     *
     * @param template
     */
    private void setByteSerializer(RedisTemplate<String, Object> template) {
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new RedisObjectSerializer());
    }


    @Bean
    public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // Number of seconds before expiration. Defaults to unlimited (0)
        cacheManager.setDefaultExpiration(10); // 设置key-value超时时间
        return cacheManager;
    }

//    @Bean
//    public JedisCluster JedisClusterFactory() {
//        logger.info("JedisCluster创建！！");
//        logger.info("redis地址：" + host + ":" + port);
//        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        jedisClusterNodes.add(new HostAndPort(host, port));
//        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, jedisPoolConfig);
//        return jedisCluster;
//    }


//    @Bean
//    public RedisTemplate<String, String> redisJsonTemplate(RedisConnectionFactory factory) {
//        StringRedisTemplate template = new StringRedisTemplate(factory);
//        setJsonSerializer(template); // 设置序列化工具，这样ReportBean不需要实现Serializable接口
//        template.afterPropertiesSet();
//        return template;
//    }
//
//    /**
//     * 设置序列化方式
//     * @param template
//     */
//    private void setJsonSerializer(StringRedisTemplate template) {
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
//                Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//    }


}
