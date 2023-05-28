package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.connectivity.client.OrdersRestClient;
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
    public Collection<OrderDTO> getRegisteredOrders() {
        return orderQueue.getRegisteredOrders();
    }

    @Override
    public Collection<OrderDTO> getDeliveryDriverActiveOrders(String deliveryDriverId) {
        return client.getDeliveryDriverActiveOrders(deliveryDriverId);
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
        OrderDTO order = orderQueue.takeOrder(orderId, deliveryDriverId);
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
