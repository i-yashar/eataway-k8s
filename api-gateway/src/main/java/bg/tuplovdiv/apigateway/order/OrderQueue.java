package bg.tuplovdiv.apigateway.order;

import bg.tuplovdiv.apigateway.dto.OrderDTO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OrderQueue {

    private static final ConcurrentHashMap<UUID, OrderDTO> ORDERS;

    static {
        ORDERS = new ConcurrentHashMap<>();
    }

    public Collection<OrderDTO> getRegisteredOrders() {
        return ORDERS.values().stream().toList();
    }

    public void registerOrder(OrderDTO order) {
        ORDERS.putIfAbsent(order.getOrderId(), order);
    }


    public OrderDTO takeOrder(UUID orderId) {
        OrderDTO order = ORDERS.get(orderId);

        if (order == null) {
            throw new IllegalStateException();
        }

        ORDERS.remove(orderId);

        return order;
    }
}
