package bg.tuplovdiv.apigateway.messaging;

import org.springframework.stereotype.Component;

@Component
public class OrderStatusChangeEventTrigger {

    private final OrderStatusChangeDispatcher dispatcher;

    public OrderStatusChangeEventTrigger(OrderStatusChangeDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void trigger(OrderStatusChangeEvent order) {
        dispatcher.dispatch(order);
    }
}
