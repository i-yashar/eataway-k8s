package bg.tuplovdiv.orderservice.validation.order;

import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.exception.OrderNotFoundException;
import bg.tuplovdiv.orderservice.model.entity.OrderEntity;
import bg.tuplovdiv.orderservice.repository.OrderRepository;
import bg.tuplovdiv.orderservice.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static bg.tuplovdiv.orderservice.model.OrderStatus.*;

@Component
public class DeliveryValidator {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public DeliveryValidator(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public boolean isValid(OrderDTO request) {
        OrderEntity order = getOrder(request.getOrderId());

        switch (order.getStatus()) {
            case REGISTERED -> {
                if(!isDriverFree(request.getDeliveryDriverId())) {
                    return false;
                }
            }
            case ACTIVE, ABOUT_TO_BE_DELIVERED -> {
                if(!isCorrectDriver(order, request.getDeliveryDriverId())) {
                    return false;
                }
            }
            case DELIVERED -> {
                return false;
            }
        }

        return true;
    }

    private boolean isDriverFree(String driverId) {
        return orderRepository.findOrderEntityByDeliveryDriverIdAndStatus(driverId, ACTIVE)
                .isEmpty()
                && orderRepository.findOrderEntityByDeliveryDriverIdAndStatus(driverId, ABOUT_TO_BE_DELIVERED)
                .isEmpty();
    }

    private boolean isCorrectDriver(OrderEntity order, String driverId) {
        return order.getDeliveryDriverId().equals(driverId);
    }

    private OrderEntity getOrder(UUID orderId) {
        return orderRepository.findOrderEntityByExternalId(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with orderId " + orderId + " not found"));
    }
}
