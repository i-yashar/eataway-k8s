package bg.tuplovdiv.apigateway.order;

import bg.tuplovdiv.apigateway.dto.OrderDTO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OrderQueue {

    private static final ConcurrentHashMap<UUID, OrderDTO> orders;

    static {
        orders = new ConcurrentHashMap<>();
    }

    public Collection<OrderDTO> getRegisteredOrders() {
        return orders.values().stream().toList();
    }

    public void registerOrder(OrderDTO order) {
        orders.put(order.getOrderId(), order);
    }


    public OrderDTO takeOrder(UUID orderId) {
        OrderDTO order = orders.get(orderId);

        if (order == null) {
            throw new IllegalStateException();
        }

        orders.remove(orderId);

        return order;
    }
}
