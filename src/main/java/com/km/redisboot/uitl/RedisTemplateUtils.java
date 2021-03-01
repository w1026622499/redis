package com.km.redisboot.uitl;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Wh
 * @title
 * @description
 * @createTime 2021年03月01日 10:37:00
 * @modifier：Wh
 * @modification_time：2021-03-01 10:37
 */
@Component
public class RedisTemplateUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void setObject(String key, Object value, Long timeOut) {
        redisTemplate.opsForValue().set(key, value);
        if (timeOut != null) {
            redisTemplate.expire(key, timeOut, TimeUnit.SECONDS);
        }
    }

    public void setObject(String key, Object object) {
        redisTemplate.opsForValue().set(key, object);
    }

    public Object getObjet(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
