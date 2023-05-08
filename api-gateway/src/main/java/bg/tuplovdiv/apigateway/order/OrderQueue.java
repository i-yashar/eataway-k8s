package bg.tuplovdiv.apigateway.order;

import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.messaging.OrderContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class OrderQueue {

    private static final ConcurrentLinkedDeque<OrderContext> orders;

    static {
        orders = new ConcurrentLinkedDeque<>();
    }

    public Collection<OrderDTO> getActiveOrders() {
        return orders.stream()
                .map(this::mapToOrderDTO)
                .toList();
    }

    public void registerOrder(OrderContext order) {
        orders.add(order);
    }

    public OrderDTO takeOrder(UUID orderId) {
        Optional<OrderContext> optOrder = orders.stream()
                .filter(order -> order.getOrderId().equals(orderId))
                .findFirst();

        if(optOrder.isEmpty()) {
            throw new IllegalStateException();
        }

        orders.removeIf(order -> order.getOrderId().equals(orderId));

        return mapToOrderDTO(optOrder.get());
    }

    private OrderDTO mapToOrderDTO(OrderContext order) {
        return new OrderDTO()
                .setOrderId(order.getOrderId())
                .setClientId(order.getClientId())
                .setClientPhoneNumber(order.getClientPhoneNumber())
                .setAddress(order.getAddress())
                .setTotalCost(order.getTotalCost());
    }
}
