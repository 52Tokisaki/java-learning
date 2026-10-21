package com.itheima.publisher.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@AllArgsConstructor
@Configuration
public class MqConfig {
    private final RabbitTemplate rabbitTemplate;
    
    @PostConstruct
    public void init() {
        rabbitTemplate.setReturnsCallback(returned -> {
            log.error("接收到MQ的return Callback");
            log.info("exchange: {}", returned.getExchange());
            log.info("message: {}", returned.getMessage());
            log.info("routingKey: {}", returned.getRoutingKey());
            log.info("replyCode: {}", returned.getReplyCode());
            log.info("replyText: {}", returned.getReplyText());
        });
    }
}