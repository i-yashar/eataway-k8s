package bg.tuplovdiv.apigateway.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static bg.tuplovdiv.apigateway.config.messaging.RabbitMQConfig.QUEUE;

@Component
public class CreateOrderListener implements MessageListener<OrderContext, Void> {

    @Override
    @RabbitListener(queues = QUEUE)
    public Void accept(OrderContext message) {
        System.out.println(message);
        System.out.println(message.getMessageId());
        System.out.println(message.getCreatedAt());
        return null;
    }
}
