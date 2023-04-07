package bg.tuplovdiv.orderservice.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static bg.tuplovdiv.orderservice.config.messaging.RabbitMQConfig.EXCHANGE;
import static bg.tuplovdiv.orderservice.config.messaging.RabbitMQConfig.ROUTING_KEY;

@Component
public class OrderDispatcher implements MessageDispatcher {

    private final RabbitTemplate template;

    public OrderDispatcher(RabbitTemplate template) {
        this.template = template;
    }

    @Override
    public void dispatch(Message message) {
        setMessageMetadata(message);
        template.convertAndSend(EXCHANGE, ROUTING_KEY, message);
    }

    private void setMessageMetadata(Message message) {
        message.setMessageId(UUID.randomUUID());
        message.setCreatedAt(System.currentTimeMillis());
    }
}
