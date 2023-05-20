package bg.tuplovdiv.orderservice.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static bg.tuplovdiv.orderservice.config.messaging.RabbitMQConfig.*;

@Component("createOrderDispatcher")
public class CreateOrderDispatcher implements MessageDispatcher {

    private final RabbitTemplate template;

    public CreateOrderDispatcher(RabbitTemplate template) {
        this.template = template;
    }

    @Override
    public void dispatch(Message message) {
        setMessageMetadata(message);
        template.convertAndSend(EXCHANGE, ORDER_CREATED_ROUTING_KEY, message);
    }

    private void setMessageMetadata(Message message) {
        message.setMessageId(UUID.randomUUID());
        message.setCreatedAt(System.currentTimeMillis());
    }
}
