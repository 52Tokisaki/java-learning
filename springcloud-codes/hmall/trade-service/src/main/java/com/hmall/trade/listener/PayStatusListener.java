package com.hmall.trade.listener;

import com.hmall.common.enums.PayStatus;
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
public class PayStatusListener {
    private final IOrderService orderService;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "trade.pay.success.queue"),
            exchange = @Exchange(name = "pay.direct"),
            key = "pay.success"
    ))
    public void listenPaySuccess(Long orderId) {
        log.info("Received pay success message: {}", orderId);
        Order order = orderService.getById(orderId);
        if (order == null || !order.getStatus().equals(PayStatus.WAIT_BUYER_PAY.getValue())) {
            return; // 通过业务逻辑判断当前消息是否已处理过
        }
        orderService.markOrderPaySuccess(orderId);
    }

}
