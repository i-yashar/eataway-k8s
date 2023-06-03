package bg.tuplovdiv.orderservice.task.impl;

import bg.tuplovdiv.orderservice.messaging.process.CreateOrderProcess;
import bg.tuplovdiv.orderservice.model.enums.OrderStatus;
import bg.tuplovdiv.orderservice.repository.ActiveOrderRepository;
import bg.tuplovdiv.orderservice.task.ManagementTasks;
import org.springframework.stereotype.Component;

@Component
class ManagementTasksImpl implements ManagementTasks {

    private final ActiveOrderRepository activeOrderRepository;
    private final CreateOrderProcess createOrderProcess;

    ManagementTasksImpl(ActiveOrderRepository activeOrderRepository, CreateOrderProcess createOrderProcess) {
        this.activeOrderRepository = activeOrderRepository;
        this.createOrderProcess = createOrderProcess;
    }

    public void dispatchRegisteredOrders() {
        activeOrderRepository.findAllByStatus(OrderStatus.REGISTERED)
                .forEach(o -> createOrderProcess.start(o.toOrder()));
    }
}
