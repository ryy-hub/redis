package com.example.redis.service.impl;

import com.example.redis.service.RedisMessageReceive;
import org.springframework.stereotype.Component;

/**
 * @program: redis-demo
 * @description:
 * @author: ryy
 * @create: 2023-09-03 15:39
 **/
@Component
public class RedisMessageReceiveHandle implements RedisMessageReceive<String> {


    @Override
    public void messageHandle(String message) {

    }

    @Override
    public String topic() {
        return null;
    }
}
