package com.km.redisboot.controller;


import com.alibaba.fastjson.JSONObject;
import com.km.redisboot.domain.User;
import com.km.redisboot.mapper.UserMapper;
import com.km.redisboot.uitl.RedisTemplateUtils;
import com.km.redisboot.uitl.RedisUitl;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private RedisUitl redisUtils;

    @Autowired
    private RedisTemplateUtils redisTemplate;

    @Resource
    private UserMapper userMapper;

    @RequestMapping("/setRedis")
    public String setRedisKey(User userEntity) {
        String json = JSONObject.toJSONString(userEntity);
        redisUtils.setString("userEntity", json);
        return "存储成功1";
    }


    @RequestMapping("/setRediss")
    public String setRedisKeys(User userEntity) {
        redisTemplate.setObject("userEntity",userEntity);
        return "存储成功";
    }

    @RequestMapping("/getRedi")
    public User setRedisKeys(String key) {
        return (User) redisTemplate.getObjet(key);
    }

    @RequestMapping("/getRedis")
    public User setRedisKey() {
        String userEntityJson = redisUtils.getString("userEntity");
        User userEntity = JSONObject.parseObject(userEntityJson, User.class);
        return userEntity;
    }


    @RequestMapping("/findMemberAll")
    @Cacheable(cacheNames = "member", key = "'findMemberAll'")
    public List<User> findMemberAll() {
        return userMapper.findMemberAll();
    }



}
