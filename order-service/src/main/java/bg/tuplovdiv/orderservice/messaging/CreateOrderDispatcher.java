package bg.tuplovdiv.orderservice.messaging;

import bg.tuplovdiv.orderservice.dto.OrderDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static bg.tuplovdiv.orderservice.config.RabbitMQConfig.EXCHANGE;
import static bg.tuplovdiv.orderservice.config.RabbitMQConfig.ORDER_CREATED_ROUTING_KEY;

@Component
public class CreateOrderDispatcher {

    private final RabbitTemplate template;

    public CreateOrderDispatcher(RabbitTemplate template) {
        this.template = template;
    }

    public void dispatch(OrderDTO order) {
        template.convertAndSend(EXCHANGE, ORDER_CREATED_ROUTING_KEY, order);
    }
}
