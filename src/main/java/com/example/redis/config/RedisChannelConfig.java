package com.example.redis.config;

import com.example.redis.service.RedisMessageReceive;
import jakarta.annotation.Resource;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.List;

/**
 * @program: redis-demo
 * @description:
 * @author: ryy
 * @create: 2023-09-03 15:44
 **/
public class RedisChannelConfig implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Resource
    private List<RedisMessageReceive> redisMessageReceiveList;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RedisChannelConfig.applicationContext = applicationContext;
    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        for (RedisMessageReceive redisMessageReceive : redisMessageReceiveList) {
            String topic = redisMessageReceive.topic();
            MessageListenerAdapter listenerAdapter = buildListener(redisMessageReceive);
            ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
            context.getBeanFactory().registerSingleton(topic, listenerAdapter);
            redisMessageListenerContainer.addMessageListener(listenerAdapter, new PatternTopic(topic));
        }

        return redisMessageListenerContainer;

    }

    /**
     * 构建消息监听器
     * @param receive
     * @return
     */
    private MessageListenerAdapter buildListener(RedisMessageReceive receive) {
        final MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(receive, "messageHandle");
        listenerAdapter.afterPropertiesSet();
        return listenerAdapter;
    }
}
