package bg.tuplovdiv.orderservice.messaging.process;

import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.messaging.CreateOrderDispatcher;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderProcess {

    private final CreateOrderDispatcher dispatcher;

    public CreateOrderProcess(CreateOrderDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void start(OrderDTO order) {
        dispatcher.dispatch(order);
    }
}
