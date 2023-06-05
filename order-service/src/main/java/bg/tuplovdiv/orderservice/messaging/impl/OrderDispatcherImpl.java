package bg.tuplovdiv.orderservice.messaging.impl;

import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.dto.OrderStatusChangeDTO;
import bg.tuplovdiv.orderservice.messaging.OrderDispatcher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static bg.tuplovdiv.orderservice.config.RabbitMQConfig.*;

@Component
class OrderDispatcherImpl implements OrderDispatcher {

    private final RabbitTemplate template;

    OrderDispatcherImpl(RabbitTemplate template) {
        this.template = template;
    }

    @Override
    public void sendCreatedOrder(OrderDTO order) {
        template.convertAndSend(EXCHANGE, ORDER_CREATED_ROUTING_KEY, order);
    }

    @Override
    public void sendChangedOrderStatus(OrderStatusChangeDTO orderStatusChange) {
        template.convertAndSend(EXCHANGE, ORDER_UPDATED_ROUTING_KEY, orderStatusChange);
    }
}
