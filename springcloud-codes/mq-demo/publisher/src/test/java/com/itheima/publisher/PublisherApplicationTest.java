package com.itheima.publisher;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PublisherApplicationTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSimpleQueue() {
        String queueName = "simple.queue";
        String msg = "hello world";
        rabbitTemplate.convertAndSend(queueName, msg);
    }

    @Test
    public void testWorkQueues() {
        String queueName = "work.queue";
        String msg = "hello msg_";
        for (int i = 0; i < 50; i++) {
            rabbitTemplate.convertAndSend(queueName, msg + i);
        }
    }

    @Test
    public void testFanoutExchange() {
        String exchangeName = "hmall.fanout";
        String msg = "hello fanout";
        rabbitTemplate.convertAndSend(exchangeName, "", msg);
    }
}