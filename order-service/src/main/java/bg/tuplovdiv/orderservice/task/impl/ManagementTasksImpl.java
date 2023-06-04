package bg.tuplovdiv.orderservice.task.impl;

import bg.tuplovdiv.orderservice.mapper.OrderMapper;
import bg.tuplovdiv.orderservice.messaging.process.CreateOrderProcess;
import bg.tuplovdiv.orderservice.model.enums.OrderStatus;
import bg.tuplovdiv.orderservice.repository.ActiveOrderRepository;
import bg.tuplovdiv.orderservice.task.ManagementTasks;
import org.springframework.stereotype.Component;

@Component
class ManagementTasksImpl implements ManagementTasks {

    private final ActiveOrderRepository activeOrderRepository;
    private final CreateOrderProcess createOrderProcess;
    private final OrderMapper orderMapper;

    ManagementTasksImpl(ActiveOrderRepository activeOrderRepository, CreateOrderProcess createOrderProcess, OrderMapper orderMapper) {
        this.activeOrderRepository = activeOrderRepository;
        this.createOrderProcess = createOrderProcess;
        this.orderMapper = orderMapper;
    }

    public void dispatchRegisteredOrders() {
        activeOrderRepository.findAllByStatus(OrderStatus.REGISTERED)
                .forEach(o -> createOrderProcess.start(orderMapper.toDTO(o.toOrder())));
    }
}
