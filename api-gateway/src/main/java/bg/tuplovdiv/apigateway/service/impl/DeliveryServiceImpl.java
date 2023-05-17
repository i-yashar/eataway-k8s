package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.connectivity.client.OrdersRestClient;
import bg.tuplovdiv.apigateway.dto.BasketDTO;
import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.messaging.OrderStatusChangeEvent;
import bg.tuplovdiv.apigateway.messaging.OrderStatusChangeEventTrigger;
import bg.tuplovdiv.apigateway.order.OrderQueue;
import bg.tuplovdiv.apigateway.service.DeliveryService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final OrderQueue orderQueue;
    private final OrderStatusChangeEventTrigger statusChangeEventTrigger;
    private final OrdersRestClient client;

    public DeliveryServiceImpl(OrderQueue orderQueue, OrderStatusChangeEventTrigger statusChangeEventTrigger, OrdersRestClient client) {
        this.orderQueue = orderQueue;
        this.statusChangeEventTrigger = statusChangeEventTrigger;
        this.client = client;
    }

    @Override
    public Collection<OrderDTO> getRegisteredOrders() {
        return orderQueue.getActiveOrders();
    }

    @Override
    public OrderDTO getOrderInfo(UUID orderId) {
        return client.getUserOrder(orderId);
    }

    @Override
    public BasketDTO getOrderBasketInfo(UUID orderId) {
        return orderQueue.getOrderContext(orderId).getBasket();
    }

    @Override
    public OrderDTO takeOrder(UUID orderId, String deliveryDriverId) {
        OrderDTO order = orderQueue.takeOrder(orderId);
        order.setDeliveryDriverId(deliveryDriverId);
        order.setStatus("ACTIVE");

        return performOrderUpdate(order, statusChangeEventTrigger::trigger);
    }

    @Override
    public OrderDTO updateOrder(UUID orderId, String status) {
        OrderDTO order = client.getUserOrder(orderId);
        order.setStatus(status);

        return performOrderUpdate(order, statusChangeEventTrigger::trigger);
    }

    private OrderDTO performOrderUpdate(OrderDTO order, Consumer<OrderStatusChangeEvent> action) {
        OrderDTO updatedOrder = client.updateOrder(order);
        OrderStatusChangeEvent orderStatusChangeEvent = getOrderStatusChangeEvent(updatedOrder);

        action.accept(orderStatusChangeEvent);

        return updatedOrder;
    }

    private OrderStatusChangeEvent getOrderStatusChangeEvent(OrderDTO order) {
        return new OrderStatusChangeEvent()
                .setOrderId(order.getOrderId())
                .setClientId(order.getClientId())
                .setDeliveryDriverId(order.getDeliveryDriverId())
                .setStatus(order.getStatus());
    }
}
