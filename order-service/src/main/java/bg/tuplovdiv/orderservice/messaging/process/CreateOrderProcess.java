package bg.tuplovdiv.orderservice.messaging.process;

import bg.tuplovdiv.orderservice.messaging.MessageDispatcher;
import bg.tuplovdiv.orderservice.messaging.OrderContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderProcess {

    private final MessageDispatcher dispatcher;

    public CreateOrderProcess(@Qualifier("createOrderDispatcher") MessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void start(OrderContext order) {
        dispatcher.dispatch(order);
    }
}
