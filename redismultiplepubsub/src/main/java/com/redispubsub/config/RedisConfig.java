package com.redispubsub.config;

import com.redispubsub.subscriber.Consumer;
import com.redispubsub.subscriber.ConsumerTwo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }

   /* @Bean
    public ChannelTopic topic(){
        return new ChannelTopic("pubsub:akshay-singh");
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(){
        return new MessageListenerAdapter(new Consumer());
    }

    @Bean
    public ChannelTopic topic2() {
        return new ChannelTopic("pubsub:topic2");
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter1() {
        return new MessageListenerAdapter(new ConsumerTwo());
    }*/
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(lettuceConnectionFactory());
        // Dynamically create and add message listeners with their respective topics
        Map<String, MessageListenerAdapter> topicListeners = new HashMap<>();
        topicListeners.put("pubsub:topic1", new MessageListenerAdapter(new Consumer()));
        topicListeners.put("pubsub:topic2", new MessageListenerAdapter(new ConsumerTwo()));
        // Add more topics and corresponding MessageListenerAdapters as needed

        topicListeners.forEach((key,value)->{
            container.addMessageListener(value, new ChannelTopic(key));
        });

        // second approach
       /* for (Map.Entry<String, MessageListenerAdapter> entry : topicListeners.entrySet()) {
            container.addMessageListener(entry.getValue(), new ChannelTopic(entry.getKey()));
        }*/

        // old pattern
        /*container.addMessageListener(messageListenerAdapter(),topic());
        container.addMessageListener(messageListenerAdapter1(),topic2());*/
        return container;
    }
}
