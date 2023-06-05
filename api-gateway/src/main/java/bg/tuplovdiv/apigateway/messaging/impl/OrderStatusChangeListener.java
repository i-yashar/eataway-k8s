package bg.tuplovdiv.apigateway.messaging.impl;

import bg.tuplovdiv.apigateway.cache.OrderCache;
import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.dto.OrderStatusChangeDTO;
import bg.tuplovdiv.apigateway.dto.OrderStatusInfoDTO;
import bg.tuplovdiv.apigateway.order.OrderStatusEmitters;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.Instant;

import static bg.tuplovdiv.apigateway.config.RabbitMQConfig.ORDER_UPDATED_QUEUE;

@Component
class OrderStatusChangeListener {

    private final OrderCache orderCache;

    OrderStatusChangeListener(OrderCache orderCache) {
        this.orderCache = orderCache;
    }

    @RabbitListener(queues = ORDER_UPDATED_QUEUE)
    public void accept(OrderStatusChangeDTO statusChange) {
        String clientId = statusChange.getClientId();

        updateOrder(statusChange);

        SseEmitter emitter = OrderStatusEmitters.get(clientId);
        try {
            emitter.send(statusChange);
        } catch (Exception e) {
            emitter.complete();
            OrderStatusEmitters.remove(clientId);
        }
    }

    private void updateOrder(OrderStatusChangeDTO statusChange) {
        OrderDTO order = orderCache.getOrder(statusChange.getOrderId());
        order.setStatus(statusChange.getStatus());
        orderCache.updateOrder(order);

        OrderStatusInfoDTO orderStatusInfo = new OrderStatusInfoDTO()
                .setOrderId(statusChange.getOrderId())
                .setTime(String.valueOf(Instant.now().getEpochSecond()))
                .setInfoMessage(statusChange.getStatus());
        orderCache.updateOrderStatusInfoMessages(orderStatusInfo);
    }
}
