package bg.tuplovdiv.apigateway.messaging;

import bg.tuplovdiv.apigateway.order.OrderQueue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static bg.tuplovdiv.apigateway.config.messaging.RabbitMQConfig.ORDER_CREATED_QUEUE;

@Component
public class CreateOrderListener implements MessageListener<OrderContext, Void> {

    private final OrderQueue orderQueue;

    public CreateOrderListener(OrderQueue orderQueue) {
        this.orderQueue = orderQueue;
    }

    @Override
    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public Void accept(OrderContext order) {
        orderQueue.registerOrder(order);
        return null;
    }
}
