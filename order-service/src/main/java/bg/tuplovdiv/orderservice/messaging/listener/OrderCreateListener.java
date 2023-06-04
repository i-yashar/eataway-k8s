package bg.tuplovdiv.orderservice.messaging.listener;

import bg.tuplovdiv.orderservice.dto.CreateOrderRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import static bg.tuplovdiv.orderservice.config.RabbitMQConfig.ORDER_CREATE_QUEUE;

public class OrderCreateListener {

    @RabbitListener(queues = ORDER_CREATE_QUEUE)
    public void accept(CreateOrderRequest order) {
    }
}
