package bg.tuplovdiv.orderservice.service.security.authorization;

import bg.tuplovdiv.orderservice.exception.OrderNotFoundException;
import bg.tuplovdiv.orderservice.model.entity.OrderEntity;
import bg.tuplovdiv.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderAuthorizationService extends BaseAuthorizationService {

    private final OrderRepository orderRepository;

    public OrderAuthorizationService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    protected String getResourceOwnerId(UUID orderId) {
        OrderEntity order = orderRepository.findOrderEntityByExternalId(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with orderId " + orderId + " not found"));

        return order.getClientId();
    }
}
