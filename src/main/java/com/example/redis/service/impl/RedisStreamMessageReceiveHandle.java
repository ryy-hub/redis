package com.example.redis.service.impl;

import com.example.redis.service.RedisStreamMessageReceive;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.stereotype.Component;

/**
 * @program: redis-demo
 * @description:
 * @author: ryy
 * @create: 2023-09-03 16:03
 **/
@Component
public class RedisStreamMessageReceiveHandle implements RedisStreamMessageReceive {

    @Override
    public String streamKey() {
        return null;
    }

    @Override
    public String group() {
        return null;
    }

    @Override
    public void onMessage(ObjectRecord<String, Object> message) {

    }
}
