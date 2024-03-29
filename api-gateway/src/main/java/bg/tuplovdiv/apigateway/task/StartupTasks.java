package bg.tuplovdiv.apigateway.task;

import bg.tuplovdiv.apigateway.connectivity.client.OrderManagementClient;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class StartupTasks {

    private final OrderManagementClient orderManagementClient;

    StartupTasks(OrderManagementClient orderManagementClient) {
        this.orderManagementClient = orderManagementClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void dispatchRegisteredOrders() {
        orderManagementClient.triggerRegisteredOrderDispatch();
    }
}
