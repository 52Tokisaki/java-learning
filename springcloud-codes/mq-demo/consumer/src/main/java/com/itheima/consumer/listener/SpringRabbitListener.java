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

    @RabbitListener(queues = "direct.queue1")
    public void listenDirectQueue1(String msg) {
        System.out.println("消费者1接收到direct.queue1的消息：【" + msg + "】");
    }

    @RabbitListener(queues = "direct.queue2")
    public void listenDirectQueue2(String msg) {
        System.out.println("消费者2接收到direct.queue2的消息：【" + msg + "】");
    }
}
