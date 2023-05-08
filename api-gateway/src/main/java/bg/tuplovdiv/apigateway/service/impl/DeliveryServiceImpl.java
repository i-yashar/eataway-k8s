package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.connectivity.client.OrdersRestClient;
import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.messaging.OrderContext;
import bg.tuplovdiv.apigateway.order.OrderQueue;
import bg.tuplovdiv.apigateway.service.DeliveryService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final OrderQueue orderQueue;
    private final OrdersRestClient client;

    public DeliveryServiceImpl(OrderQueue orderQueue, OrdersRestClient client) {
        this.orderQueue = orderQueue;
        this.client = client;
    }

    @Override
    public Collection<OrderDTO> getActiveOrders() {
        return orderQueue.getActiveOrders();
    }

    @Override
    public OrderContext getOrderInfo(UUID orderId) {
        return orderQueue.getOrderInfo(orderId);
    }

    @Override
    public OrderDTO takeOrder(UUID orderId, String deliveryDriverId) {
        OrderDTO order = orderQueue.takeOrder(orderId);
        order.setDeliveryDriverId(deliveryDriverId);
        order.setStatus("ACTIVE");

        return client.updateOrder(order);
    }

    @Override
    public OrderDTO updateOrder(UUID orderId, String status) {
        OrderDTO order = client.getUserOrder(orderId);
        order.setStatus(status);

        return client.updateOrder(order);
    }
}
