package bg.tuplovdiv.apigateway.messaging.impl;

import bg.tuplovdiv.apigateway.cache.OrderCache;
import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.order.OrderQueue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static bg.tuplovdiv.apigateway.config.RabbitMQConfig.ORDER_CREATED_QUEUE;

@Component
class OrderCreateListener {

    private final OrderCache orderCache;
    private final OrderQueue orderQueue;

    OrderCreateListener(OrderCache orderCache, OrderQueue orderQueue) {
        this.orderCache = orderCache;
        this.orderQueue = orderQueue;
    }

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void accept(OrderDTO order) {
        orderQueue.registerOrder(order);
        orderCache.updateOrder(order);
    }
}
