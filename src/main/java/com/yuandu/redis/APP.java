package com.yuandu.redis;//package com.yuandu.redis;
//
//import com.yuandu.redis.repository.RedisListRepository;
//import com.yuandu.utils.date.DateUtils;
//import com.yuandu.utils.json.JsonUtils;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
//import org.apache.poi.ss.usermodel.DateUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.Date;
//import java.util.List;
//
//@SpringBootApplication
//@EnableCaching
//@EnableConfigurationProperties
//@Controller
//public class APP {
//
//    public static void main(String[] args){
//        SpringApplication.run(APP.class);
//    }
//
//    @Autowired
//    private RedisListRepository redisTemplate;
//
//    private Logger logger = LogManager.getLogger(APP.class);
//
//    @RequestMapping(value = "/setUser", method = RequestMethod.GET)
//    @ResponseBody
//    public User setUser() {
//        User user = new User();
//        user.setId(1L);
//        user.setName("龙");
//        user.setAge(20);
//        user.setBirthday(DateUtils.getAfterDate(new Date(), 1));
//        redisTemplate.add("user", user, 60L);
//        List<User> u = redisTemplate.get("user");
//        logger.info("u="+ JsonUtils.toJson(u));
//        return user;
//    }
//
//    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
//    @ResponseBody
//    public User getUser() {
//        List<User> u = redisTemplate.get("user");
//        logger.info("u="+ JsonUtils.toJson(u));
//        return u != null? u.get(0): null;
//    }
//
//
//}
