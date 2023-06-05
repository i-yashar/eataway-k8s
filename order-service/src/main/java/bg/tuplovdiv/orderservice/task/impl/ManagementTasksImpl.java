package bg.tuplovdiv.orderservice.task.impl;

import bg.tuplovdiv.orderservice.mapper.OrderMapper;
import bg.tuplovdiv.orderservice.messaging.OrderDispatcher;
import bg.tuplovdiv.orderservice.model.enums.OrderStatus;
import bg.tuplovdiv.orderservice.repository.ActiveOrderRepository;
import bg.tuplovdiv.orderservice.task.ManagementTasks;
import org.springframework.stereotype.Component;

@Component
class ManagementTasksImpl implements ManagementTasks {

    private final ActiveOrderRepository activeOrderRepository;
    private final OrderDispatcher orderDispatcher;
    private final OrderMapper orderMapper;

    ManagementTasksImpl(ActiveOrderRepository activeOrderRepository, OrderDispatcher orderDispatcher, OrderMapper orderMapper) {
        this.activeOrderRepository = activeOrderRepository;
        this.orderDispatcher = orderDispatcher;
        this.orderMapper = orderMapper;
    }

    public void dispatchRegisteredOrders() {
        activeOrderRepository.findAllByStatus(OrderStatus.REGISTERED)
                .forEach(o -> orderDispatcher.sendCreatedOrder(orderMapper.toDTO(o.toOrder())));
    }
}
