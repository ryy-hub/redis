package com.example.redis.service;

/**
 * @program: redis-demo
 * @description:
 * @author: ryy
 * @create: 2023-09-03 15:37
 **/
public interface RedisMessageReceive<T> {

    void messageHandle(String message);

    String topic();


}
