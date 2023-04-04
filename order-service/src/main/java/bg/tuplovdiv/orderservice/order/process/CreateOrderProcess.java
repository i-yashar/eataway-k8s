package bg.tuplovdiv.orderservice.order.process;

import bg.tuplovdiv.orderservice.order.OrderContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateOrderProcess {

    public UUID start(OrderContext context) {
        //todo: add order dispatch logic
        return context.getOrderId();
    }
}
