package com.km.redisboot.controller;


import com.alibaba.fastjson.JSONObject;
import com.km.redisboot.domain.User;
import com.km.redisboot.uitl.RedisUitl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private RedisUitl redisUtils;

    @RequestMapping("/setRedis")
    public String setRedisKey(User userEntity) {
        String json = JSONObject.toJSONString(userEntity);
        redisUtils.setString("userEntity", json);
        return "存储成功1";
    }

    @RequestMapping("/getRedis")
    public User setRedisKey() {
        String userEntityJson = redisUtils.getString("userEntity");
        User userEntity = JSONObject.parseObject(userEntityJson, User.class);
        return userEntity;
    }


}
