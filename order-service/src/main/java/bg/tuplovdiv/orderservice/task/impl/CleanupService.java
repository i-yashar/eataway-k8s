package bg.tuplovdiv.orderservice.task.impl;

import bg.tuplovdiv.orderservice.model.enums.OrderStatus;
import bg.tuplovdiv.orderservice.repository.ActiveOrderRepository;
import bg.tuplovdiv.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
class CleanupService {

    private final ActiveOrderRepository activeOrderRepository;
    private final OrderRepository orderRepository;

    CleanupService(ActiveOrderRepository activeOrderRepository, OrderRepository orderRepository) {
        this.activeOrderRepository = activeOrderRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    void cleanUpActiveOrders() {
        activeOrderRepository.findAllByStatus(OrderStatus.DELIVERED)
                .forEach(o -> {
                    orderRepository.save(o.toOrder());
                    activeOrderRepository.delete(o);
                });
    }
}
