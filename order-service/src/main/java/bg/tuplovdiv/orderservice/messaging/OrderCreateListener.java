package bg.tuplovdiv.orderservice.messaging;

import bg.tuplovdiv.orderservice.dto.CreateOrderRequestDTO;
import bg.tuplovdiv.orderservice.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static bg.tuplovdiv.orderservice.config.RabbitMQConfig.ORDER_CREATE_QUEUE;

@Component
public class OrderCreateListener {

    private final OrderService orderService;

    public OrderCreateListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = ORDER_CREATE_QUEUE)
    public void accept(CreateOrderRequestDTO orderRequest) {
        orderService.createOrder(orderRequest);
    }
}
