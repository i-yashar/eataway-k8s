package bg.tuplovdiv.apigateway.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static bg.tuplovdiv.apigateway.config.messaging.RabbitMQConfig.EXCHANGE;
import static bg.tuplovdiv.apigateway.config.messaging.RabbitMQConfig.ORDER_UPDATED_ROUTING_KEY;

@Component
public class OrderStatusChangeDispatcher implements MessageDispatcher {

    private final RabbitTemplate template;

    public OrderStatusChangeDispatcher(RabbitTemplate template) {
        this.template = template;
    }

    @Override
    public void dispatch(Message message) {
        setMessageMetadata(message);
        template.convertAndSend(EXCHANGE, ORDER_UPDATED_ROUTING_KEY, message);
    }

    private void setMessageMetadata(Message message) {
        message.setMessageId(UUID.randomUUID());
        message.setCreatedAt(System.currentTimeMillis());
    }
}
