package bg.tuplovdiv.apigateway.order;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static bg.tuplovdiv.apigateway.config.messaging.RabbitMQConfig.QUEUE;

@Component
public class CreateOrderListener implements MessageListener<OrderContext> {

    @Override
    @RabbitListener(queues = QUEUE)
    public OrderContext accept(OrderContext message) {
        return null;
    }
}
