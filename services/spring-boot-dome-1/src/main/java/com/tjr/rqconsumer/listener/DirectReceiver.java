package com.tjr.rqconsumer.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
public class DirectReceiver {

    private static final Logger logger=  LoggerFactory.getLogger(DirectReceiver.class);

    @RabbitHandler
    public void process(Map testMessage) {
        logger.info("DirectReceiver消费者收到消息  : {}",testMessage);
    }

}
