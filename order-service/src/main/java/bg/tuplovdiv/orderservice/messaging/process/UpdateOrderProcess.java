package bg.tuplovdiv.orderservice.messaging.process;

import bg.tuplovdiv.orderservice.dto.OrderStatusChangeDTO;
import bg.tuplovdiv.orderservice.messaging.UpdateOrderDispatcher;
import org.springframework.stereotype.Component;

@Component
public class UpdateOrderProcess {

    public final UpdateOrderDispatcher updateOrderDispatcher;

    public UpdateOrderProcess(UpdateOrderDispatcher updateOrderDispatcher) {
        this.updateOrderDispatcher = updateOrderDispatcher;
    }

    public void start(OrderStatusChangeDTO orderStatusChange) {
        updateOrderDispatcher.dispatch(orderStatusChange);
    }
}
