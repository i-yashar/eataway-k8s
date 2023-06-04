package bg.tuplovdiv.apigateway.messaging.impl;

import bg.tuplovdiv.apigateway.dto.CreateOrderRequestDTO;
import bg.tuplovdiv.apigateway.dto.OrderStatusChangeDTO;
import bg.tuplovdiv.apigateway.messaging.OrderDispatcher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static bg.tuplovdiv.apigateway.config.RabbitMQConfig.ORDER_CREATE_ROUTING_KEY;
import static bg.tuplovdiv.apigateway.config.RabbitMQConfig.ORDER_UPDATE_ROUTING_KEY;
import static bg.tuplovdiv.orderservice.config.messaging.RabbitMQConfig.EXCHANGE;

@Component
class OrderDispatcherImpl implements OrderDispatcher {

    private final RabbitTemplate template;

    OrderDispatcherImpl(RabbitTemplate template) {
        this.template = template;
    }

    @Override
    public void sendOrderCreateRequest(CreateOrderRequestDTO request) {
        template.convertAndSend(EXCHANGE, ORDER_CREATE_ROUTING_KEY, request);
    }

    @Override
    public void sendOrderStatusUpdate(OrderStatusChangeDTO statusChange) {
        template.convertAndSend(EXCHANGE, ORDER_UPDATE_ROUTING_KEY, statusChange);
    }
}
