package com.example.redis.service;

import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;

/**
 * @program: redis-demo
 * @description:
 * @author: ryy
 * @create: 2023-09-03 15:37
 **/
public interface RedisStreamMessageReceive extends StreamListener<String, ObjectRecord<String,Object>> {

    //void messageHandle(String message);

    String streamKey();

    String group();


}
