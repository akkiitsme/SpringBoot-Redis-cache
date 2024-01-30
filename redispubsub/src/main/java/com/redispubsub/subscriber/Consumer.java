package com.redispubsub.subscriber;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.util.Arrays;

@Slf4j
public class Consumer implements MessageListener {
    @Override
    public void onMessage(Message message , byte[] pattern) {
        System.out.println(Arrays.toString(message.getBody()));
       // System.out.println(Arrays.toString(message.getChannel()));
        log.info("Consuming Message: "+message);
    }
}
