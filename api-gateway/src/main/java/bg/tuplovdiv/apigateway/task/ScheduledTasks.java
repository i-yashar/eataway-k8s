package bg.tuplovdiv.apigateway.task;

import bg.tuplovdiv.apigateway.connectivity.client.OrderManagementClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
class ScheduledTasks {

    private static final long TRIGGER_RATE = 60;
    private static final long INITIAL_DELAY = 1;

    private final OrderManagementClient orderManagementClient;

    ScheduledTasks(OrderManagementClient orderManagementClient) {
        this.orderManagementClient = orderManagementClient;
    }

    @Scheduled(fixedRate = TRIGGER_RATE, initialDelay = INITIAL_DELAY, timeUnit = TimeUnit.MINUTES)
    public void triggerOrderDispatch() {
        orderManagementClient.triggerRegisteredOrderDispatch();
    }
}
