package bg.tuplovdiv.apigateway.order;

import bg.tuplovdiv.apigateway.messaging.OrderContext;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class OrderQueue {
    private static final ConcurrentLinkedDeque<OrderContext> orders;

    static {
        orders = new ConcurrentLinkedDeque<>();
    }

    public void registerOrder(OrderContext order) {
        orders.add(order);
    }

    public OrderContext takeOrder(UUID orderId) {
        Optional<OrderContext> optOrder = orders.stream()
                .filter(order -> order.getOrderId().equals(orderId))
                .findFirst();

        if(optOrder.isEmpty()) {
            throw new IllegalStateException();
        }

        return optOrder.get();
    }
}
