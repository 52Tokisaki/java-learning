package com.hmall.trade.listener;

import com.hmall.api.client.PayClient;
import com.hmall.api.dto.PayOrderDTO;
import com.hmall.trade.constants.MQConstants;
import com.hmall.trade.domain.po.Order;
import com.hmall.trade.service.IOrderService;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderDelayMessageListener { 
    private final IOrderService orderService;
    private final PayClient payClient;
    
    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(MQConstants.DELAY_ORDER_QUEUE_NAME),
        exchange = @Exchange(name = MQConstants.DELAY_EXCHANGE_NAME, delayed = "true"),
        key = {MQConstants.DELAY_ORDER_KEY}
    ))
    public void listenOrderDelayMessage(Long orderId) {
        log.info("Received delay message, orderId: {}", orderId);
        // 监听延迟消息, 判断订单支付状态
        // 1. 获取订单信息
        Order order = orderService.getById(orderId);
        // 2. 查询支付流水单号
        PayOrderDTO payOrder = payClient.queryPayOrderByBizOrderNo(orderId);

        // 3. 获取支付状态
        if (payOrder != null && payOrder.getStatus() == 3) {
            // 4.1 支付成功，标记订单已支付
            orderService.markOrderPaySuccess(orderId);
        } else {
            // 4.2 未完成支付，取消订单，并恢复库存
            orderService.cancelOrder(orderId);        
        }
        
    }
}