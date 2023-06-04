package bg.tuplovdiv.apigateway.messaging.impl;

import bg.tuplovdiv.apigateway.dto.OrderStatusChangeDTO;
import bg.tuplovdiv.apigateway.order.OrderStatusEmitters;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static bg.tuplovdiv.apigateway.config.RabbitMQConfig.ORDER_UPDATED_QUEUE;

@Component
class OrderStatusChangeListener {

    @RabbitListener(queues = ORDER_UPDATED_QUEUE)
    Void accept(OrderStatusChangeDTO statusChange) {
        String clientId = statusChange.getClientId();

        SseEmitter emitter = OrderStatusEmitters.get(clientId);
        try {
            emitter.send(statusChange);
        } catch (Exception e) {
            emitter.complete();
            OrderStatusEmitters.remove(clientId);
        }

        return null;
    }
}
