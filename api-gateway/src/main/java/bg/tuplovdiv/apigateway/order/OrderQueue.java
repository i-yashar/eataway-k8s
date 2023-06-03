package bg.tuplovdiv.apigateway.order;

import bg.tuplovdiv.apigateway.dto.OrderDTO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class OrderQueue {

    private static final ConcurrentLinkedDeque<OrderDTO> orders;

    static {
        orders = new ConcurrentLinkedDeque<>();
    }

    public Collection<OrderDTO> getRegisteredOrders() {
        return orders.stream().toList();
    }

    public void registerOrder(OrderDTO order) {
        orders.add(order);
    }


    public OrderDTO takeOrder(UUID orderId) {
        Optional<OrderDTO> optOrder = orders.stream()
                .filter(order -> order.getOrderId().equals(orderId))
                .findFirst();

        if (optOrder.isEmpty()) {
            throw new IllegalStateException();
        }

        orders.removeIf(order -> order.getOrderId().equals(orderId));

        return optOrder.get();
    }
}
