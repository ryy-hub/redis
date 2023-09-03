package com.example.redis.config;

import com.example.redis.service.RedisStreamMessageReceive;
import com.example.redis.utils.RedisStream;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

/**
 * @program: redis-demo
 * @description:
 * @author: ryy
 * @create: 2023-09-03 16:05
 **/
@Slf4j
@Configuration
public class RedisStreamConfig {
    @Resource
    private List<RedisStreamMessageReceive> redisStreamMessageReceives;

    @Resource
    private RedisStream redisStream;

    @Bean
    @SuppressWarnings("all")
    public StreamMessageListenerContainer<String, ObjectRecord<String, Object>> container(RedisConnectionFactory factory) {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, Object>> listenerContainerOptions = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .batchSize(1000)
                .pollTimeout(Duration.ofSeconds(5))
                .targetType(Object.class)
                .build();
        StreamMessageListenerContainer<String, ObjectRecord<String, Object>> streamMessageListenerContainer = StreamMessageListenerContainer.create(factory, listenerContainerOptions);

        for (RedisStreamMessageReceive redisStreamMessageReceive : redisStreamMessageReceives) {
            String streamKey = redisStreamMessageReceive.streamKey();
            String group = redisStreamMessageReceive.group();
            //校验消费者组是否存在
            checkGroup(streamKey, group);
            streamMessageListenerContainer.receive(
                    Consumer.from(group, group),
                    StreamOffset.create(streamKey, ReadOffset.lastConsumed()),
                    redisStreamMessageReceive
            );
        }

        streamMessageListenerContainer.start();
        return streamMessageListenerContainer;

    }

    /**
     * 校验 消费者组
     *
     * @param key
     * @param group
     */
    private void checkGroup(String key, String group) {
        StreamInfo.XInfoConsumers xInfoConsumers = null;

        try {
            xInfoConsumers = redisStream.consumers(key, group);
        } catch (Exception e) {
            log.error("", e);
        }

        if (Objects.isNull(xInfoConsumers)) {
            redisStream.createGroup(key, group);
        }
    }

}
