package com.itheima.publisher;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.lang.Nullable;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
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

    @Test
    public void testDirectExchange() {
        String exchangeName = "hmall.direct";
        String routingKey = "yellow";
        String msg = "hello direct yellow";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, msg);
    }

    @Test
    public void testTopicExchange() {
        String exchangeName = "hmall.topic";
        String routingKey = "china.#";
        String msg = "hello topic china.news";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, msg);
    }

    @Test
    public void testSendMap() throws InterruptedException {
        // 准备消息
        Map<String,Object> msg = new HashMap<>();
        msg.put("name", "鸡你太美");
        msg.put("age", 21);
        // 发送消息
        rabbitTemplate.convertAndSend("object.queue", msg);
    }

    @Test
    public void testReturnCallbackAndConfirm() throws InterruptedException {
        CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
        cd.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Spring amqp 发生了异常", ex);
            }

            @Override
            public void onSuccess(@Nullable CorrelationData.Confirm result) {
                if (result.isAck()) {
                    log.info("消息发送成功，收到ack");
                } else {
                    log.info("消息发送失败， 收到nack, reason: {}", result.getReason());
                }
            }
        });
        String exchangeName = "hmall.direct";
        String routingKey = "yellow";
        String msg = "hello direct yellow";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, msg, cd);
        Thread.sleep(100000);
    }
}