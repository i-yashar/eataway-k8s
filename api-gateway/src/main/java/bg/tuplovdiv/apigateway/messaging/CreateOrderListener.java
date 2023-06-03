package bg.tuplovdiv.apigateway.messaging;

import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.mapper.OrderMapper;
import bg.tuplovdiv.apigateway.order.OrderQueue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static bg.tuplovdiv.apigateway.config.messaging.RabbitMQConfig.ORDER_CREATED_QUEUE;

@Component
public class CreateOrderListener implements MessageListener<OrderContext, Void> {

    private final OrderQueue orderQueue;
    private final OrderMapper orderMapper;

    public CreateOrderListener(OrderQueue orderQueue, OrderMapper orderMapper) {
        this.orderQueue = orderQueue;
        this.orderMapper = orderMapper;
    }

    @Override
    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public Void accept(OrderContext context) {
        OrderDTO order = orderMapper.toDTO(context);
        orderQueue.registerOrder(order);
        return null;
    }
}
