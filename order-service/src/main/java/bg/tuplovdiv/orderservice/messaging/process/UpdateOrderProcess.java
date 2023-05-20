package bg.tuplovdiv.orderservice.messaging.process;

import bg.tuplovdiv.orderservice.messaging.MessageDispatcher;
import bg.tuplovdiv.orderservice.messaging.delivery.OrderStatusChange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class UpdateOrderProcess {

    private final MessageDispatcher dispatcher;

    public UpdateOrderProcess(@Qualifier("updateOrderDispatcher") MessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void start(OrderStatusChange orderStatusChange) {
        dispatcher.dispatch(orderStatusChange);
    }
}
