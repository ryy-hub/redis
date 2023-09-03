package com.example.redis.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.connection.stream.StreamInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @program: redis-demo
 * @description:
 * @author: ryy
 * @create: 2023-09-03 16:06
 **/
@Component
public class RedisStream {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public String createGroup(String key, String group) {
        return redisTemplate.opsForStream().createGroup(key, group);
    }

    public StreamInfo.XInfoConsumers consumers(String key, String group) {
        return redisTemplate.opsForStream().consumers(key, group);

    }
}
