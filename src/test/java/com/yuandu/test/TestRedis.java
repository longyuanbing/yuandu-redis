package com.yuandu.test;//package com.yuandu.test;
//
//import com.yuandu.redis.APP;
//import com.yuandu.redis.User;
//import com.yuandu.redis.repository.RedisListRepository;
//import com.yuandu.redis.repository.RedisObjectRepository;
//import com.yuandu.utils.date.DateUtils;
//import com.yuandu.utils.json.JsonUtils;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.stereotype.Repository;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.Date;
//import java.util.List;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = APP.class)
//public class TestRedis {
//
//    @Autowired
//    private RedisObjectRepository redisTemplate;
//
//    private Logger logger = LogManager.getLogger(TestRedis.class);
//
//    @org.junit.Test
//    public void test() throws Exception {
//        User user = new User();
//        user.setId(1L);
//        user.setName("龙");
//        user.setAge(20);
//        user.setBirthday(DateUtils.getAfterDate(new Date(), 1));
//        redisTemplate.add("user", user, 760L);
//        redisTemplate.add("user1", "11111", 760L);
//        User u = (User)redisTemplate.get("use");
//        String u1 = (String)redisTemplate.get("user1");
//        logger.info("u="+ JsonUtils.toJson(u1));
//    }
//
//    @org.junit.Test
//    public void testObj() throws Exception {
//        User u = (User)redisTemplate.get("user");
//        logger.info("u="+ JsonUtils.toJson(u));
//    }
//}
