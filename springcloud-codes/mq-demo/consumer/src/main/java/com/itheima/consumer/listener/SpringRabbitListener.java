package com.itheima.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Component
public class SpringRabbitListener {

    @RabbitListener(queues = {"simple.queue"})
    public void listenSimpleQueueMessage(String msg) {
        log.info("Spring 消费者接收到消息: {}", msg);
        throw new RuntimeException("我是故意的");
    }

    @RabbitListener(queues = {"work.queue"})
    public void listenWorkQueueMessage1(String msg) throws InterruptedException {
        log.info("Spring 消费者1接收到消息: {}", msg);
        Thread.sleep(20);
    }
    @RabbitListener(queues = {"work.queue"})
    public void listenWorkQueueMessage2(String msg) throws InterruptedException {
        log.info("Spring 消费者2接收到消息: {}", msg);
        Thread.sleep(200);
    }

    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String msg) {
        System.out.println("消费者1接收到Fanout消息：【" + msg + "】");
    }

    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String msg) {
        System.out.println("消费者2接收到Fanout消息：【" + msg + "】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("direct.queue1"),
            exchange = @Exchange(name = "hmall.direct", type = ExchangeTypes.DIRECT),
            key = {"red", "yellow"}
    ))
    public void listenDirectQueue1(String msg) {
        System.out.println("消费者1接收到direct.queue1的消息：【" + msg + "】");
//        throw new RuntimeException("我是故意的");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("direct.queue2"),
            exchange = @Exchange(name = "hmall.direct", type = ExchangeTypes.DIRECT),
            key = {"blue", "yellow"}
    ))
    public void listenDirectQueue2(String msg) {
        System.out.println("消费者2接收到direct.queue2的消息：【" + msg + "】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("topic.queue1"),
            exchange = @Exchange(name = "hmall.topic", type = ExchangeTypes.TOPIC),
            key = {"user.*"}
    ))
    public void listenTopicQueue1(String msg){
        System.out.println("消费者1接收到topic.queue1的消息：【" + msg + "】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("topic.queue2"),
            exchange = @Exchange(name = "hmall.topic", type = ExchangeTypes.TOPIC),
            key = {"user.#"}
    ))
    public void listenTopicQueue2(String msg){
        System.out.println("消费者2接收到topic.queue2的消息：【" + msg + "】");
    }

    @RabbitListener(queues = "object.queue")
    public void listenSimpleQueueMessage(Map<String, Object> msg) throws InterruptedException {
        System.out.println("消费者接收到object.queue消息：【" + msg + "】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dlx.queue"),
            exchange = @Exchange(name = "dlx.direct", type = ExchangeTypes.DIRECT, delayed = "true"),
            key = {"hi"}
    ))
    public void listenDlxQueue(String msg) {
        System.out.println("接收到死信交换机发送的消息" + msg + LocalDateTime.now());
    }
}
