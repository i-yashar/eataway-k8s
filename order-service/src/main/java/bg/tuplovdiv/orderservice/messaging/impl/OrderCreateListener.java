package bg.tuplovdiv.orderservice.messaging.impl;

import bg.tuplovdiv.orderservice.dto.CreateOrderRequestDTO;
import bg.tuplovdiv.orderservice.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static bg.tuplovdiv.orderservice.config.RabbitMQConfig.ORDER_CREATE_QUEUE;

@Component
class OrderCreateListener {

    private final OrderService orderService;

    OrderCreateListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = ORDER_CREATE_QUEUE)
    public void accept(CreateOrderRequestDTO orderRequest) {
        orderService.createOrder(orderRequest);
    }
}
