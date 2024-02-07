package com.redispubsub.publisher;

import com.redispubsub.dto.Product;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublisherController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource(name="redisTemplate")
    private ListOperations<String, String> listOps;

 /*   @Autowired
    @Qualifier("topic")
    private ChannelTopic channelTopic;*/

    @PostMapping("/publish")
    public String publish(@RequestBody Product product){
      //  System.out.println("Topic: "+channelTopic.getTopic());

        publish("pubsub:topic1" , product.toString());
        publish( "pubsub:topic2", "Hello Im topic No 2");
        return "Message is Published...!!";
    }

    public void publish(String channel, String message) {
        redisTemplate.convertAndSend(channel, message);
    }
}
