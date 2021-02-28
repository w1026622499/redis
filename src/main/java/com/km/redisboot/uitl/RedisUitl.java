package com.km.redisboot.uitl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUitl {

    /**
     * 获取redis模板
     * */

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setString(String key, String value) {
        setString(key, value, null);
    }

    /**
     * 设置有效期为每秒
     */
    public void setString(String key, String value, Long timeOut) {
        stringRedisTemplate.opsForValue().set(key, value);
        if (timeOut != null) {
            stringRedisTemplate.expire(key, timeOut, TimeUnit.SECONDS);
        }
    }

    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }



}
