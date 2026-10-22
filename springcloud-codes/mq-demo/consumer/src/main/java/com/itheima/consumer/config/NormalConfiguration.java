package com.itheima.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NormalConfiguration { 
    @Bean
    public DirectExchange normalExchange() {
        return ExchangeBuilder.directExchange("normal.direct").build();
    }
    
    // 死信将传递给dlx.direct交换机
    @Bean
    public Queue normalQueue() {
        return QueueBuilder.durable("normal.queue").deadLetterExchange("dlx.direct").build();
    }
    
    @Bean
    public Binding bindingWithNormalExchange(Queue normalQueue, DirectExchange normalExchange) {
        return BindingBuilder.bind(normalQueue).to(normalExchange).with("hi");
    }
}