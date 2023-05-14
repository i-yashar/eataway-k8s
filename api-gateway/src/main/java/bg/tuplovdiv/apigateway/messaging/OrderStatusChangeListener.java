package bg.tuplovdiv.apigateway.messaging;

import bg.tuplovdiv.apigateway.order.OrderStatusEmitters;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static bg.tuplovdiv.apigateway.config.messaging.RabbitMQConfig.ORDER_UPDATED_QUEUE;

@Component
public class OrderStatusChangeListener implements MessageListener<OrderStatusChangeEvent, Void> {

    @Override
    @RabbitListener(queues = ORDER_UPDATED_QUEUE)
    public Void accept(OrderStatusChangeEvent message) {
        String clientId = message.getClientId();

        SseEmitter emitter = OrderStatusEmitters.get(clientId);
        try {
            emitter.send(message);
        } catch (Exception e) {
            emitter.complete();
            OrderStatusEmitters.remove(clientId);
        }

        return null;
    }
}
