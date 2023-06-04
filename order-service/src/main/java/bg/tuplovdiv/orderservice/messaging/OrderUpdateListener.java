package bg.tuplovdiv.orderservice.messaging;

import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static bg.tuplovdiv.orderservice.config.RabbitMQConfig.ORDER_UPDATE_QUEUE;

@Component
public class OrderUpdateListener {

    private final OrderService orderService;

    public OrderUpdateListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = ORDER_UPDATE_QUEUE)
    public void accept(OrderDTO order) {
        orderService.updateOrder(order);
    }
}
