package bg.tuplovdiv.orderservice.messaging;

import bg.tuplovdiv.orderservice.dto.OrderStatusChangeDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static bg.tuplovdiv.orderservice.config.RabbitMQConfig.EXCHANGE;
import static bg.tuplovdiv.orderservice.config.RabbitMQConfig.ORDER_UPDATED_ROUTING_KEY;

@Component
public class UpdateOrderDispatcher {

    private final RabbitTemplate template;

    public UpdateOrderDispatcher(RabbitTemplate template) {
        this.template = template;
    }

    public void dispatch(OrderStatusChangeDTO orderStatusChange) {
        template.convertAndSend(EXCHANGE, ORDER_UPDATED_ROUTING_KEY, orderStatusChange);
    }
}
