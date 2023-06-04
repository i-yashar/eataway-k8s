package bg.tuplovdiv.orderservice.task.impl;

import bg.tuplovdiv.orderservice.task.ManagementTasks;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class StartupTasks {

    private final ManagementTasks managementTasks;

    StartupTasks(ManagementTasks managementTasks) {
        this.managementTasks = managementTasks;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void dispatchRegisteredOrders() {
        managementTasks.dispatchRegisteredOrders();
    }
}
