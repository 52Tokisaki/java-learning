package com.itheima.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringRabbitListener {

    @RabbitListener(queues = {"simple.queue"})
    public void listenSimpleQueueMessage(String msg) {
        log.info("Spring 消费者接收到消息: {}", msg);
    }

    @RabbitListener(queues = {"work.queue"})
    public void listenWorkQueueMessage1(String msg) {
        log.info("Spring 消费者1接收到消息: {}", msg);
    }
    @RabbitListener(queues = {"work.queue"})
    public void listenWorkQueueMessage2(String msg) {
        log.info("Spring 消费者2接收到消息: {}", msg);
    }
}
