package bg.tuplovdiv.apigateway.order;

import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.messaging.OrderContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;

@Component
public class OrderQueue {

    private static final ConcurrentLinkedDeque<OrderContext> orders;
    private static final ConcurrentMap<UUID, OrderContext> activeOrders;
    private static final ConcurrentMap<UUID, String> deliveryDrivers;

    static {
        orders = new ConcurrentLinkedDeque<>();
        activeOrders = new ConcurrentHashMap<>();
        deliveryDrivers = new ConcurrentHashMap<>();
    }

    public Collection<OrderDTO> getRegisteredOrders() {
        return orders.stream()
                .map(this::mapToOrderDTO)
                .toList();
    }

    public void registerOrder(OrderContext order) {
        orders.add(order);
        activeOrders.put(order.getOrderId(), order);
    }

    public Collection<OrderDTO> getActiveOrders() {
        return activeOrders.values().stream()
                .map(this::mapToOrderDTO)
                .toList();
    }

    public Collection<String> getDeliveryDrivers() {
        return new ArrayList<>(deliveryDrivers.values());
    }

    public OrderDTO takeOrder(UUID orderId, String deliveryDriverId) {
        Optional<OrderContext> optOrder = orders.stream()
                .filter(order -> order.getOrderId().equals(orderId))
                .findFirst();

        if (optOrder.isEmpty()) {
            throw new IllegalStateException();
        }

        OrderContext order = optOrder.get();
        activeOrders.put(orderId, order);
        deliveryDrivers.put(orderId, deliveryDriverId);

        orders.removeIf(orderContext -> orderContext.getOrderId().equals(orderId));

        return mapToOrderDTO(order);
    }

    private OrderDTO mapToOrderDTO(OrderContext order) {
        return new OrderDTO()
                .setOrderId(order.getOrderId())
                .setClientId(order.getClientId())
                .setClientPhoneNumber(order.getClientPhoneNumber())
                .setAddress(order.getAddress())
                .setTotalCost(order.getTotalCost());
    }

    public OrderContext getOrderContext(UUID orderId) {
        return activeOrders.get(orderId);
    }

    public void removeOrderContext(UUID orderId) {
        activeOrders.remove(orderId);
    }
}
