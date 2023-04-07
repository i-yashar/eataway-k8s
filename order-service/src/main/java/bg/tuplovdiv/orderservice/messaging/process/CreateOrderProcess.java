package bg.tuplovdiv.orderservice.messaging.process;

import bg.tuplovdiv.orderservice.messaging.MessageDispatcher;
import bg.tuplovdiv.orderservice.messaging.OrderContext;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderProcess {

    private final MessageDispatcher dispatcher;

    public CreateOrderProcess(MessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void start(OrderContext order) {
        dispatcher.dispatch(order);
    }
}
